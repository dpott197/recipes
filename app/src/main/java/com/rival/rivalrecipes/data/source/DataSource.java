package com.rival.rivalrecipes.data.source;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public interface DataSource {

    // Request(s)

    boolean isLoggedIn();

    boolean login(String email, String password);

    JSONObject getRecipes() throws IOException, JSONException;

    JSONObject getRecipe(String recipeId) throws IOException, JSONException;

    JSONObject getRecipeImages() throws IOException, JSONException;

}
