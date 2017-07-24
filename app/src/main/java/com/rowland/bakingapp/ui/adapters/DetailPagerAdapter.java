package com.rowland.bakingapp.ui.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.rowland.bakingapp.data.payload.Recipe;
import com.rowland.bakingapp.ui.fragments.DetailFragment;
import com.rowland.bakingapp.ui.fragments.IngredientFragment;
import com.rowland.bakingapp.ui.fragments.StepFragment;

import java.util.ArrayList;

/**
 * Created by Rowland on 7/24/2017.
 */

public class DetailPagerAdapter extends FragmentStatePagerAdapter {

    // The class Log identifier
    private static final String LOG_TAG = DetailPagerAdapter.class.getSimpleName();

    private Recipe mRecipe;
    private String[] TITLES = {"Ingredients", "Steps"};

    public DetailPagerAdapter(FragmentManager fm, Bundle args) {
        super(fm);
        mRecipe = args.getParcelable(DetailFragment.SELECTED_KEY);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0: {
                Bundle args = new Bundle();
                args.putParcelableArrayList(DetailFragment.SELECTED_KEY, (ArrayList<? extends Parcelable>) mRecipe.getIngredients());
                IngredientFragment frag = new IngredientFragment().newInstance(args);
                return frag;
            }
            case 1: {
                Bundle args = new Bundle();
                args.putParcelableArrayList(DetailFragment.SELECTED_KEY, (ArrayList<? extends Parcelable>) mRecipe.getSteps());
                StepFragment frag = new StepFragment().newInstance(args);
                return frag;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
