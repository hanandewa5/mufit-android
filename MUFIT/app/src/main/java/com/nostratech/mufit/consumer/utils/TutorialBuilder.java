package com.nostratech.mufit.consumer.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;

import com.nostratech.mufit.consumer.R;

import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class TutorialBuilder {

    private final static int SHOWCASE_INTERVAL = 50;
    private final static int SHOWCASE_FADE_DURATION = 500;
    private final int showcaseColor;
    private final Activity activity;
    private final MaterialShowcaseSequence sequence;

    public TutorialBuilder(Activity activity, Typeface textStyle){
        this.showcaseColor = activity.getResources().getColor(R.color.showcase_mask_color);
        this.activity = activity;
        this.sequence = new MaterialShowcaseSequence(activity);

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(SHOWCASE_INTERVAL);
        config.setDismissTextStyle(textStyle);
        config.setFadeDuration(SHOWCASE_FADE_DURATION);

        sequence.setConfig(config);
    }

    public void start(){
        sequence.start();
    }

    public void addSequenceItem(View targetView,
                                String dismissText,
                                String contentText,
                                IShowcaseListener listener){

        MaterialShowcaseView.Builder baseBuilder = getBaseBuilder(targetView,
                contentText,
                dismissText,
                listener);

        sequence.addSequenceItem(baseBuilder.build());
    }

    public void addSequenceItemRectangleShape(View targetView,
                                              String dismissText,
                                              String contentText,
                                              IShowcaseListener listener,
                                              boolean rectangle){

        MaterialShowcaseView.Builder baseBuilder = getBaseBuilder(targetView, contentText, dismissText, listener);
        baseBuilder.withRectangleShape(rectangle);

        sequence.addSequenceItem(baseBuilder.build());
    }

    private MaterialShowcaseView.Builder getBaseBuilder(View targetView,
                                                        String contentText,
                                                        String dismissText,
                                                        IShowcaseListener listener){
        MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(activity)
                .setTarget(targetView)
                .setDismissText(dismissText)
                .setContentText(contentText)
                .renderOverNavigationBar()
                .setDismissOnTouch(true)
                .setMaskColour(showcaseColor);

        if (listener != null) builder.setListener(listener);

        return builder;
    }

    //TODO: remove method
    public static MaterialShowcaseView getShowcaseViewBuilder(Activity activity,
                                                              View v,
                                                              String dismissText,
                                                              String contentText,
                                                              boolean rectangle,
                                                              IShowcaseListener listener){

        MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(activity)
                .setTarget(v)
                .setDismissText(dismissText)
                .setContentText(contentText)
                .setMaskColour(activity.getResources().getColor(R.color.showcase_mask_color));

        if (listener != null) builder.setListener(listener);
        if (rectangle) builder.withRectangleShape(rectangle);

        return builder.build();
    }

}
