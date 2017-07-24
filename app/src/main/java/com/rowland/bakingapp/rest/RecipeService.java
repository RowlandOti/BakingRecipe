package com.rowland.bakingapp.rest;

import com.rowland.bakingapp.data.payload.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rowland on 7/21/2017.
 */

public interface RecipeService {

    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}
