package com.nostratech.mufit.consumer.muhealth.weightbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nostratech.mufit.consumer.R;

public class ArrowIndicator extends LinearLayout {

    TextView value;
    ImageView arrow;

    public ArrowIndicator(Context context) {
        super(context);
        inflateLayout(context);
    }

    public ArrowIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context);
    }

    public ArrowIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }

    public ArrowIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateLayout(context);
    }

    private void inflateLayout(Context context){
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_arrow_indicator, this);
        value = findViewById(R.id.arrowIndicator_value);
        arrow = findViewById(R.id.arrowIndicator_arrow);
    }

    public void setValue(String text){
        value.setText(text);
    }
}
