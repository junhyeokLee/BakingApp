package com.junhyeoklee.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.SimpleIdlingResource;
import com.junhyeoklee.bakingapp.data.database.AppDatabase;
import com.junhyeoklee.bakingapp.data.model.Ingredient;
import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.junhyeoklee.bakingapp.ui.adapter.RecipeAdapter;
import com.junhyeoklee.bakingapp.ui.viewModel.MainViewModel;
import com.junhyeoklee.bakingapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener{

    private static final String TAG = "MainActivity";

    @Nullable private SimpleIdlingResource mIdlingResource;

    private AppDatabase mDb;
    private MainViewModel viewModel;

    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;

    TextView emptyView;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

        recipeAdapter = new RecipeAdapter(getApplicationContext(),this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        mDb = AppDatabase.getsInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utility.isNetworkAvailable(getApplicationContext())){
            setupViewModel();
        } else{
            emptyView.setVisibility(View.VISIBLE);
            String errorMsg = getResources().getString(R.string.empty_view_text)
                    + " "
                    + getResources().getString(R.string.network_issue);
            emptyView.setText(errorMsg);
        }
    }

    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        viewModel.getRecipeList(mIdlingResource).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "onChanged: Updating list of recipes from LiveData in ViewModel");
                if(recipes != null && !recipes.isEmpty()){
                    // show recipes in recyclerview
                    emptyView.setVisibility(View.INVISIBLE);
                    recipeAdapter.setRecipeList(recipes);
                }
                else {
                    Log.d(TAG, "onChanged: recipes list is null or empty");
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(getResources().getString(R.string.empty_view_text));
                    }
            }
        });
    }

    private void initializeView(){
        emptyView = (TextView)findViewById(R.id.tv_main_empty_view);
        recipeRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        if(findViewById(R.id.layout_tablet) != null){
            mTwoPane = true;
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        }
        else{
            mTwoPane = false;
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }

    }

    @Override
    public void onItemClickListener(int itemId) {

        Recipe recipe = recipeAdapter.getItem(itemId);
        ArrayList<Ingredient> ingredients = Utility.fromListtoArrayList(
                recipe.getIngredients()
        );

//        // update widget UI after user pick a recipe
//        WidgetUpdateService.startActionUpdateWidget(this,
//                recipe.getName(),
//                ingredients);

//        Intent intent = new Intent(getApplicationContext(),
//                DetailActivity.class);
//        intent.putExtra(DetailActivity.RECIPE_ITEM,
//                recipe);
//        startActivity(intent);

    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
