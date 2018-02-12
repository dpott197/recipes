package com.rival.rivalrecipes.data;

import android.util.Log;

import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darren on 2/11/18.
 */

public class JsonParser {

    private static final String TAG = "JsonParser";

    public static final String RESULTS = "results";

    // Recipe Properties
    public static final String RECIPE_ID = "recipe_name";
    public static final String RECIPE_NAME = "recipe_name";

    public static List<RecipeViewModel> getRecipes(JSONObject recipesResponseJsonObject) {
        ArrayList<RecipeViewModel> viewModels = new ArrayList<>();

        try {
            JSONArray resultsJSONArray = recipesResponseJsonObject.getJSONArray(RESULTS);
            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject recipeJsonObject = resultsJSONArray.getJSONObject(i);

                String recipeId = "";
                String recipeName = "";
                String recipeInstructions = "";
                String sm_url = "";
                String lg_url = "";

                if (recipeJsonObject.has(RECIPE_ID)) {
                    recipeId = recipeJsonObject.getString(RECIPE_ID);
                }

                if (recipeJsonObject.has(RECIPE_NAME)) {
                    recipeName = recipeJsonObject.getString(RECIPE_NAME);
                }

                viewModels.add(new RecipeViewModel(
                        recipeId,
                        recipeName,
                        recipeInstructions,
                        sm_url,
                        lg_url
                ));
            }
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }

        return viewModels;
    }
}
