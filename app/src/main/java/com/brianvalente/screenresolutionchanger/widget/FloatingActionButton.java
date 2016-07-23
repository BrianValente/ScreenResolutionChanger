package com.brianvalente.screenresolutionchanger.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.brianvalente.screenresolutionchanger.R;

/**
 * Created by brianvalente on 7/19/16.
 */

public class FloatingActionButton extends ImageView {
    AttributeSet attributeSet;

    public FloatingActionButton(Context context) {
        super(context);
        init(context);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet = attrs;
        init(context);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attributeSet = attrs;
        init(context);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        attributeSet = attrs;
        init(context);
    }

    private void init(final Context context) {

        Drawable background = context.getResources().getDrawable(R.drawable.fab);
        if (attributeSet != null) {
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton);
            Drawable icon = a.getDrawable(R.styleable.FloatingActionButton_fab_icon);
            if (icon != null) setImageDrawable(icon);
            int color = a.getInt(R.styleable.FloatingActionButton_fab_color, 0);
            if (true) background.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        setBackground(background);
        setScaleType(ScaleType.CENTER_INSIDE);



        if (isInEditMode()) return;
        setClickable(true);
    }
}
