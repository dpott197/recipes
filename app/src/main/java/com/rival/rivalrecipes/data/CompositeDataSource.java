package com.rival.rivalrecipes.data;

import android.content.Context;

import com.rival.rivalrecipes.data.source.DataSource;
import com.rival.rivalrecipes.data.source.LiveDataSource;
import com.rival.rivalrecipes.data.source.MockDataSource;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.util.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * An array of sample (dummy) items.
     */
    public static final List<RecipeViewModel> ITEMS = new ArrayList<RecipeViewModel>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RecipeViewModel> ITEM_MAP = new HashMap<String, RecipeViewModel>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(RecipeViewModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static RecipeViewModel createDummyItem(int position) {
        return new RecipeViewModel(
                String.valueOf(position),
                "Item " + position,
                makeDetails(position),
                null,
                null
        );
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore instructions information here.");
        }
        return builder.toString();
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
