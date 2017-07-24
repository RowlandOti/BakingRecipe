package com.rowland.bakingapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.ui.fragments.DetailFragment;

import butterknife.ButterKnife;

public class DetailActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Inject all the views
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            // Restore/Recover assets

            return;
        } else {
            // In two-pane mode, show the detail pane
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.SELECTED_KEY,getIntent().getParcelableExtra(DetailFragment.SELECTED_KEY));
            showDetailFragment(args);
        }
    }

    // Insert the DetailFragment
    private void showDetailFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DetailFragment detailFrag = DetailFragment.newInstance(args);
        ft.replace(R.id.detail_fragment_container, detailFrag);
        ft.commit();
    }

}
