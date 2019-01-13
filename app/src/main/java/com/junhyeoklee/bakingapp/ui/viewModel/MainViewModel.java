package com.junhyeoklee.bakingapp.ui.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.junhyeoklee.bakingapp.AppExecutors;
import com.junhyeoklee.bakingapp.SimpleIdlingResource;
import com.junhyeoklee.bakingapp.data.api.RecipeApiService;
import com.junhyeoklee.bakingapp.data.api.RecipeInterface;
import com.junhyeoklee.bakingapp.data.database.AppDatabase;
import com.junhyeoklee.bakingapp.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel{

    private static final String TAG = "MainViewModel";

    private MutableLiveData<List<Recipe>> recipeList;
    private AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "MainViewModel: Actively retrieving the tasks from the database");
        recipeList = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipeList(@Nullable final SimpleIdlingResource idlingResource){
        loadRecipeData(idlingResource);
        return recipeList;
    }

    public void setRecipeList(List<Recipe> newRecipes){ recipeList.setValue(newRecipes);}

    private void loadRecipeData(@Nullable final SimpleIdlingResource idlingResource){
        if(idlingResource != null){
            idlingResource.setIdleState(false);
        }
       Call<List<Recipe>> call = RecipeApiService.getRetrofitInstance().create(RecipeInterface.class).getRecipes();
//        RecipeInterface mRetrofitRequest =
//                new Retrofit.Builder()
//                .baseUrl(RecipeInterface.API_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build()
//                        .create(RecipeInterface.class);

        final List<Recipe> newData = new ArrayList<>();
//        Call<List<Recipe>> call = mRetrofitRequest.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                newData.addAll(response.body());

                Log.d(TAG, "onResponse: recipe size = " + response.body().size());
                Log.d(TAG, "onResponse: newData size = " + newData.size());
                recipeList.postValue(newData);

                // insert recipe data into the ROOM database
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                    for(Recipe newRecipe : newData){
                        Recipe recipe = database.recipeDao().loadRecipeById(newRecipe.getId());
                        if(recipe == null){
                            // add the database only if it has an unique id
                            database.recipeDao().insertRecipe(newRecipe);
                        }
                    }
                    }
                });
                if(idlingResource != null){
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + "Retrofit error");
                Log.d(TAG, "onFailure: can't retrieve recipe data");
            }
        });

        Log.d(TAG, "getInternetRecipeData: returning newData.size() = " + newData.size());
        if(newData!= null && newData.size()>0){
            Log.d(TAG, "getInternetRecipeData: returning newData");
        } else {
            Log.d(TAG, "getInternetRecipeData: returning null");

        }
    }
}
