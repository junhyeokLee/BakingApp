package com.junhyeoklee.bakingapp.data.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.junhyeoklee.bakingapp.data.model.Ingredient;
import com.junhyeoklee.bakingapp.data.model.Step;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by goandroid on 8/20/18.
 */

public class DataConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Step> stringToStepList(String stepData){
        if(stepData == null){
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Step>>() {}.getType();

        return  gson.fromJson(stepData, listType);

    }

    @TypeConverter
    public static String stepListToString(List<Step> stepList){
        return gson.toJson(stepList);
    }


    @TypeConverter
    public static List<Ingredient> stringToIngredientList(String ingredientData){
        if(ingredientData == null){
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Ingredient>>() {}.getType();

        return gson.fromJson(ingredientData, listType);
    }

    @TypeConverter
    public static String ingredientListToString(List<Ingredient> ingredientList){
        return gson.toJson(ingredientList);
    }
}
