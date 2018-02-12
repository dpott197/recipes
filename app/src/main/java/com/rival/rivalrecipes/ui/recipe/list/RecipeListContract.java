package com.rival.rivalrecipes.ui.recipe.list;

import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeListContract {

    interface View {
        void hideProgressBar();
        void showProgressBar();
        void showRecipeViewModels(List<RecipeViewModel> viewModels);
    }

    interface Presenter {
        List<RecipeViewModel> showRecipeViewModels();
    }

    interface Repository {
        JSONObject loadRecipes();
        JSONObject loadRecipeImages();
    }

}
