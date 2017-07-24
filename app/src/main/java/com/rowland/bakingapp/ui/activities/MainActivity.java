package com.rowland.bakingapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Recipe;
import com.rowland.bakingapp.ui.fragments.DetailFragment;
import com.rowland.bakingapp.ui.fragments.MainFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseToolBarActivity implements MainFragment.ISelectionCallBack {

    // Logging Identifier for class
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String SELECTED_POSITION_KEY = "selected_position_key";

    private int mSelectedPosition = -1;
    // The selected item remote id
    private Recipe mSelectedRecipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inject all the views
        ButterKnife.bind(this);
        // Check that the activity is using the layout with the fragment_container id
        if (findViewById(R.id.detail_fragment_container) != null) {
            // If this view is present, then the activity should be in two-pane mode.
            toggleShowTwoPane(true);
            mSelectedRecipe = getIntent().getParcelableExtra(DetailFragment.SELECTED_KEY);
            /// Restore state
            if (savedInstanceState != null) {
                // Recover assets
                mSelectedPosition = savedInstanceState.getInt(SELECTED_POSITION_KEY);
                mSelectedRecipe = savedInstanceState.getParcelable(DetailFragment.SELECTED_KEY);
                // Set positions as selected
                onSelected(mSelectedRecipe, mSelectedPosition);
                return;
            } else {
                // In two-pane mode, show the detail pane
                //showDetailFragment(null);
            }
        } else {
            toggleShowTwoPane(false);
        }
        showMainFragment(null);
    }

    // Insert the DetailFragment
    private void showDetailFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DetailFragment detailFrag = DetailFragment.newInstance(args);
        ft.replace(R.id.detail_fragment_container, detailFrag);
        ft.commit();
    }

    // Insert the MainFragment
    private void showMainFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MainFragment mainFrag = MainFragment.newInstance(args);
        ft.replace(R.id.main_fragment_container, mainFrag);
        ft.commit();
    }

    // Save any important data for recovery
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_POSITION_KEY, mSelectedPosition);
        outState.putParcelable(DetailFragment.SELECTED_KEY, mSelectedRecipe);
    }

    @Override
    public void onSelected(Recipe recipe, int selectedPosition) {
        mSelectedPosition = selectedPosition;
        mSelectedRecipe = recipe;

        if (mIsTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.SELECTED_KEY, mSelectedRecipe);
            showDetailFragment(args);
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.SELECTED_KEY, mSelectedRecipe);
            startActivity(intent);
        }
    }
}