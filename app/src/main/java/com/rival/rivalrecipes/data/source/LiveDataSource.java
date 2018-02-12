package com.rival.rivalrecipes.data.source;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class LiveDataSource implements DataSource {

    private static final String TAG = "LiveDataSource";

    // URL(s) and Path(s)
    private static final String BASE_URL = "https://mobile.rival.run";
    private static final String LOGIN_PATH = "/login";
    private static final String RECIPES_PATH = "/recipes";
    private static final String RECIPE_IMAGES_PATH = "/recipe-images";

    // Header(s)
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String POST = "POST";
    public static final String ACCEPT = "Accept";

    protected static LiveDataSource sRecipeService;

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String userToken = "validtoken12345";

    public LiveDataSource() {

    }

    public static void init() {
        sRecipeService = new LiveDataSource();
    }

    public static LiveDataSource getInstance() {
        return sRecipeService;
    }

    @Override
    public void login(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, "jsonObject.put(...) failed", e);
        }

        String urlString = BASE_URL + LOGIN_PATH;
        try {
            String response = post(urlString, jsonObject);
            JSONObject responseJsonObject = new JSONObject(response);

            if (responseJsonObject.has("user_token")) {
                setUserToken(responseJsonObject.getString("user_token"));
            }
        } catch (IOException | JSONException | NullPointerException e) {
            Log.e(TAG, "login(...) failed", e);
        }
    }

    @Override
    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getUserToken());
    }

    @Override
    public JSONObject getRecipes() throws IOException, JSONException {
        String urlString = BASE_URL + RECIPES_PATH;
        Log.d(TAG, "getRecipes() " + urlString);
        String response = getString(urlString);
        JSONObject responseJsonObject = new JSONObject(response);
        Log.d(TAG, responseJsonObject.toString());
        return responseJsonObject;
    }

    @Override
    public JSONObject getRecipeImages() throws IOException, JSONException {
        String urlString = BASE_URL + RECIPE_IMAGES_PATH;
        Log.d(TAG, "getRecipes() " + urlString);
        String response = getString(urlString);
        JSONObject responseJsonObject = new JSONObject(response);
        Log.d(TAG, responseJsonObject.toString());
        return responseJsonObject;
    }

    @Override
    public JSONObject getRecipe(String recipeId) throws IOException, JSONException {
        String urlString = BASE_URL + RECIPES_PATH + "/" + recipeId;
        Log.d(TAG, "getRecipes() " + urlString);
        String response = getString(urlString);
        JSONObject responseJsonObject = new JSONObject(response);
        Log.d(TAG, responseJsonObject.toString());
        return responseJsonObject;
    }

    private String getUserToken() {
        return userToken;
    }

    protected String getString(String urlString) throws IOException {
        return new String(getBytes(urlString), "UTF-8");
    }

    protected byte[] getBytes(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty(AUTHORIZATION, getUserToken());

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();
            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlString);
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

    protected String post(String urlString, JSONObject jsonObject) throws IOException {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(POST);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(ACCEPT, APPLICATION_JSON);

            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(jsonObject.toString());
            streamWriter.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d(TAG, stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e(TAG, connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
