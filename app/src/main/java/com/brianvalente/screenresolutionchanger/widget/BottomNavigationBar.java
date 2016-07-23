package com.brianvalente.screenresolutionchanger.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brianvalente.screenresolutionchanger.R;

import java.util.ArrayList;

/**
 * Created by brianvalente on 7/19/16.
 */

public class BottomNavigationBar extends LinearLayout {
    private Context                            mContext;
    private AttributeSet                       mAttributeSet;
    private BottomNavigationBarItem            mActualItem;
    private ArrayList<BottomNavigationBarItem> mItems         = new ArrayList<>();
    private int                                mDefaultItemId;

    public BottomNavigationBar(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttributeSet = attrs;
        init(context);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAttributeSet = attrs;
        init(context);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mAttributeSet = attrs;
        init(context);
    }

    private void init(Context ctx) {
        mContext = ctx;

        if (mAttributeSet != null) {
            TypedArray a = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.BottomNavigationBar);
            mDefaultItemId = a.getResourceId(R.styleable.BottomNavigationBar_bnb_defaultItem, 0);
            a.recycle();
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (int i = 0; i < getChildCount(); i++) {
                    BottomNavigationBarItem item = (BottomNavigationBarItem) getChildAt(i);
                    mItems.add(item);
                }

                mActualItem = (BottomNavigationBarItem) findViewById(mDefaultItemId);
                if (mActualItem == null) mActualItem = mItems.get(0);

                mActualItem.setActiveInanimated();

                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
