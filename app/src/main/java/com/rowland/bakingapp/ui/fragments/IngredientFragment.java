package com.rowland.bakingapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Ingredient;
import com.rowland.bakingapp.ui.adapters.IngredientListAdapter;
import com.rowland.bakingapp.ui.adapters.StepListAdapter;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {

    // The class Log identifier
    private static final String LOG_TAG = IngredientFragment.class.getSimpleName();


    /*private IngredientListAdapter mIngredientListAdapter;

    @BindView(R.id.ingredient_recycle_view)
    RecyclerView mIngredientRecyclerView;*/
    @BindView(R.id.ingredient_wordcloud_view)
    WordCloudView mIngredientWordCloudView;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public IngredientFragment newInstance(Bundle args) {
        IngredientFragment frag = new IngredientFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mIngredientRecyclerView.setLayoutManager(layoutManager);
        mIngredientListAdapter = new IngredientListAdapter(getActivity(), null);
        mIngredientRecyclerView.setAdapter(mIngredientListAdapter);*/

        List<Ingredient> list = getArguments().getParcelableArrayList(DetailFragment.SELECTED_KEY);
        /*mIngredientListAdapter.addAll(list);
        mIngredientListAdapter.notifyDataSetChanged();*/

        List<WordCloud> cloudList = new ArrayList<>();
        for (Ingredient i: list) {
            cloudList.add(new WordCloud(i.getIngredient(), new Random().nextInt(10)));
        }

        mIngredientWordCloudView.setColors(ColorTemplate.MATERIAL_COLORS);
        mIngredientWordCloudView.setDataSet(cloudList);
        mIngredientWordCloudView.notifyDataSetChanged();
    }

}
