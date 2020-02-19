package com.nostratech.mufit.consumer.muhealth.weightbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.detailtrainer.helper.LayoutUtils;

import java.util.List;

public class MultiColorBar extends LinearLayout {

    public static final String TAG = MultiColorBar.class.getSimpleName();

    private double startValue;
    private double endValue;


    public MultiColorBar(Context context) {
        super(context);
    }


    public MultiColorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiColorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValueRange(int startValue, int endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public double getRange() {
        return endValue - startValue;
    }

    public void setData(List<BarData> barDataList, double value) {
        int width = getMeasuredWidth();
        Log.i(TAG, "Creating Layout with width of: " + width + "px");

        createArrowIndicator(value, getRange());
        createMultiColorBar(barDataList, getRange());
    }

    /**
     * Creates the colored bars
     * @param barDataList - {@link BarData} models that define how to divide the bars and their length
     * @param valueRange
     */
    private void createMultiColorBar(List<BarData> barDataList, double valueRange) {
        BarData previousBar = null;

        LinearLayout barLayout = new LinearLayout(getContext());
        barLayout.setOrientation(HORIZONTAL);
        barLayout.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i =0; i< barDataList.size(); i++) {

            BarData data = barDataList.get(i);
            //Calculate length of bar as (this bar's max value) - (min value if this is the first bar, else previous bar's max value)
            double barValueLength = data.getCutoff() - (previousBar != null ? previousBar.getCutoff() : startValue);

            //Allocated width for this bar, as a fraction of the whole bar's range of values
            double barWidthFraction = barValueLength / valueRange;
            Log.i(TAG, "allocated width: " + barWidthFraction);

            int viewWidth = (int) (barWidthFraction * getMeasuredWidth());

            LayoutParams params = new LayoutParams(
                    viewWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            BarView barView = new BarView(getContext());

            barView.setBarColor(data.getColor());
            if(i == 0) barView.setCornerRoundedLeft(25);
            else if(i == barDataList.size() - 1) barView.setCornerRoundedRight(25);

            barView.setLayoutParams(params);
            barView.setLabel(data.getLabel());
            barView.setBarHeight(LayoutUtils.dpToPx(15));
            barView.setNumberLabel(String.valueOf(data.getCutoff()));

            barLayout.addView(barView);

            previousBar = data;
        }

        addView(barLayout);
    }

    private void createArrowIndicator(double value, double valueRange){
        BmiIndicator indicator = new BmiIndicator(getContext());
        indicator.setValue(String.valueOf(value));
        indicator.setIndicatorColor(getResources().getColor(R.color.muhealth_normal));

        //Calculate offset for arrow indicator
        double marginFraction = (value - startValue) / valueRange;
        int marginLeft = (int) (marginFraction * getMeasuredWidth());
        int indicatorWidth = getResources().getDimensionPixelSize(R.dimen.bmi_bubble_indicator_width);

        Log.i(TAG, "Arrow margin Left " + marginLeft + " -- arrow width: " + indicatorWidth);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(marginLeft - indicatorWidth / 2);
        Log.i(TAG, "W: " + params.width + " H: " + params.height);

        indicator.setLayoutParams(params);
        addView(indicator);
    }
}
