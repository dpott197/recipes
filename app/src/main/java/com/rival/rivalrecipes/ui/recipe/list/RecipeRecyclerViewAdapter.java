package com.rival.rivalrecipes.ui.recipe.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rival.rivalrecipes.R;
import com.rival.rivalrecipes.ui.Router;
import com.rival.rivalrecipes.ui.recipe.RecipeViewModel;
import com.rival.rivalrecipes.ui.recipe.detail.RecipeDetailFragment;
import com.rival.rivalrecipes.util.FragmentUtils;

import java.util.List;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final RecipeListActivity parentActivity;
    private final List<RecipeViewModel> recipeViewModels;
    private final boolean isTwoPane;

    RecipeRecyclerViewAdapter(
            RecipeListActivity parent,
            List<RecipeViewModel> recipeViewModels,
            boolean twoPane
    ) {
        this.recipeViewModels = recipeViewModels;
        this.parentActivity = parent;
        this.isTwoPane = twoPane;
    }

    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new RecipeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeRecyclerViewAdapter.ViewHolder holder, int position) {
        RecipeViewModel viewModel = recipeViewModels.get(position);
        holder.nameTextView.setText(viewModel.getName());
        holder.itemView.setTag(recipeViewModels.get(position));

        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .override(48, 48);

        Glide.with(parentActivity)
                .applyDefaultRequestOptions(myOptions)
                .load(viewModel.getSmallImageUrl())
                .into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return recipeViewModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameTextView;
        final ImageView thumbnailImageView;

        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            thumbnailImageView = view.findViewById(R.id.thumbnailImageView);

            view.setOnClickListener(this);
            nameTextView.setOnClickListener(this);
            thumbnailImageView.setOnClickListener(this);
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
