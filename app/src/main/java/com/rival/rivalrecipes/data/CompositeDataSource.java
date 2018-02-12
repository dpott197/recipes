package com.rival.rivalrecipes.data;

import android.content.Context;

import com.rival.rivalrecipes.data.source.DataSource;
import com.rival.rivalrecipes.data.source.LiveDataSource;
import com.rival.rivalrecipes.data.source.MockDataSource;
import com.rival.rivalrecipes.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Helper class for providing sample name for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CompositeDataSource implements DataSource {

    protected static CompositeDataSource sCompositeDataSource;

    private Context applicationContext;
    private LiveDataSource liveDataSource;
    private MockDataSource mockDataSource;

    public static void init(Context applicationContext, LiveDataSource liveDataSource, MockDataSource mockDataSource) {
        sCompositeDataSource = new CompositeDataSource(applicationContext, mockDataSource, liveDataSource);
    }

    CompositeDataSource(
            Context applicationContext,
            MockDataSource mockDataSource,
            LiveDataSource liveDataSource
    ) {
        this.applicationContext = applicationContext;
        this.mockDataSource = mockDataSource;
        this.liveDataSource = liveDataSource;
    }

    public static CompositeDataSource getInstance() {
        return sCompositeDataSource;
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public void login(String email, String password) {
        if (NetworkUtils.isConnected(applicationContext)) {
            liveDataSource.login(email, password);
        } else {
            mockDataSource.login(email, password);
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public boolean isLoggedIn() {
        if (NetworkUtils.isConnected(applicationContext)) {
            return liveDataSource.isLoggedIn();
        } else {
            return mockDataSource.isLoggedIn();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipes() throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return liveDataSource.getRecipes();
        } else {
            return mockDataSource.getRecipes();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipeImages() throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return liveDataSource.getRecipeImages();
        } else {
            return mockDataSource.getRecipeImages();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipe(String recipeId) throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return liveDataSource.getRecipe(recipeId);
        } else {
            return mockDataSource.getRecipe(recipeId);
        }
    }

}
