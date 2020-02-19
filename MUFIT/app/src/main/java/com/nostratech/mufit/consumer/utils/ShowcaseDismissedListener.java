package com.nostratech.mufit.consumer.utils;

import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public abstract class ShowcaseDismissedListener implements IShowcaseListener {

    @Override
    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {

    }

    @Override
    public abstract void onShowcaseDismissed(MaterialShowcaseView showcaseView);

}
