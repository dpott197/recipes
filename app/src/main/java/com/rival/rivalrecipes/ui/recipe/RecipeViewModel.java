package com.rival.rivalrecipes.ui.recipe;

/**
 * Created by darren on 2/11/18.
 */

public class RecipeViewModel {

    public final String id;
    public final String name;
    public final String instructions;

    public RecipeViewModel(String id, String name, String instructions) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return name;
    }

}
