package com.nostratech.mufit.consumer.detailtrainer.helper;

import android.content.Context;
import android.content.res.Resources;

public class LayoutUtils {

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getDimensPx(Context context, int dimenId){
        return (int) context.getResources().getDimension(dimenId);
    }
}
