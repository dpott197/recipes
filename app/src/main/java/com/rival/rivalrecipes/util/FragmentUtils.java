package com.rival.rivalrecipes.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by darren on 2/11/18.
 */

public class FragmentUtils {

    private static final String TAG = FragmentUtils.class.getSimpleName();

    public static void replace(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        try {
            fragmentManager.beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
        } catch (IllegalStateException e) {
            Log.e(TAG, "", e);
        }
    }
}
