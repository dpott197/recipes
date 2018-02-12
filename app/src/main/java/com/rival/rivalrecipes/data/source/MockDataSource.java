package com.rival.rivalrecipes.data.source;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockDataSource implements DataSource {

    private Context context;

    public MockDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void login(String email, String password) {
        // do nothing
    }

    @Override
    public boolean isLoggedIn() {
        return true;
    }

    @Override
    public JSONObject getRecipes() {
        return null;
    }

    @Override
    public JSONObject getRecipeImages() {
        return null;
    }

    @Override
    public JSONObject getRecipe(String recipeId) {
        return null;
    }

    protected String getString(String urlString) throws IOException {
        return new String(getBytes(urlString), "UTF-8");
    }

    protected byte[] getBytes(String fileName) throws IOException {
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
