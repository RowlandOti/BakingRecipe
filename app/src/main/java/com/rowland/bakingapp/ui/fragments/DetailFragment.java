package com.rowland.bakingapp.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.ui.adapters.DetailPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    // Logging Identifier for class
    private final String LOG_TAG = DetailFragment.class.getSimpleName();

    // The Recipe ID Identifier Key
    public static final String SELECTED_KEY = "recipe_key";

    private DetailPagerAdapter mDetailPagerAdpater;

    @BindView(R.id.detail_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.detail_tablayout)
    TabLayout mTabLayout;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Bundle args) {
        DetailFragment frag = new DetailFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        Bundle args = getArguments();
        mDetailPagerAdpater = new DetailPagerAdapter(getChildFragmentManager(), args);
        mViewPager.setAdapter(mDetailPagerAdpater);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
