package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileUpdateRequestModel {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("current_password")
    @Expose
    private String currentPassword;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    @SerializedName("new_password_confirmation")
    @Expose
    private String newPasswordConfirmation;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("photo_selfie")
    @Expose
    private String photoSelfie;
    @SerializedName("version")
    @Expose
    private Integer version;

    public ProfileUpdateRequestModel(String address, String currentPassword, String fullName, String id,
                                     String newPassword, String newPasswordConfirmation,
                                     String phone, String photoSelfie, Integer version) {
        this.address = address;
        this.currentPassword = currentPassword;
        this.fullName = fullName;
        this.id = id;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
        this.phone = phone;
        this.photoSelfie = photoSelfie;
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoSelfie() {
        return photoSelfie;
    }

    public void setPhotoSelfie(String photoSelfie) {
        this.photoSelfie = photoSelfie;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


}
