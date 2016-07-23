package com.brianvalente.screenresolutionchanger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.brianvalente.screenresolutionchanger.widget.BottomNavigationBarItem;
import com.brianvalente.screenresolutionchanger.widget.FloatingActionButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by brianvalente on 7/18/16.
 */

public class MainActivity extends Activity {
    private final int REVERT_SECS = 5;
    private final String PHYSICAL_SIZE    = "Physical size: ";
    private final String OVERRIDE_SIZE    = "Override size: ";
    private final String PHYSICAL_DENSITY = "Physical density: ";
    private final String OVERRIDE_DENSITY = "Override density: ";

    private FloatingActionButton       mSettingsFAB;
    private RecyclerView               mResolutionsRV;
    private RecyclerView.LayoutManager mLayoutManager;
    private ResolutionsAdapter         mResolutionsAdapter;
    private AlertDialog                mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBarItem BnbiDpi = (BottomNavigationBarItem) findViewById(R.id.bnb_dpi);

        mSettingsFAB   = (FloatingActionButton) findViewById(R.id.fab_settings);
        mResolutionsRV = (RecyclerView)         findViewById(R.id.resolutionsRV);

        mLayoutManager      = new LinearLayoutManager(this);
        mResolutionsAdapter = new ResolutionsAdapter(this);

        mSettingsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResolutionSettingsActivity.class);
                startActivity(intent);
            }
        });

        mResolutionsRV.setHasFixedSize(true);
        mResolutionsRV.setLayoutManager(mLayoutManager);
        mResolutionsRV.setAdapter(mResolutionsAdapter);

        BnbiDpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_LONG).show();
            }
        });
    }

    void changeResolution(Resolution resolution, boolean showConfirmationDialog) {
        final Resolution previousResolution = getActualResolution();

        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes("wm size " + resolution.getResolution() + "\n");
            outputStream.flush();

            if (App.sharedPreferences.getBoolean("resolution_scaledpi", true)) {
                int actualDPI = getActualDPI();
                int newDPI    = (resolution.getWidth() * actualDPI) / previousResolution.getWidth();
                outputStream.writeBytes("wm density " + newDPI + "\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch(IOException e){
            throw new RuntimeException(e);
        } catch(InterruptedException e){
            throw new RuntimeException(e);
        }

        if (showConfirmationDialog) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            final CountDownTimer timer = new CountDownTimer(REVERT_SECS * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    mDialog.setMessage(getString(R.string.dialog_resolution_keep_message, l / 1000));
                    mDialog.hide();
                    mDialog.show();
                }

                @Override
                public void onFinish() {
                    mDialog.dismiss();
                    changeResolution(previousResolution, false);
                }
            };

            alertDialog.setTitle(getString(R.string.dialog_resolution_keep_title));
            alertDialog.setMessage(getString(R.string.dialog_resolution_keep_message, REVERT_SECS));
            alertDialog.setPositiveButton(getString(R.string.dialog_resolution_keep_btn_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    timer.cancel();
                }
            });
            alertDialog.setNegativeButton(getString(R.string.dialog_resolution_keep_btn_negative), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    timer.onFinish();
                    timer.cancel();
                }
            });
            alertDialog.setCancelable(false);

            mDialog = alertDialog.create();
            timer.start();
        }
    }

    private Resolution getActualResolution() {
        String dataString = "";

        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            DataInputStream inputStream = new DataInputStream(su.getInputStream());

            outputStream.writeBytes("wm size\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();

            int availableBytes = inputStream.available();

            if (availableBytes != 0) {
                byte[] data = new byte[availableBytes];
                inputStream.read(data);
                dataString = new String(data);
            }

            if (dataString.contains(OVERRIDE_SIZE))      dataString = dataString.substring(dataString.indexOf(OVERRIDE_SIZE) + OVERRIDE_SIZE.length(), dataString.length());
            else if (dataString.contains(PHYSICAL_SIZE)) dataString = dataString.substring(PHYSICAL_SIZE.length(), dataString.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Resolution(dataString);
    }

    private int getActualDPI() {
        String dataString = "";

        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            DataInputStream inputStream = new DataInputStream(su.getInputStream());

            outputStream.writeBytes("wm density\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();

            int availableBytes = inputStream.available();

            if (availableBytes != 0) {
                byte[] data = new byte[availableBytes];
                inputStream.read(data);
                dataString = new String(data);
            }

            if (dataString.contains(OVERRIDE_DENSITY))      dataString = dataString.substring(dataString.indexOf(OVERRIDE_DENSITY) + OVERRIDE_DENSITY.length(), dataString.length());
            else if (dataString.contains(PHYSICAL_DENSITY)) dataString = dataString.substring(PHYSICAL_DENSITY.length(), dataString.length());

            char lastChar = dataString.charAt(dataString.length()-1);
            if (lastChar == '\n') dataString = dataString.substring(0, dataString.length()-1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Integer.parseInt(dataString);
    }
}
