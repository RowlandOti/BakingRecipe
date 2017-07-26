package com.rowland.bakingapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Step;
import com.rowland.bakingapp.managers.BeepManager;

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

    // The Step ID Identifier Key
    public static final String SELECTED_VIDEO_KEY = "step_video_key";

    private List<Step> list;

    @BindView(R.id.step_vertical_stepper)
    VerticalStepperFormLayout mStepVerticalLayout;

    interface VideoPlay {
        void play();
    }


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
    public View createStepContentView(final int stepNumber) {
        final Step step = list.get(stepNumber);
        View stepView = LayoutInflater.from(getContext()).inflate(R.layout.row_step, null, false);
        TextView mDescriptionTextView = (TextView) stepView.findViewById(R.id.step_description);
        mDescriptionTextView.setText(step.getDescription());
        ImageView mThumbnailImageView = (ImageView) stepView.findViewById(R.id.step_thumbnail);
        mThumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = step.getVideoURL();
                if (!videoUrl.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putString(SELECTED_VIDEO_KEY, videoUrl);
                    showVideoFragment(args);
                } else {
                    Toast.makeText(getContext(), "No Video is available for this Step", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return stepView;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        hideVideoFragment();
        mStepVerticalLayout.setStepAsCompleted(stepNumber);
    }

    @Override
    public void sendData() {
        //method not needed for now
        BeepManager bpManager = new BeepManager(getActivity());
        bpManager.playBeepSoundAndVibrate();
    }

    // Insert the DetailFragment
    private void showVideoFragment(Bundle args) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        VideoFragment frag = VideoFragment.newInstance(args);
        ft.replace(R.id.video_fragment_container, frag);
        ft.commit();
    }

    private void hideVideoFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fm.findFragmentById(R.id.video_fragment_container));
    }
}
