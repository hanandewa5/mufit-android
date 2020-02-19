package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("version")
    @Expose
    private Integer version;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("address")
    @Expose
    private Object address;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("date_created")
    @Expose
    private long dateCreated;

    @SerializedName("photo_selfie")
    @Expose
    private String photoSelfie;

    @SerializedName("profile_b2b_id")
    @Expose
    private String profileB2BId;

    public String getProfileB2BId() {
        return profileB2BId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPhotoSelfie() {
        return photoSelfie;
    }

    public void setPhotoSelfie(String photoSelfie) {
        this.photoSelfie = photoSelfie;
    }

}
