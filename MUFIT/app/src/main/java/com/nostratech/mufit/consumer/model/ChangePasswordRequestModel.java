package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequestModel {

    @SerializedName("oldPassword")
    private String oldPassword;

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("confirmationPassword")
    private String confirmationPassword;

    public ChangePasswordRequestModel(String oldPassword, String newPassword, String confirmationPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmationPassword = confirmationPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }
}
