package com.rival.rivalrecipes.data.source;

import org.json.JSONObject;


public interface DataSource {

    void login(String email, String password);

    boolean isLoggedIn();

    JSONObject getRecipes();

    JSONObject getRecipeImages();

    JSONObject getRecipe(String recipeId);

}
