package com.junhyeoklee.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private static final String TAG = "RecipeAdapter";
    private Context mContext;
    private List<Recipe> recipeList;

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }
    final private ItemClickListener mItemClickListener;

    public RecipeAdapter(Context context,ItemClickListener listener){
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_row,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
    Recipe recipeItem = recipeList.get(position);
    holder.recipeTextView.setText(recipeItem.getName());
    String ingridient = recipeItem.getIngredients().size() + " Ingredients";
    holder.ingredientTextView.setText(ingridient);

    String stringUrl = recipeItem.getImage();
    if(stringUrl.isEmpty()){
        holder.imageView.setImageResource(R.drawable.image_not_available);
    }else{
        Picasso.with(mContext)
                .load(stringUrl)
                .error(R.drawable.image_not_available)
                .into(holder.imageView);
    }
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

    class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView recipeTextView;
        TextView ingredientTextView;
        ImageView imageView;

        public RecipeViewHolder(View itemView){

            super(itemView);
            recipeTextView = (TextView)itemView.findViewById(R.id.tv_main_recipe);
            ingredientTextView = (TextView)itemView.findViewById(R.id.tv_main_ingredient);
            imageView = (ImageView)itemView.findViewById(R.id.iv_main_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // pass the current recipe item to start the detail fragment
            int elementId = getAdapterPosition();
            mItemClickListener.onItemClickListener(elementId);


        }
    }
}
