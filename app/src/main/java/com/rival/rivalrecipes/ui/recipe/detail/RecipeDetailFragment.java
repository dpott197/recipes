package com.rival.rivalrecipes.ui.recipe.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.list.RecipeListActivity;

/**
 * A fragment representing a single RecipeViewModel detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_RECIPE_VIEW_MODEL = "ARG_RECIPE_VIEW_MODEL";

    /**
     * The recipe view model this fragment is presenting.
     */
    private RecipeViewModel recipeViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(RecipeViewModel recipeViewModel) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(RecipeDetailFragment.ARG_RECIPE_VIEW_MODEL, recipeViewModel);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_RECIPE_VIEW_MODEL)) {
            // Load the recipe name specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load name from a name provider.
            recipeViewModel = (RecipeViewModel) (getArguments().getSerializable(ARG_RECIPE_VIEW_MODEL));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(recipeViewModel.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the recipe name as text in a TextView.
        if (recipeViewModel != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(recipeViewModel.getInstructions());
        }

        return rootView;
    }
}
