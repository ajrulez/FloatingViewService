package com.alexstyl.floatingviewservicelib.util;

import android.content.res.Resources;

/**
 * Utilities for the OverlayService
 * <p>Created by alexstyl on 23/03/15.</p>
 */
public class Utils {


    /**
     * Returns the height of the status bar
     *
     * @param res The resources to use
     * @return
     */
    public static int getStatusBarHeight(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Returns the height of the system's navigation bar
     *
     * @param res The resources to use
     * @return
     */
    public static int getNavigationBarHeight(Resources res) {
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
