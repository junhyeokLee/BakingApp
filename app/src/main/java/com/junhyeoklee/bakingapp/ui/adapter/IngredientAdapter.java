package com.junhyeoklee.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Ingredient;
import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.junhyeoklee.bakingapp.ui.holders.IngredientsViewHolder;

import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "IngredientAdapter";
    private Recipe mRecipe;
    private Context mContext;
    private List<Ingredient> ingredients;

    public IngredientAdapter(Context context) { mContext = context; }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_ingredient_row,parent,false);
        mContext = parent.getContext();
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof IngredientsViewHolder){
            IngredientsViewHolder viewHolder = (IngredientsViewHolder) holder;
            StringBuilder ingValue = new StringBuilder();
            for(int i = 0; i < ingredients.size(); i++){
                Ingredient ingredient = ingredients.get(i);
                ingValue.append(String.format(Locale.getDefault(), "• %s (%s %s)", ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));
                if (i != ingredients.size() - 1)
                    ingValue.append("\n");
            }
            viewHolder.mTvIngredients.setText(ingValue.toString());
        }
    }

//    @Override
//    public void onBindViewHolder(IngredientViewHolder holder, int position) {
//
//        Ingredient ingredientItem = ingredients.get(position);
//        holder.nameTextView.setText(ingredientItem.getIngredient());
//        double quantity = ingredientItem.getQuantity();
//
//        holder.quantityTextView.setText(String.valueOf(quantity));
//        holder.unitTextView.setText(ingredientItem.getMeasure());
//
//        if(position % 2 ==0){
//            holder.constraintLayout.setBackgroundColor(Color.parseColor("#D3D3D3"));
//        }
//
//    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : 1;
    }

    public Ingredient getItem(int position){ return ingredients.get(position); }

    public List<Ingredient> getIngredients(){ return ingredients; }

    public void setIngredients(List<Ingredient> newIngredients){
        ingredients = newIngredients;
        notifyDataSetChanged();
    }

//    class IngredientViewHolder extends RecyclerView.ViewHolder{
//        TextView nameTextView;
//        TextView quantityTextView;
//        TextView unitTextView;
//
//        ConstraintLayout constraintLayout;
//
//        public IngredientViewHolder(View itemView) {
//            super(itemView);
//            nameTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_name);
//            quantityTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_quantity);
//            unitTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_unit);
//            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.layout_ingredient);
//        }
//    }
}
