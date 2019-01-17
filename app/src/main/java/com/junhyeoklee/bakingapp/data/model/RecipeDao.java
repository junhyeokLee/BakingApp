package com.junhyeoklee.bakingapp.data.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by goandroid on 7/16/18.
 */

@Dao
public interface RecipeDao {

    @Insert
    void insertRecipe(Recipe recipe);


    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe loadRecipeById(int id);


}
