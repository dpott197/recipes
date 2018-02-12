package com.rival.rivalrecipes.data;

import android.content.Context;

import com.rival.rivalrecipes.data.source.DataSource;
import com.rival.rivalrecipes.data.source.RemoteDataSource;
import com.rival.rivalrecipes.data.source.LocalDataSource;
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
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    public static void init(Context applicationContext, RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        sCompositeDataSource = new CompositeDataSource(applicationContext, localDataSource, remoteDataSource);
    }

    CompositeDataSource(
            Context applicationContext,
            LocalDataSource localDataSource,
            RemoteDataSource remoteDataSource
    ) {
        this.applicationContext = applicationContext;
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static CompositeDataSource getInstance() {
        return sCompositeDataSource;
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public boolean login(String email, String password) {
        if (NetworkUtils.isConnected(applicationContext)) {
            return remoteDataSource.login(email, password);
        } else {
            return localDataSource.login(email, password);
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public boolean isLoggedIn() {
        if (NetworkUtils.isConnected(applicationContext)) {
            return remoteDataSource.isLoggedIn();
        } else {
            return localDataSource.isLoggedIn();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipes() throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return remoteDataSource.getRecipes();
        } else {
            return localDataSource.getRecipes();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipeImages() throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return remoteDataSource.getRecipeImages();
        } else {
            return localDataSource.getRecipeImages();
        }
    }

    /**
     * Always perform asynchronously
     */
    @Override
    public JSONObject getRecipe(String recipeId) throws IOException, JSONException {
        if (NetworkUtils.isConnected(applicationContext)) {
            return remoteDataSource.getRecipe(recipeId);
        } else {
            return localDataSource.getRecipe(recipeId);
        }
    }

}
