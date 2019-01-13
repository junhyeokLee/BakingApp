package com.junhyeoklee.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Step;
import com.junhyeoklee.bakingapp.ui.fragment.StepFragment;
import com.junhyeoklee.bakingapp.ui.fragment.VideoFragment;
import com.junhyeoklee.bakingapp.util.Utility;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    public static String NAME_EXTRA = "recipe";
    public static String STEP_ID_EXTRA = "step_id";
    public static String STEP_LIST_EXTRA = "step_list";

    private static final String TAG = "StepActivity";

    private String recipeName;
    private int stepId;
    private ArrayList<Step> stepArrayList;

    private Button previousButton;
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            //pass with the step object
            Intent dataIntent = getIntent();

            if (dataIntent.hasExtra(STEP_ID_EXTRA)
                    && dataIntent.hasExtra(STEP_LIST_EXTRA)) {
                stepId = dataIntent.getIntExtra(STEP_ID_EXTRA, -1);
                stepArrayList = dataIntent.getParcelableArrayListExtra(STEP_LIST_EXTRA);
                recipeName = dataIntent.getStringExtra(NAME_EXTRA);

                setTitle(recipeName);

            } else {
                // required data not availabe
                Log.d(TAG, "onCreate: null step data");
                finish();
            }
        } else {
            stepId = savedInstanceState.getInt(STEP_ID_EXTRA);

            stepArrayList = savedInstanceState.getParcelableArrayList(STEP_LIST_EXTRA);

            recipeName = savedInstanceState.getString(NAME_EXTRA);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeView();
    }

    public void initializeView() {
        previousButton = (Button) findViewById(R.id.bt_previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepId > 0) {
                    stepId--;

                    // Start the preview step fragment
                    loadFragments();
                } else {
                    Utility.showToastMessage(getApplicationContext(),
                            "This is already the very first step.");
                }

            }
        });

        nextButton = (Button) findViewById(R.id.bt_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stepRange = stepArrayList.size() - 1;

                if (stepId < stepRange) {
                    stepId++;
                    loadFragments();
                } else {
                    Utility.showToastMessage(getApplicationContext(),
                            "This is already the last step.");
                }
            }
        });


        loadFragments();

    }


    public void loadFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        String videoTag = "video"+stepId;
        VideoFragment videoFragment =
                (VideoFragment)getSupportFragmentManager().findFragmentByTag(videoTag);

        if(videoFragment==null){
            String urlPath = stepArrayList.get(stepId).getVideoURL();
            videoFragment = VideoFragment.newInstance(urlPath);
        }

        transaction.replace(R.id.fragment_step_video, videoFragment, videoTag);

        String stepTag = "step" + stepId;

        StepFragment stepFragment =
                (StepFragment) getSupportFragmentManager().findFragmentByTag(stepTag);

        if(stepFragment==null){
            stepFragment = StepFragment.newInstance(stepId, stepArrayList);
        }


        transaction.replace(R.id.fragment_step_description, stepFragment, stepTag);

        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save StepId,
        outState.putInt(STEP_ID_EXTRA, stepId);

        // save Recipe Name
        outState.putString(NAME_EXTRA, recipeName);

        // save step array list
        outState.putParcelableArrayList(STEP_LIST_EXTRA, stepArrayList);
    }
}
