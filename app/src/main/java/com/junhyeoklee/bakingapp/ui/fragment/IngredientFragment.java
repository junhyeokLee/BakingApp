package com.junhyeoklee.bakingapp.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Ingredient;
import com.junhyeoklee.bakingapp.ui.adapter.IngredientAdapter;
import com.junhyeoklee.bakingapp.ui.viewModel.DetailViewModel;

import java.util.List;

public class IngredientFragment extends Fragment {

    private static final String TAG = "IngredientFragment";
    private DetailViewModel detailViewModel;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    IngredientAdapter ingredientAdapter;

    public static IngredientFragment newInstance(){
        IngredientFragment f = new IngredientFragment();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        detailViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
       View view = null;
        List<Ingredient> newIngredients = detailViewModel.getRecipe().getValue().getIngredients();

        if(newIngredients != null && newIngredients.size()>0){
            view = inflater.inflate(R.layout.fragment_ingredients,container,false);
            mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_ingredients);
            layoutManager = new LinearLayoutManager(this.getActivity());
            mRecyclerView.setLayoutManager(layoutManager);

            ingredientAdapter = new IngredientAdapter(this.getActivity());
            ingredientAdapter.setIngredients(newIngredients);

            mRecyclerView.setAdapter(ingredientAdapter);
        }
        else{
            Log.d(TAG, "onCreateView: empty ingredient list");

        }
        return view;
    }
}
