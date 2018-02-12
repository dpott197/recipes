package com.rival.rivalrecipes.ui.recipe;

import java.io.Serializable;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeViewModel implements Serializable {

    private final String id;
    private final String name;
    private final String instructions;
    private final String smallImageUrl;
    private final String largeImageUrl;

    public RecipeViewModel(
            String id,
            String name,
            String instructions,
            String smallImageUrl,
            String largeImageUrl
    ) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
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

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    @Override
    public String toString() {
        return name;
    }

}
