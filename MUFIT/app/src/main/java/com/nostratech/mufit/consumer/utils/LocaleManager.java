package com.nostratech.mufit.consumer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleManager {

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_INDONESIA = "in";
    private static final String LANGUAGE_KEY = "language_key";
    private static final String PREFER_NAME = "SessionPref";

    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        int PRIVATE_MODE = 0;
        SharedPreferences prefs = c.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_INDONESIA);
    }

    @SuppressLint("ApplySharedPref")
    private static void persistLanguage(Context c, String language) {
        int PRIVATE_MODE = 0;
        SharedPreferences prefs = c.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }
}