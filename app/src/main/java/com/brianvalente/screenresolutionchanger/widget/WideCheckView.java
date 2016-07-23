package com.brianvalente.screenresolutionchanger.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brianvalente.screenresolutionchanger.App;
import com.brianvalente.screenresolutionchanger.R;

/**
 * Created by brianvalente on 7/22/16.
 */

public class WideCheckView extends RelativeLayout {
    private Context      mContext;
    private AttributeSet mAttributeSet;
    private String       mTitle;
    private String       mSummary;
    private String       mKey;
    private TextView     mTitleTV;
    private TextView     mSummaryTV;
    private CheckBox     mCheckBox;
    private ViewGroup    mViewGroup;

    public WideCheckView(Context context) {
        super(context);
        init(context);
    }

    public WideCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttributeSet = attrs;
        init(context);
    }

    public WideCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAttributeSet = attrs;
        init(context);
    }

    public WideCheckView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mAttributeSet = attrs;
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        if (mAttributeSet != null) {
            TypedArray a = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.WideCheckView);
            mTitle = a.getString(R.styleable.WideCheckView_wcv_title);
            mSummary = a.getString(R.styleable.WideCheckView_wcv_summary);
            mKey = a.getString(R.styleable.WideCheckView_wcv_key);
            a.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.widecheckview, this, false);

        mViewGroup = (ViewGroup) view.findViewById(R.id.widecheckview);
        mTitleTV   = (TextView)  view.findViewById(R.id.title);
        mSummaryTV = (TextView)  view.findViewById(R.id.summary);
        mCheckBox  = (CheckBox)  view.findViewById(R.id.checkbox);

        addView(view);

        mTitleTV  .setText(mTitle);
        mSummaryTV.setText(mSummary);

        if (isInEditMode()) return;

        mViewGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newValue = !App.sharedPreferences.getBoolean(mKey, false);
                App.sharedPreferencesEditor.putBoolean(mKey, newValue);
                App.sharedPreferencesEditor.apply();
                mCheckBox.setChecked(newValue);
            }
        });

        mCheckBox.setChecked(App.sharedPreferences.getBoolean(mKey, false));
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.sharedPreferencesEditor.putBoolean(mKey, b);
                App.sharedPreferencesEditor.apply();
            }
        });
    }
}
