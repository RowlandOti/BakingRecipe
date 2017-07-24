package com.rowland.bakingapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rowland.bakingapp.R;
import com.rowland.bakingapp.data.payload.Recipe;
import com.rowland.bakingapp.rest.RecipeService;
import com.rowland.bakingapp.ui.adapters.RecipeListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.recipe_recycle_view)
    RecyclerView recipeRecyclerView;
    private RecipeListAdapter recipeListAdapter;

    public interface ISelectionCallBack {
        void onSelected(Recipe recipe, int selectedPosition);
    }

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Bundle args) {
        MainFragment frag = new MainFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle onSavedInstanceState) {
        super.onViewCreated(rootView, onSavedInstanceState);
        recipeListAdapter = new RecipeListAdapter(getActivity(), null);
        recipeRecyclerView.setAdapter(recipeListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeListAdapter.addAll(response.body());
                recipeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in Retrieving Recipes", Toast.LENGTH_SHORT).show();
                Log.d(MainFragment.class.getSimpleName(), t.toString());
            }
        });
    }
}
