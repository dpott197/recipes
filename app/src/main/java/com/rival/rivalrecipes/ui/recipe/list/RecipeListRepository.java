package com.rival.rivalrecipes.ui.recipe.list;

import android.util.Log;

import com.rival.rivalrecipes.data.source.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeListRepository implements RecipeListContract.Repository {

    private static final String TAG = RecipeListRepository.class.getSimpleName();

    private DataSource dataSource;

    RecipeListRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public JSONObject loadRecipes() {
        try {
            return dataSource.getRecipes();
        } catch (IOException | JSONException e) {
            Log.e(TAG, "loadRecipes() failed", e);
            return new JSONObject(); // Opted to fail gracefully and return and empty object rather than null
        }
    }

    @Override
    public JSONObject loadRecipeImages() {
        try {
            return dataSource.getRecipeImages();
        } catch (IOException | JSONException e) {
            Log.e(TAG, "loadRecipeImages() failed", e);
            return new JSONObject(); // Opted to fail gracefully and return and empty object rather than null
        }
    }

}
