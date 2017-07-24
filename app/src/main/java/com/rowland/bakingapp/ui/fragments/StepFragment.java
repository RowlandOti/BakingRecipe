package com.rowland.bakingapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements VerticalStepperForm {

    // The class Log identifier
    private static final String LOG_TAG = StepFragment.class.getSimpleName();

    private List<Step> list;

    @BindView(R.id.step_vertical_stepper)
    VerticalStepperFormLayout mStepVerticalLayout;


    public StepFragment() {
        // Required empty public constructor
    }

    public StepFragment newInstance(Bundle args) {
        StepFragment frag = new StepFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = getArguments().getParcelableArrayList(DetailFragment.SELECTED_KEY);

        String[] stepTitles = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            stepTitles[i] = list.get(i).getShortDescription();
        }
        VerticalStepperFormLayout.Builder.newInstance(mStepVerticalLayout, stepTitles, this, getActivity())
                .displayBottomNavigation(true)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .materialDesignInDisabledSteps(true)
                .init();
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View stepView = LayoutInflater.from(getContext()).inflate(R.layout.row_step, null, false);
        TextView mDescriptionTextView = (TextView) stepView.findViewById(R.id.step_description);
        mDescriptionTextView.setText(list.get(stepNumber).getDescription());
        ImageView mThumbnailImageView = (ImageView) stepView.findViewById(R.id.step_thumbnail);
        mThumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToDo: use ExoPlayer to play video
            }
        });
        return stepView;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        mStepVerticalLayout.setStepAsCompleted(stepNumber);
    }

    @Override
    public void sendData() {
        //method not needed for now
    }
}
