package com.nostratech.mufit.consumer.muhealth.weightbar;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.utils.FontUtils;

public class BarView extends LinearLayout {

    private TextView numberLabel;
//    private View bar;
    private TextView label;
    private int color;


    private FrameLayout frameLayout;
    private View bar;
//    private TextView value;

    public BarView(Context context) {
        super(context);
        inflateLayout(context);
//        initViews(context);
    }

    private void initViews(Context context){
        frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        addView(frameLayout);

        bar= new View(context);
        bar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        frameLayout.addView(bar);

        numberLabel = new TextView(context);
        numberLabel.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        numberLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        numberLabel.setTextSize(10);
        frameLayout.addView(numberLabel);

        label = new TextView(context);
        label.setTextSize(getResources().getDimension(R.dimen.multicolorbar_label_textsize));
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        label.setTypeface(FontUtils.getTruenoLight(context));
        addView(label);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.bar_view, this);

        numberLabel = findViewById(R.id.barView_numberLabel);
        bar = findViewById(R.id.barView_bar);
        label = findViewById(R.id.barView_label);
    }

    public void setNumberLabel(String number) {
        numberLabel.setText(number);
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void setBarColor(int color) {
        this.bar.setBackgroundColor(color);
        this.color = color;
    }

    public void setBarHeight(int height) {
        bar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    //    public void setCornerRoundedLeft(){
//        bar.setBackgroundResource(R.drawable.view_rounded_bar_right);
//    }
//
//    public void setCornerRoundedRight(){
//        bar.setBackgroundResource(R.drawable.view_rounded_bar_right);
//    }
//
    public void setCornerRoundedLeft(int radius) {
        PaintDrawable drawable = new PaintDrawable(color);
        drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        bar.setBackground(drawable);
    }

    public void setCornerRoundedRight(int radius) {
        PaintDrawable drawable = new PaintDrawable(color);
        drawable.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        bar.setBackground(drawable);
    }

}
