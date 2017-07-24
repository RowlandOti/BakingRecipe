package com.rowland.bakingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Rowland on 7/24/2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = StepListAdapter.class.getSimpleName();

    // Unsorted List
    private List<Step> mList;
    private Context mContext;

    public StepListAdapter(Context context, List<Step> list) {
        this.mList = list != null ? list : new ArrayList<Step>();
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_step, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = mList.get(position);
        // Bind the data to the view holder
        holder.bindTo(step, position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_description)
        TextView mDescriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Bind the data to the holder views
        private void bindTo(final Step step, final int position) {
            mDescriptionTextView.setText(step.getShortDescription());
        }
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Step> list) {
        if (list != null) {
            mList.addAll(list);
        }
    }
}
