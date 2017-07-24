package com.rowland.bakingapp;

import com.rowland.bakingapp.data.payload.Recipe;
import com.rowland.bakingapp.rest.RecipeService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rowland on 7/24/2017.
 */

public class RetrofitRecipeApiCallTest {

    private final CountDownLatch latch = new CountDownLatch(1);
    private RecipeService recipeService;
    private boolean mIsSuccessfull = false;

    @Before
    public void beforeTest() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeService = retrofit.create(RecipeService.class);
    }

    @Test
    public void testApiResponse() throws InterruptedException {
        Assert.assertNotNull(recipeService);
        Call<List<Recipe>> call = recipeService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mIsSuccessfull = response.isSuccessful();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mIsSuccessfull = false;
                latch.countDown();
            }
        });

        latch.await();
        Assert.assertTrue(mIsSuccessfull);
    }

    @After
    public void afterTest() {
        recipeService = null;
    }
}
