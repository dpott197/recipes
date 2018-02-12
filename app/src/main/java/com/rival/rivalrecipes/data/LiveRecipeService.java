package com.rival.rivalrecipes.data;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LiveRecipeService implements RecipeService {

    private static final String TAG = "LiveRecipeService";

    private static final String BASE_URL = "https://mobile.rival.run";

    private String userToken = "validtoken12345";

    @Override
    public void login(String email, String password) {

    }

    @Override
    public JSONObject getRecipes() {
        return new JSONObject();
    }

    @Override
    public JSONObject getRecipeImages() {
        return new JSONObject();
    }

    @Override
    public JSONObject getRecipeImage() {
        return new JSONObject();
    }

    private String getUserToken() {
        return userToken;
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Authorization", getUserToken());
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

}
