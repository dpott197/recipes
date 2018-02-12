package com.rival.rivalrecipes.ui.recipe;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeViewModel {

    private final String id;
    private final String name;
    private final String instructions;
    private final String sm_url;
    private final String lg_url;

    public RecipeViewModel(
            String id,
            String name,
            String instructions,
            String sm_url,
            String lg_url
    ) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.sm_url = sm_url;
        this.lg_url = lg_url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return name;
    }


}
