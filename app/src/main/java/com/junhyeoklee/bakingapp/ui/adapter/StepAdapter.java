package com.junhyeoklee.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.junhyeoklee.bakingapp.R;
import com.junhyeoklee.bakingapp.data.model.Step;
import com.junhyeoklee.bakingapp.ui.holders.StepViewHolder;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Step stepItem = stepList.get(position);

        if(holder instanceof StepViewHolder){
            StepViewHolder viewHolder = (StepViewHolder) holder;
            viewHolder.mTvStepOrder.setText(String.valueOf(position)+".");
            viewHolder.mTvStepName.setText(stepItem.getShortDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStepClickListener.onItemClickListener(position);
                }
            });
        }
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

}
