package com.junhyeoklee.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by goandroid on 8/10/18.
 */

public class StepFragment extends Fragment {
    public static final String STEP_ID_ARG = "step";
    public static final String STEP_LIST_ARG = "step_list";
    private static final String TAG = "StepFragment";

    private int stepId;
    private ArrayList<Step> steps;

    public static StepFragment newInstance(int stepId, ArrayList<Step> stepArrayList) {
        StepFragment f = new StepFragment();

        Bundle args = new Bundle();
        args.putInt(STEP_ID_ARG, stepId);
        args.putParcelableArrayList(STEP_LIST_ARG, stepArrayList);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step, container, false);

        if (getArguments().containsKey(STEP_ID_ARG)
                && getArguments().containsKey(STEP_LIST_ARG)) {

            stepId = getArguments().getInt(STEP_ID_ARG);
            steps = getArguments().getParcelableArrayList(STEP_LIST_ARG);

            if (stepId >= 0 && stepId < steps.size()) {
                final Step stepInfo = steps.get(stepId);


                TextView textViewStepId = (TextView) view.findViewById(R.id.tv_step_id);
                String strId = "Step : " + stepInfo.getId();
                textViewStepId.setText(strId);

                TextView textViewStepShortDesc = (TextView) view.findViewById(R.id.tv_step_short_desc);
                textViewStepShortDesc.setText(stepInfo.getShortDescription());

                TextView textViewStepDesc = (TextView) view.findViewById(R.id.tv_step_desc);
                textViewStepDesc.setText(stepInfo.getDescription());

                ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);

                //String thumbnailUrl = "https://www.drawingtutorials101.com/drawing-tutorials/Anime-and-Manga/Dragon-Ball-Z/krillin/how-to-draw-Krillin-from-Dragon-Ball-Z-step-0.png";
                String thumbnailUrl = steps.get(stepId).getThumbnailURL();
                if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                    Log.d(TAG, "onCreateView: thumbnailUrl string is null or empty");
                } else {

                    Picasso.with(getActivity().getApplicationContext())
                            .load(thumbnailUrl)
                            .into(imageView);
                }

            } else {
                Log.d(TAG, "onCreateView: stepId is out of range");
            }

        }

        return view;
    }
}
