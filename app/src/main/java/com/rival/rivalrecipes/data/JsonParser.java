package com.rival.rivalrecipes.data;

import android.support.v4.util.Pair;
import android.util.Log;

import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darren on 2/11/18.
 */

public class JsonParser {

    private static final String TAG = "JsonParser";

    public static final String IMG_URLS = "img_urls";
    public static final String LG_URL = "lg_url";
    private static final String RECIPE_ID = "recipe_id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String RECIPE_INSTRUCTIONS = "recipe_instructions";
    private static final String RESULTS = "results";
    public static final String SM_URL = "sm_url";

    public static List<RecipeViewModel> getRecipes(
            JSONObject recipesResponseJsonObject,
            JSONObject imagesResponseJsonObject
    ) {
        ArrayList<RecipeViewModel> recipeViewModels = new ArrayList<>();
        Map<String, Pair<String, String>> imageUrlMap = new HashMap<>();

        try {
            JSONArray imagesJsonArray = imagesResponseJsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < imagesJsonArray.length(); i++) {
                JSONObject imageJsonObject = imagesJsonArray.getJSONObject(i);
                JSONObject urlsJsonObject = imageJsonObject.getJSONObject(IMG_URLS);
                String smallImageUrl = urlsJsonObject.getString(SM_URL);
                String largeImageUrl = urlsJsonObject.getString(LG_URL);
                imageUrlMap.put(imageJsonObject.getString(RECIPE_ID), new Pair<>(smallImageUrl, largeImageUrl));
            }

            JSONArray recipesJsonArray = recipesResponseJsonObject.getJSONArray(JsonParser.RESULTS);
            for (int i = 0; i < recipesJsonArray.length(); i++) {
                JSONObject recipeJsonObject = recipesJsonArray.getJSONObject(i);

                String recipeId = "";
                String recipeName = "";
                String recipeInstructions = "";
                String smallImageUrl = "";
                String largeImageUrl = "";

                if (recipeJsonObject.has(JsonParser.RECIPE_ID)) {
                    recipeId = recipeJsonObject.getString(JsonParser.RECIPE_ID);
                }

                if (recipeJsonObject.has(JsonParser.RECIPE_NAME)) {
                    recipeName = recipeJsonObject.getString(JsonParser.RECIPE_NAME);
                }

                if (recipeJsonObject.has(JsonParser.RECIPE_INSTRUCTIONS)) {
                    recipeInstructions = recipeJsonObject.getString(JsonParser.RECIPE_INSTRUCTIONS);
                }

                if (imageUrlMap.containsKey(recipeId)) {
                    smallImageUrl = imageUrlMap.get(recipeId).first;
                    largeImageUrl = imageUrlMap.get(recipeId).second;
                }

                recipeViewModels.add(new RecipeViewModel(
                        recipeId,
                        recipeName,
                        recipeInstructions,
                        smallImageUrl,
                        largeImageUrl
                ));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse recipes", e);
        }

        return recipeViewModels;
    }
}
