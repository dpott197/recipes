package com.rival.rivalrecipes.ui.recipe;

import java.io.Serializable;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeViewModel implements Serializable {

    private final String id;
    private final String name;
    private final String instructions;
    private final String smallUrl;
    private final String largeUrl;

    public RecipeViewModel(
            String id,
            String name,
            String instructions,
            String smallUrl,
            String largeUrl
    ) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.smallUrl = smallUrl;
        this.largeUrl = largeUrl;
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

    public String getSmallUrl() {
        return smallUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    @Override
    public String toString() {
        return name;
    }


}
