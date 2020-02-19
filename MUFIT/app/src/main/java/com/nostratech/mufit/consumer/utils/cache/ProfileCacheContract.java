package com.nostratech.mufit.consumer.utils.cache;

public interface ProfileCacheContract {

    void setEmail(String email);

    String getEmail();

    void setPhoneNumber(String phoneNumber);

    String getPhoneNumber();

    void setB2bId(String b2bId);

    String getB2bId();

    void setMuhealthOnboarded(boolean bool);

    boolean getMuhealthOnboarded();
}
