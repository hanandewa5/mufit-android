package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmadan Ditiananda on 4/24/2018.
 */

public class LoginResponseModel {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("clientId")
    @Expose
    private String clientId;

    @SerializedName("phone")
    @Expose
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getFullName() {
        return fullName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

