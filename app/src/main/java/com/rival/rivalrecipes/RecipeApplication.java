package com.rival.rivalrecipes;

import android.app.Application;

import com.rival.rivalrecipes.data.CompositeDataSource;
import com.rival.rivalrecipes.data.source.RemoteDataSource;
import com.rival.rivalrecipes.data.source.LocalDataSource;

public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CompositeDataSource.init(this, new RemoteDataSource(), new LocalDataSource(this));
    }

}
