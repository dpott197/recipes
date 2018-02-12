package com.rival.rivalrecipes.ui.recipe.list;

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

public class RecipeListPresenter implements RecipeListContract.Presenter {

    public static final String TAG = RecipeListPresenter.class.getSimpleName();

    private final RecipeListContract.View view;
    private final RecipeListContract.Repository repository;

    RecipeListPresenter(RecipeListContract.View view, RecipeListContract.Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public List<RecipeViewModel> showRecipeViewModels() {
        List<RecipeViewModel> viewModels = new ArrayList<RecipeViewModel>();
        JSONObject recipesResponseJsonObject = repository.loadRecipes();
        JSONObject imagesResponseJsonObject = repository.loadRecipeImages();

        try {
            JSONArray resultsJSONArray = recipesResponseJsonObject.getJSONArray("results");
            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject recipeJsonObject = resultsJSONArray.getJSONObject(i);

                String recipeId = "";
                String recipeName = "";
                String recipeInstructions = "";
                String sm_url = "";
                String lg_url = "";

                if (recipeJsonObject.has("recipe_name")) {
                    recipeName = recipeJsonObject.getString("recipe_name");
                }

                viewModels.add(new RecipeViewModel(
                        recipeId,
                        recipeName,
                        recipeInstructions,
                        sm_url,
                        lg_url
                ));
            }
            JSONObject recipesResponseImagesJsonObject = repository.loadRecipeImages();
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }

        return viewModels;
    }

}
