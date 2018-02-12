package com.rival.rivalrecipes.data.source;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public interface DataSource {

    // Accessor(s)

    boolean isLoggedIn();

    // Request(s)

    void login(String email, String password) throws IOException, JSONException;

    JSONObject getRecipes() throws IOException, JSONException;

    JSONObject getRecipe(String recipeId) throws IOException, JSONException;

    JSONObject getRecipeImages() throws IOException, JSONException;

}
