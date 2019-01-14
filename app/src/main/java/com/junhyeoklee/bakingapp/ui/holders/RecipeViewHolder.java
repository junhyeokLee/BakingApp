package com.junhyeoklee.bakingapp.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public Recipe mRecipe;
    public View mView;

    public MediaSource mMediaSource;

    @BindView(R.id.iv_recipe_thumbnail)
    public ImageView mThumbnailView;

    @BindView(R.id.ib_recipe_play_icon)
    public ImageButton mPlayIconButton;

    @BindView(R.id.fl_exo_thumbnail_holder)
    public FrameLayout mThumbnailHolder;

    @BindView(R.id.exo_step_player)
    public PlayerView mStepExoPlayerView;

    @BindView(R.id.btn_recipe_name)
    public Button recipeBtnView;

    @BindView(R.id.tv_ingredient_num)
    public TextView ingredientNumTextView;

    @BindView(R.id.tv_steps_num)
    public TextView steps_labelNumText;

    @BindView(R.id.tv_servings_num)
    public TextView servings_labelNumText;


    public RecipeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this,itemView);

    }
}
