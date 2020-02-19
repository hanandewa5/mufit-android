package com.nostratech.mufit.consumer.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtils {
    private static Typeface truenoRegular;
    private static Typeface truenoMedium;
    private static Typeface truenoLight;

    public static Typeface getTruenoRegular(Context context){
        if(truenoRegular == null){
            truenoRegular = Typeface.createFromAsset(context.getAssets(),
                    "fonts/trueno_regular.otf");
        }
        return truenoRegular;
    }

    public static Typeface getTruenoMedium(Context context){
        if(truenoMedium == null){
            truenoMedium = Typeface.createFromAsset(context.getAssets(),
                    "fonts/trueno_medium.otf");
        }
        return truenoMedium;
    }

    public static Typeface getTruenoLight(Context context){
        if(truenoLight == null){
            truenoLight = Typeface.createFromAsset(context.getAssets(),
                    "fonts/trueno_light.otf");
        }
        return truenoLight;
    }

}
