package com.rival.rivalrecipes.ui.recipe.list;

import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.data.CompositeDataSource;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailActivity;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailFragment;


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

    private String TAG = "RecipeListActivity";

    private RecipeListContract.Presenter presenter;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean isTwoPane;

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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, CompositeDataSource.ITEMS, isTwoPane));
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
    }

    @Override
    public void onLoaderReset(Loader<List<RecipeViewModel>> loader) {
        Log.d(TAG, "onLoadFinished");
    }
    //endregion

    @Override
    public void showRecipeViewModels(List<RecipeViewModel> viewModels) {

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final List<RecipeViewModel> mValues;
        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeViewModel item = (RecipeViewModel) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item.getId());
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      List<RecipeViewModel> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getId());
            holder.mNameTextView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mNameTextView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.identifierTextView);
                mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
            }
        }
    }

}
