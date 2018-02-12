package com.rival.rivalrecipes.ui.recipe.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.ui.Router;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailFragment;

import java.util.List;

/**
 * Created by darren on 2/11/18.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final RecipeListActivity parentActivity;
    private final List<RecipeViewModel> recipeViewModels;
    private final boolean isTwoPane;

    private RecipeViewModel selectedRecipeViewModel;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(RecipeDetailFragment.ARG_RECIPE_VIEW_MODEL, selectedRecipeViewModel);
                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Router.toRecipeDetail(context, selectedRecipeViewModel);
            }
        }
    };

    SimpleItemRecyclerViewAdapter(
            RecipeListActivity parent,
            List<RecipeViewModel> recipeViewModels,
            boolean twoPane
    ) {
        this.recipeViewModels = recipeViewModels;
        this.parentActivity = parent;
        this.isTwoPane = twoPane;
    }

    @Override
    public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
        selectedRecipeViewModel = recipeViewModels.get(position);

        holder.idTextView.setText(recipeViewModels.get(position).getId());
        holder.nameTextView.setText(recipeViewModels.get(position).getName());

        holder.itemView.setTag(recipeViewModels.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return recipeViewModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView idTextView;
        final TextView nameTextView;

        ViewHolder(View view) {
            super(view);
            idTextView = view.findViewById(R.id.identifierTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
        }
    }

}
