package com.junhyeoklee.bakingapp.ui.adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.GlideException;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.junhyeoklee.bakingapp.GlideApp;
import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Ingredient;
import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.junhyeoklee.bakingapp.data.model.Step;
import com.junhyeoklee.bakingapp.ui.holders.IngredientsViewHolder;
import com.junhyeoklee.bakingapp.ui.holders.RecipeViewHolder;
import com.junhyeoklee.bakingapp.ui.holders.StepViewHolder;
import com.junhyeoklee.bakingapp.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecipeAdapter";
    private Context mContext;
    private Recipe mRecipe;
    private List<Recipe> recipeList;
    private SimpleExoPlayer mStepExoPlayer;
    private boolean EXO_RELEASED = true;
    private int mIngredientWidgetID;

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }
    final private ItemClickListener mItemClickListener;

    public RecipeAdapter(Context context,ItemClickListener listener){
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_row,parent,false);
      mContext = parent.getContext();
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RecipeViewHolder) {

            ((RecipeViewHolder) holder).mRecipe = recipeList.get(position);
            String recipeName = recipeList.get(position).getName();
            String ingredientNum = String.valueOf(recipeList.get(position).getIngredients().size());
            String stepsNum = String.valueOf(recipeList.get(position).getSteps().size());
            String servingsNum = String.valueOf(recipeList.get(position).getServings());
            final String lastNotEmptyVideoURL = getLastVideoURL(position);

            loadVideoThumbnail(mContext, lastNotEmptyVideoURL, ((RecipeViewHolder) holder).mPlayIconButton, ((RecipeViewHolder) holder).mThumbnailView);

            if (mIngredientWidgetID == AppWidgetManager.INVALID_APPWIDGET_ID || mIngredientWidgetID == -1) {

                mStepExoPlayer = getExoPlayer(mContext, lastNotEmptyVideoURL, ((RecipeViewHolder) holder));

                ((RecipeViewHolder) holder).mPlayIconButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!EXO_RELEASED) {
                            mStepExoPlayer.release();
                            mStepExoPlayer = getExoPlayer(mContext, lastNotEmptyVideoURL, ((RecipeViewHolder) holder));
                        }
                        ((RecipeViewHolder) holder).mStepExoPlayerView.setControllerAutoShow(true);

                        ((RecipeViewHolder) holder).mStepExoPlayerView.setPlayer(mStepExoPlayer);

                        mStepExoPlayer.prepare(((RecipeViewHolder) holder).mMediaSource);

                        mStepExoPlayer.setPlayWhenReady(true);

                        EXO_RELEASED = false;
                        switchToExoPlayerView((RecipeViewHolder) holder);

                    }
                });
            }
            ((RecipeViewHolder) holder).recipeBtnView.setText(recipeName);
            ((RecipeViewHolder) holder).recipeBtnView.setTextSize(16);
            ((RecipeViewHolder) holder).ingredientNumTextView.setText(ingredientNum);
            ((RecipeViewHolder) holder).steps_labelNumText.setText(stepsNum);
            ((RecipeViewHolder) holder).servings_labelNumText.setText(servingsNum);

            ((RecipeViewHolder) holder).recipeBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClickListener(position);
                }
            });

        }
    }


    public static void loadVideoThumbnail(Context ctx, String videoURL, final ImageButton playButton, final ImageView thumbnailView) {
        CircularProgressDrawable circularProgressDrawable = Utility.getCircleProgressDrawable(ctx, 15f, 80f);
        GlideApp.with(ctx)
                .load(videoURL)
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        playButton.setVisibility(View.VISIBLE);
                        thumbnailView.setImageDrawable(resource);
                        return true;
                    }
                })
                .into(thumbnailView);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        switchToThumbnailView((RecipeViewHolder) holder);
    }

    private void switchToExoPlayerView(RecipeViewHolder holder) {
        // hide thumbnail
        holder.mThumbnailHolder.setVisibility(View.GONE);
        // show player
        holder.mStepExoPlayerView.setVisibility(View.VISIBLE);
    }

    private void switchToThumbnailView(RecipeViewHolder holder) {
        // hide player
        holder.mStepExoPlayerView.setVisibility(View.GONE);
        // show thumbnail
        holder.mThumbnailHolder.setVisibility(View.VISIBLE);
    }


    private SimpleExoPlayer getExoPlayer(Context ctx, String mp4VideoUriString,RecipeViewHolder holder) {
        Uri mp4VideoUri = Uri.parse(mp4VideoUriString);
        // 1. Create a default TrackSelector
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Selects tracks provided by the MediaSource
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Prepare the player
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = null;
        if (ctx != null) {
            String appName = ctx.getApplicationInfo().name;
            dataSourceFactory = new DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, appName), bandwidthMeter);
        }
        // This is the MediaSource representing the media to be played.
        holder.mMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mp4VideoUri);

        // Create and return player
        return ExoPlayerFactory.newSimpleInstance(ctx, trackSelector);
    }
    private String getLastVideoURL(int position) {
        // find last not empty step video
        List<Step> allSteps = recipeList.get(position).getSteps();
        List<String> allNotEmptyVideoURLs = new ArrayList<>();
        for (int i = 0; i < allSteps.size(); i++) {
            String videoURL = allSteps.get(i).getVideoURL();
            if (!videoURL.isEmpty()) {
                allNotEmptyVideoURLs.add(videoURL);
            }
        }
        int videoURLsLength = allNotEmptyVideoURLs.size() - 1;
        return allNotEmptyVideoURLs.get(videoURLsLength);
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    @Nullable
    public Recipe getItem(int position){return recipeList.get(position);}

    public List<Recipe> getRecipeList(){return recipeList;}

    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;

        notifyDataSetChanged();
    }

//    class RecipeViewHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener {
//        View mView;
//         ImageView mThumbnailView;
//         ImageButton mPlayIconButton;
//         PlayerView mStepExoPlayerView;
//         FrameLayout mThumbnailHolder;
//        Button recipeBtnView;
//        TextView ingredientNumTextView;
//        TextView steps_labelNumText;
//        TextView servings_labelNumText;
////        ImageView imageView;
//        Recipe mRecipe;
//        MediaSource mMediaSource;
//
//        public RecipeViewHolder(View itemView){
//
//            super(itemView);
//            mView = itemView;
//            mThumbnailView = itemView.findViewById(R.id.iv_recipe_thumbnail);
//            mPlayIconButton = itemView.findViewById(R.id.ib_recipe_play_icon);
//            mThumbnailHolder = itemView.findViewById(R.id.fl_exo_thumbnail_holder);
//            mStepExoPlayerView = itemView.findViewById(R.id.exo_step_player);
//
//            recipeBtnView = (Button)itemView.findViewById(R.id.btn_recipe_name);
//            ingredientNumTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_num);
//            steps_labelNumText = (TextView)itemView.findViewById(R.id.tv_steps_num);
//            servings_labelNumText = (TextView)itemView.findViewById(R.id.tv_servings_num);
////            imageView = (ImageView)itemView.findViewById(R.id.iv_main_image);
//
//            recipeBtnView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            // pass the current recipe item to start the detail fragment
//            int elementId = getAdapterPosition();
//            mItemClickListener.onItemClickListener(elementId);
//
//
//        }
//    }
}
