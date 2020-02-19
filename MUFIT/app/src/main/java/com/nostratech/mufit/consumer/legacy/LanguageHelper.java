package com.nostratech.mufit.consumer.legacy;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Not sure what it's used for
 */
public class LanguageHelper {

    public static void changeLocale (Resources resources, String locale){
        Configuration configuration;
        configuration = new Configuration(resources.getConfiguration());

        switch (locale){
                case "en" :
                    configuration.locale = new Locale("en");
                    break;
                default:
                    configuration.locale = Locale.getDefault();
                    break;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
