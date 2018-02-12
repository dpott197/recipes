package com.rival.rivalrecipes.ui.recipe.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.ui.Router;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailFragment;
import com.rival.rivalrecipes.util.FragmentUtils;

import java.util.List;

/**
 * Created by darren on 2/11/18.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final RecipeListActivity parentActivity;
    private final List<RecipeViewModel> recipeViewModels;
    private final boolean isTwoPane;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.idTextView.setText(recipeViewModels.get(position).getId());
        holder.nameTextView.setText(recipeViewModels.get(position).getName());

        holder.itemView.setTag(recipeViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeViewModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView idTextView;
        final TextView nameTextView;

        ViewHolder(View view) {
            super(view);
            idTextView = view.findViewById(R.id.identifierTextView);
            nameTextView = view.findViewById(R.id.nameTextView);

            view.setOnClickListener(this);
            idTextView.setOnClickListener(this);
            nameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeViewModel recipeViewModel = recipeViewModels.get(this.getAdapterPosition());

            if (isTwoPane) {
                FragmentUtils.replace(
                        parentActivity.getSupportFragmentManager(),
                        R.id.recipe_detail_container,
                        RecipeDetailFragment.newInstance(recipeViewModel)
                );
            } else {
                Router.toRecipeDetail(view.getContext(), recipeViewModel);
            }
        }

    }

}
