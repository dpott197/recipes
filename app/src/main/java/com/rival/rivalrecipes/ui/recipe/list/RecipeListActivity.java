package com.rival.rivalrecipes.ui.recipe.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.data.CompositeDataSource;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item instructions. On tablets, the activity presents the list of items and
 * item instructions side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeListContract.View,
        LoaderManager.LoaderCallbacks<List<RecipeViewModel>> {

    private String TAG = RecipeListActivity.class.getSimpleName();

    private RecipeListContract.Presenter presenter;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean isTwoPane;
    private List<RecipeViewModel> viewModels = new ArrayList<RecipeViewModel>();
    private SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            isTwoPane = true;
        }

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        presenter = new RecipeListPresenter(this, new RecipeListRepository(CompositeDataSource.getInstance()));
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter(this, viewModels, isTwoPane);
        recyclerView.setAdapter(adapter);
    }

    //region LoaderCallbacks
    @Override
    public Loader<List<RecipeViewModel>> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader");
        return new AsyncTaskLoader<List<RecipeViewModel>>(this) {
            @Override
            public List<RecipeViewModel> loadInBackground() {
                return presenter.showRecipeViewModels();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<RecipeViewModel>> loader, List<RecipeViewModel> viewModels) {
        Log.d(TAG, "onLoadFinished: " + viewModels.toString());
        showRecipeViewModels(viewModels);
    }

    @Override
    public void onLoaderReset(Loader<List<RecipeViewModel>> loader) {
        Log.d(TAG, "onLoadFinished");
    }
    //endregion

    @Override
    public void showRecipeViewModels(List<RecipeViewModel> viewModels) {
        this.viewModels.clear();
        this.viewModels.addAll(viewModels);
        adapter.notifyDataSetChanged();
    }

}
