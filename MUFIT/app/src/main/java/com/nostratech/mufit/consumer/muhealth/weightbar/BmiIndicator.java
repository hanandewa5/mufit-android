package com.nostratech.mufit.consumer.muhealth.weightbar;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.utils.FontUtils;

public class BmiIndicator extends FrameLayout {

    TextView value;
    View bubble;

    public BmiIndicator(@NonNull Context context) {
        this(context, null);
    }

    public BmiIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }


    private void initViews(Context context){
        createIndicator(context);
        createValueLabel(context);
    }

    private void createIndicator(Context context){
        bubble = new View(context);

        //Set height & width
        int width = (int) getResources().getDimension(R.dimen.bmi_bubble_indicator_width);
        int height = (int) getResources().getDimension(R.dimen.bmi_bubble_indicator_height);

        bubble.setLayoutParams(new FrameLayout.LayoutParams(width, height));

        //Create bubble indicator background
        PaintDrawable drawable = new PaintDrawable();
        drawable.setCornerRadius(getResources().getDimension(R.dimen.bmi_bubble_indicator_corner));
        bubble.setBackground(drawable);

        addView(bubble);
    }

    private void createValueLabel(Context context){
        value = new TextView(context);

        //Set height & width to WRAP_CONTENT, gravity to CENTER
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        value.setLayoutParams(params);

        //Set font
        value.setTypeface(FontUtils.getTruenoLight(context));

        value.setTextColor(getResources().getColor(R.color.white));

        addView(value);
    }

    public void setValue(String text){
        value.setText(text);
    }

    public void setIndicatorColor(int color){
        bubble.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}