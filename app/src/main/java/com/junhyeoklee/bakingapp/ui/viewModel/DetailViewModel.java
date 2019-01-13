package com.junhyeoklee.bakingapp.ui.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.junhyeoklee.bakingapp.data.model.Recipe;

public class DetailViewModel extends ViewModel{

    private static final String TAG = "DetailViewModel";

    private MutableLiveData<Recipe> recipeLiveData;
    private boolean twoPane;
    private int currentStepId = 0;
    private boolean ingredientsDisplayed;

    public void setRecipeLiveData(Recipe recipe){
        if(this.recipeLiveData != null && this.recipeLiveData.getValue().getId() != recipe.getId()){
            return;
        }

        if(this.recipeLiveData == null){
            this.recipeLiveData = new MutableLiveData<>();
        }
        this.recipeLiveData.setValue(recipe);
    }

    public LiveData<Recipe> getRecipe() { return recipeLiveData; }

    public boolean isTwoPane() { return twoPane; }

    public void setTwoPane(boolean twoPane) { this.twoPane = twoPane; }

    public int getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int currentStepId) {
        this.currentStepId = currentStepId;
    }

    public boolean isIngredientsDisplayed() {
        return ingredientsDisplayed;
    }

    public void setIngredientsDisplayed(boolean ingredientsDisplayed) {
        this.ingredientsDisplayed = ingredientsDisplayed;
    }
}
