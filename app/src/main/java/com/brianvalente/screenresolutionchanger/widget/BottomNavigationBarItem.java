package com.brianvalente.screenresolutionchanger.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brianvalente.screenresolutionchanger.R;

/**
 * Created by brianvalente on 7/19/16.
 */

public class BottomNavigationBarItem extends RelativeLayout {
    private BottomNavigationBar mBottomNavigationBar;
    private View                mLayout;
    private Context             mContext;
    private AttributeSet        mAttributeSet;
    private ImageView           mIconIV;
    private TextView            mLabelTV;
    private Drawable            mIcon;
    private String              mLabel;

    public BottomNavigationBarItem(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttributeSet = attrs;
        init(context);
    }

    public BottomNavigationBarItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAttributeSet = attrs;
        init(context);
    }

    public BottomNavigationBarItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mAttributeSet = attrs;
        init(context);
    }

    private void init(final Context ctx) {
        mContext = ctx;

        if (mAttributeSet != null) {
            TypedArray a = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.BottomNavigationBarItem);
            mIcon = a.getDrawable(R.styleable.BottomNavigationBarItem_bnbi_icon);
            mLabel = a.getString(R.styleable.BottomNavigationBarItem_bnbi_title);
            mLayout = ((Activity) mContext).findViewById(a.getResourceId(R.styleable.BottomNavigationBarItem_bnbi_layout, 0));
            a.recycle();
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomNavigationBar.LayoutParams params = (BottomNavigationBar.LayoutParams) getLayoutParams();
                params.weight = 1;
                setLayoutParams(params);

                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.bnb_item, BottomNavigationBarItem.this, true);

                //addView(view);

                mIconIV              = (ImageView)           view.findViewById(R.id.icon);
                mLabelTV             = (TextView)            view.findViewById(R.id.label);
                mBottomNavigationBar = (BottomNavigationBar) getParent();

                if (mIcon != null)  mIconIV.setImageDrawable(mIcon);
                if (mLabel != null) mLabelTV.setText(mLabel);

                setClickable(true);

                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public String getLabel() {
        return mLabel;
    }

    public void setActive() {
        setActive(200);
    }

    public void setActiveInanimated() {
        setActive(0);
    }

    private void setActive(int duration) {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);

                float iconTopMargin = 10 - (interpolatedTime * 1);
                float labelTextSize = (float) (12.5 + (interpolatedTime * 2));

                float textSp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, labelTextSize, getResources().getDisplayMetrics());

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIconIV.getLayoutParams();
                params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iconTopMargin, getResources().getDisplayMetrics());

                mIconIV.setLayoutParams(params);
                mIconIV.setColorFilter(Color.WHITE);
                mLabelTV.setTextSize(labelTextSize);
                mLabelTV.setTextColor(Color.WHITE);
            }
        };
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(duration);
        startAnimation(animation);
    }

    public View getView() {
        return null;
    }
}
