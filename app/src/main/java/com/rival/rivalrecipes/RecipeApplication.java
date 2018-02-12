package com.rival.rivalrecipes;

import android.app.Application;

import com.rival.rivalrecipes.data.CompositeDataSource;
import com.rival.rivalrecipes.data.source.LiveDataSource;
import com.rival.rivalrecipes.data.source.MockDataSource;

public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CompositeDataSource.init(this, new LiveDataSource(), new MockDataSource(this));
    }

}
