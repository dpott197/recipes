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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LiveDataSource implements DataSource {

    private static final String TAG = "LiveDataSource";

    // URL(s) and Path(s)
    private static final String BASE_URL = "https://mobile.rival.run";
    private static final String LOGIN_PATH = "/recipes";
    private static final String RECIPES_PATH = "/recipes";

    // Header(s)
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String POST = "POST";

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
            Log.e(TAG, "", e);
        }

        String urlString = BASE_URL + LOGIN_PATH;
        try {
            String response = post(urlString, jsonObject);
            JSONObject responseJsonObject = new JSONObject(response);

            if (responseJsonObject.has("user_token")) {
                setUserToken(responseJsonObject.getString("user_token"));
            }
        } catch (IOException | JSONException | NullPointerException e) {
            Log.e(TAG, "login failed", e);
        }
    }

    @Override
    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getUserToken());
    }

    @Override
    public JSONObject getRecipes() {
        String urlString = BASE_URL + RECIPES_PATH;
        try {
            String response = getString(urlString);
            Log.d(TAG, response);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
        return new JSONObject();
    }

    @Override
    public JSONObject getRecipeImages() {
        return new JSONObject();
    }

    @Override
    public JSONObject getRecipe(String recipeId) {
        return new JSONObject();
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
        URL url = new URL(urlString);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Add Request Header(s)
        connection.setRequestMethod(POST);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
        connection.setDoOutput(true);

        // Send Post Request
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(jsonObject.toString());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (HttpsURLConnection.HTTP_OK <= responseCode && responseCode < HttpsURLConnection.HTTP_NO_CONTENT) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            return response.toString();
        }

        return null;
    }

}
