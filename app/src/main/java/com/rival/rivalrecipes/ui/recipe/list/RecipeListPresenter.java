package com.rival.rivalrecipes.ui.recipe.list;

import com.rival.rivalrecipes.data.JsonParser;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;

import org.json.JSONObject;

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
        JSONObject recipesResponseJsonObject = repository.loadRecipes();
        JSONObject imagesResponseJsonObject = repository.loadRecipeImages();
        return JsonParser.getRecipes(recipesResponseJsonObject, imagesResponseJsonObject);
    }

}
