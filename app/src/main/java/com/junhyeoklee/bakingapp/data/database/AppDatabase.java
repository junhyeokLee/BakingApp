package com.junhyeoklee.bakingapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.junhyeoklee.bakingapp.data.model.RecipeDao;

/**
 * Created by goandroid on 7/16/18.
 */

@Database(entities = {Recipe.class},
        version = 1,
        exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipelist";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){
        if (sInstance == null) {

            synchronized (LOCK){
                if(sInstance == null) { // Double check synchronization
                    Log.d(LOG_TAG, "Creating new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            AppDatabase.DATABASE_NAME).build();
                }

            }
        }

        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }


    public abstract RecipeDao recipeDao();

}
