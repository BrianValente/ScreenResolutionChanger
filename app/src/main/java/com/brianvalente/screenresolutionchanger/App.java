package com.brianvalente.screenresolutionchanger;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by brianvalente on 7/22/16.
 */

public class App extends Application {
    public static SharedPreferences        sharedPreferences;
    public static SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sharedPreferences       = getSharedPreferences("app", 0);
        sharedPreferencesEditor = sharedPreferences.edit();

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int appVersion = packageInfo.versionCode;
            switch (sharedPreferences.getInt("app_version", 0)) {
                case 0:
                    sharedPreferencesEditor.putBoolean("resolution_scaledpi", true);
                    break;
                case 6:
                    // ...
                    break;
            }
            sharedPreferencesEditor.putInt("app_version", appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
