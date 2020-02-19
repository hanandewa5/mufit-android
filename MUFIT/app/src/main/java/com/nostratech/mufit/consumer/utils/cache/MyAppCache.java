package com.nostratech.mufit.consumer.utils.cache;

import android.content.Context;

import id.mufit.core.data.AppCache;

public class MyAppCache extends AppCache implements ProfileCacheContract {

    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_B2B_ID = "userB2Bid";
    private static final String KEY_MUHEALTH_ONBOARDED = "muhealthOnboarded";

    public MyAppCache(Context context) {
        super(context);
    }

    @Override
    public void setEmail(String email) {
        getPrefsHelper().saveData(KEY_EMAIL, email);
    }

    @Override
    public String getEmail() {
        return getPrefsHelper().getString(KEY_EMAIL);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        getPrefsHelper().saveData(KEY_PHONE_NUMBER, phoneNumber);
    }

    @Override
    public String getPhoneNumber() {
        return getPrefsHelper().getString(KEY_PHONE_NUMBER);
    }

    @Override
    public void setB2bId(String b2bId) {
        getPrefsHelper().saveData(KEY_B2B_ID, b2bId);
    }

    @Override
    public String getB2bId() {
        return getPrefsHelper().getString(KEY_B2B_ID);
    }

    @Override
    public void setMuhealthOnboarded(boolean bool) {
        getPrefsHelper().saveData(KEY_MUHEALTH_ONBOARDED, bool);
    }

    @Override
    public boolean getMuhealthOnboarded() {
        return getPrefsHelper().getBool(KEY_MUHEALTH_ONBOARDED);
    }


}
