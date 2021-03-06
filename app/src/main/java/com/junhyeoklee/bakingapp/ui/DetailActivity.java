package com.junhyeoklee.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Recipe;
import com.junhyeoklee.bakingapp.ui.fragment.StepFragment;
import com.junhyeoklee.bakingapp.ui.fragment.StepListFragment;
import com.junhyeoklee.bakingapp.ui.fragment.VideoFragment;
import com.junhyeoklee.bakingapp.ui.viewModel.DetailViewModel;
import com.junhyeoklee.bakingapp.util.Utility;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    public static final String KEY_SELECTED_RECIPE_ID = "selected_recipe_id";
    private static final String INGREDIENT_PREFS_NAME
            = "com.example.aarta.tastybaking.widgets.IngredientsWidgetProvider";
    private static final String INGREDIENT_PREF_KEY = "ingr_widget_";

    public static String RECIPE_ITEM = "recipe_item";

    private int currentStepId;

    private DetailViewModel detailViewModel;

    FragmentManager fragmentManager;
    private Recipe mRecipe ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecipe = null;
        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(RECIPE_ITEM)){
            mRecipe = intent.getParcelableExtra(RECIPE_ITEM);
        } else{
            finish();
        }

        setTitle(mRecipe.getName());
        setupDetailViewModel(mRecipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeView();
    }

    private void setupDetailViewModel(Recipe newRecipe){
       detailViewModel = ViewModelProviders.of(this)
       .get(DetailViewModel.class);
       detailViewModel.setRecipeLiveData(newRecipe);
    }

    private void initializeView(){
        if(findViewById(R.id.guideline) != null){
            detailViewModel.setTwoPane(true);
            setupTwoPane();
        }
        else{
            detailViewModel.setTwoPane(false);
            setupSinglePane();
        }
    }

    private void setupSinglePane(){
        if(!detailViewModel.isIngredientsDisplayed()){
            String stepListTag = "step_list";
            fragmentManager = getSupportFragmentManager();

            StepListFragment stepListFragment = (StepListFragment) fragmentManager
                        .findFragmentByTag(stepListTag);
            if(stepListFragment == null){
                stepListFragment = StepListFragment.newInstance();
            }

            fragmentManager.beginTransaction().replace(R.id.fragment_container,stepListFragment,stepListTag)
                    .commit();

        }
    }

    private void setupTwoPane(){
        currentStepId = detailViewModel.getCurrentStepId();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String videoTag = "video" + currentStepId;
        VideoFragment videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag(videoTag);

        if(videoFragment == null){
            String urlPath = detailViewModel.getRecipe().getValue()
                    .getSteps().get(currentStepId)
                    .getVideoURL();
            videoFragment = VideoFragment.newInstance(urlPath);
        }
        transaction.replace(R.id.fragment_video, videoFragment,videoTag);

        String stepTag = "step" + currentStepId;
        StepFragment stepFragment = (StepFragment) getSupportFragmentManager()
                .findFragmentByTag(stepTag);

        if(stepFragment == null){
            stepFragment = StepFragment.newInstance(currentStepId,
                    Utility.fromListtoArrayList(detailViewModel.getRecipe().getValue().getSteps()));
        }

        transaction.replace(R.id.fragment_step_instruction,stepFragment,stepTag);
        transaction.addToBackStack(stepTag);

        String stepListTag = "step_list";
        StepListFragment stepListFragment = (StepListFragment) getSupportFragmentManager()
                .findFragmentByTag(stepListTag);

        if (stepListFragment == null) {
            stepListFragment = StepListFragment.newInstance();
        }
        transaction.replace(R.id.fragment_container, stepListFragment, stepListTag);

        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!detailViewModel.isTwoPane()) {
            if (detailViewModel.isIngredientsDisplayed()) {
                detailViewModel.setIngredientsDisplayed(false);
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
