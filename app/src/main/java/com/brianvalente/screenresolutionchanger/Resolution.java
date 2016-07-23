package com.brianvalente.screenresolutionchanger;

import android.support.annotation.Nullable;

/**
 * Created by brianvalente on 7/20/16.
 */

public class Resolution {
    private String mResolution;
    private String mResolutionName;

    Resolution(String resolution) {
        setResolution(resolution);
    }

    Resolution(String resolution, @Nullable String resolutionName) {
        setResolution(resolution);
        setResolutionName(resolutionName);
    }

    private void setResolution(String resolution) {
        mResolution = resolution;
    }

    private void setResolutionName(String resolutionName) {
        mResolutionName = resolutionName == null || resolutionName.contentEquals("")? null : resolutionName;
    }

    String getResolution() {
        return mResolution;
    }

    String getResolutionName() {
        return mResolutionName;
    }

    int getWidth() {
        int xPos = mResolution.indexOf("x");
        return Integer.parseInt(mResolution.substring(0, xPos));
    }
}
