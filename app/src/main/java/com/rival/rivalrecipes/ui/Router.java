package com.rival.rivalrecipes.ui;

import android.content.Context;
import android.content.Intent;

import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailActivity;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailFragment;

/**
 * Created by darren on 2/11/18.
 */

public class Router {

    public static void toRecipeDetail(Context context, RecipeViewModel recipeViewModel) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailFragment.ARG_RECIPE_VIEW_MODEL, recipeViewModel);
        context.startActivity(intent);
    }

}
