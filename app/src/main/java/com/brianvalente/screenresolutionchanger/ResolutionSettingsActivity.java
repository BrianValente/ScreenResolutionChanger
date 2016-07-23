package com.brianvalente.screenresolutionchanger;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by brianvalente on 7/22/16.
 */

public class ResolutionSettingsActivity extends Activity {
    private ViewGroup mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resolution_settings);
    }
}
