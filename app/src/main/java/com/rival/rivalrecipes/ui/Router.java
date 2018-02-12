package com.rival.rivalrecipes.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailActivity;
import com.rival.rivalrecipes.ui.recipe.list.RecipeListActivity;

/**
 * Created by darren on 2/11/18.
 */

public class Router {

    public static void toRecipeList(Activity activity) {
        Intent intent = new Intent(activity, RecipeListActivity.class);
        activity.startActivity(intent);
    }

    public static void toRecipeDetail(Context context, RecipeViewModel recipeViewModel) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_VIEW_MODEL, recipeViewModel);
        context.startActivity(intent);
    }

}
