package com.brianvalente.screenresolutionchanger;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by brianvalente on 7/20/16.
 */

public class Dialog {
    private Activity  mActivity;
    private View      mView;
    private ViewGroup mContent;
    private TextView  mTitleTV;
    private TextView  mMessageTV;
    private Button    mPositiveBTN;

    public Dialog() {

    }

    public Dialog(Activity activity) {
        mActivity = activity;
        mContent = (ViewGroup) mActivity.findViewById(android.R.id.content);
        mView = LayoutInflater.from(mActivity).inflate(R.layout.dialog, mContent, false);

        mTitleTV     = (TextView) mView.findViewById(R.id.title);
        mMessageTV   = (TextView) mView.findViewById(R.id.message);
        mPositiveBTN = (Button)   mView.findViewById(R.id.positive);
    }

    public void setTitle(String title) {
        mTitleTV.setText(title);
    }

    public void setMessage(String message) {
        mMessageTV.setText(message);
    }

    public void setPositiveButton(String text, View.OnClickListener action) {
        mPositiveBTN.setText(text);
        mPositiveBTN.setOnClickListener(action);
    }

    public void show() {
        mContent.addView(mView);
    }
}