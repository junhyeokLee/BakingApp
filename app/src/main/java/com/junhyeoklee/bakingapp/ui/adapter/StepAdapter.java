package com.junhyeoklee.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private static final String TAG = "StepAdapter";
    private Context mContext;
    private List<Step> stepList;

    public interface StepClickListener{
        void onItemClickListener(int itemId);
    }

    final private StepClickListener mStepClickListener;

    public StepAdapter(Context context, StepClickListener listener){
        mContext = context;
        mStepClickListener = listener;
    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_step_row, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step stepItem = stepList.get(position);

        String description = "";
        if(position >= 1){
            description = "Step #" + position + ": ";
        }

        description = description + stepItem.getShortDescription();

        holder.stepButton.setText(description);

    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size();
    }

    @Nullable
    public Step getItem(int position){
        return stepList.get(position);
    }

    public List<Step> getStepList(){
        return stepList;
    }

    public void setStepList(List<Step> newStepList){
        this.stepList = newStepList;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button stepButton;

        public StepViewHolder(View itemView){
            super(itemView);

            stepButton = (Button)itemView.findViewById(R.id.bt_step);

            itemView.setOnClickListener(this);
            stepButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = getAdapterPosition();
            mStepClickListener.onItemClickListener(elementId);

        }

    }
}
