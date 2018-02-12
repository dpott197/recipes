package com.rival.rivalrecipes.data;

import android.content.Context;

import com.rival.rivalrecipes.data.source.DataSource;
import com.rival.rivalrecipes.data.source.LiveDataSource;
import com.rival.rivalrecipes.data.source.MockDataSource;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.util.NetworkUtils;

import org.json.JSONObject;

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
public class DataManager implements DataSource {

    protected static DataManager sDataManager;

    private Context applicationContext;
    private MockDataSource mockDataSource;
    private LiveDataSource liveDataSource;

    public static void init(Context applicationContext, MockDataSource mockDataSource, LiveDataSource liveDataSource) {
        sDataManager = new DataManager(applicationContext, mockDataSource, liveDataSource);
    }

    DataManager(
            Context applicationContext,
            MockDataSource mockDataSource,
            LiveDataSource liveDataSource
    ) {
        this.applicationContext = applicationContext;
        this.mockDataSource = mockDataSource;
        this.liveDataSource = liveDataSource;
    }

    public static DataManager getInstance() {
        return sDataManager;
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
        ITEM_MAP.put(item.id, item);
    }

    private static RecipeViewModel createDummyItem(int position) {
        return new RecipeViewModel(String.valueOf(position), "Item " + position, makeDetails(position));
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
    public JSONObject getRecipes() {
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
    public JSONObject getRecipeImages() {
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
    public JSONObject getRecipe(String recipeId) {
        if (NetworkUtils.isConnected(applicationContext)) {
            return liveDataSource.getRecipe(recipeId);
        } else {
            return mockDataSource.getRecipe(recipeId);
        }
    }

}
