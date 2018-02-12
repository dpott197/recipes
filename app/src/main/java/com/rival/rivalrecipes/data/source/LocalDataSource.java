package com.rival.rivalrecipes.data.source;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalDataSource implements DataSource {

    private Context context;

    public LocalDataSource(Context context) {
        this.context = context;
    }

    @Override
    public boolean login(String email, String password) {
        return true;
    }

    @Override
    public boolean isLoggedIn() {
        return true;
    }

    @Override
    public JSONObject getRecipes() throws IOException, JSONException {
        return new JSONObject(getString("json/recipes.json"));
    }

    @Override
    public JSONObject getRecipeImages() throws IOException, JSONException {
        return new JSONObject(getString("json/recipe-images.json"));
    }

    @Override
    public JSONObject getRecipe(String recipeId) throws IOException, JSONException {
        return new JSONObject(getString("json/recipes/" + recipeId + ".json"));
    }

    private String getString(String urlString) throws IOException {
        return new String(getBytes(urlString), "UTF-8");
    }

    private byte[] getBytes(String fileName) throws IOException {
        InputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            inputStream = context.getAssets().open(fileName);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            inputStream.close();
        }
    }

}
