package com.junhyeoklee.bakingapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Ingredient;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private static final String TAG = "IngredientAdapter";
    private Context mContext;
    private List<Ingredient> ingredients;

    public IngredientAdapter(Context context) { mContext = context; }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_ingredient_row,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        Ingredient ingredientItem = ingredients.get(position);
        holder.nameTextView.setText(ingredientItem.getIngredient());
        double quantity = ingredientItem.getQuantity();

        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.unitTextView.setText(ingredientItem.getMeasure());

        if(position % 2 ==0){
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    public Ingredient getItem(int position){ return ingredients.get(position); }

    public List<Ingredient> getIngredients(){ return ingredients; }

    public void setIngredients(List<Ingredient> newIngredients){
        ingredients = newIngredients;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView quantityTextView;
        TextView unitTextView;

        ConstraintLayout constraintLayout;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_name);
            quantityTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_quantity);
            unitTextView = (TextView)itemView.findViewById(R.id.tv_ingredient_unit);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.layout_ingredient);
        }
    }
}
