package com.rival.rivalrecipes.data;

import org.json.JSONObject;


public interface RecipeService {

    // TODO: /login (POST)
    void login(String email, String password);

    // TODO: recipes (GET)
    JSONObject getRecipes();

    // TODO: /recipe-images (GET)
    JSONObject getRecipeImages();

    // TODO: /recipes/:id (GET)
    JSONObject getRecipeImage();

}
