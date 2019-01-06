package com.junhyeoklee.bakingapp.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goandroid on 6/25/18.
 */

public class SampleData {
    public static Ingredient getIngredient(){
        Ingredient ingredient = new Ingredient(2,
                "CUP",
                "Graham Cracker crumbs");
        return ingredient;
    }

    public static List<Ingredient> getIngredientList(int count){
        List<Ingredient> newIngredientList = new ArrayList<>(count);
        for(int i=0; i<count; i++){
            Ingredient newIngredient= getIngredient();
            newIngredient.setQuantity(
                    newIngredient.getQuantity() + i
            );
            newIngredientList.add(newIngredient);
        }

        return newIngredientList;
    }

    public static Step getStep(){
        Step step = new Step(0,
                "short description",
                "description",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                "thumbnailURL");
        return step;
    }

    public static List<Step> getStepList(int count){
        List<Step> newList = new ArrayList<>(count);
        for(int j=0; j<count; j++){
            Step newStep = getStep();
            newStep.setThumbnailURL(
                    newStep.getThumbnailURL() + j
            );

            newList.add(newStep);
        }
        return newList;
    }

    public static Recipe getRecipe(){
        Recipe recipe = new Recipe(1,
                "Nutella Pie",
                getIngredientList(5),
                getStepList(8),
                8,
                "image url");

        return recipe;
    }

    public static List<Recipe> getRecipeList(int count){
        List<Recipe> newRecipeList = new ArrayList<>(count);
        for(int k=0; k<count; k++){
            Recipe newRecipe = getRecipe();

            newRecipe.setName(
                    newRecipe.getName() + k
            );

            newRecipeList.add(newRecipe);
        }

        return newRecipeList;
    }

}
