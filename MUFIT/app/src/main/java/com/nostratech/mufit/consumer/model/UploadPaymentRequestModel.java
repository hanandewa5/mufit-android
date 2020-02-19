package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadPaymentRequestModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payment_photo")
    @Expose
    private String paymentPhoto;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("payment_photo_description")
    @Expose
    private String paymentPhotoDescription;

    public UploadPaymentRequestModel(String id, String paymentPhoto, Integer version, String paymentPhotoDescription) {
        this.id = id;
        this.paymentPhoto = paymentPhoto;
        this.version = version;
        this.paymentPhotoDescription = paymentPhotoDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public void setPaymentPhoto(String paymentPhoto) {
        this.paymentPhoto = paymentPhoto;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPaymentPhotoDescription() {
        return paymentPhotoDescription;
    }

    public void setPaymentPhotoDescription(String paymentPhotoDescription) {
        this.paymentPhotoDescription = paymentPhotoDescription;
    }
}
