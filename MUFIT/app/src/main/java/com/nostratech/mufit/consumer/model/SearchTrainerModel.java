package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/18/2018.
 */

public class SearchTrainerModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("shift_list")
    @Expose
    private List<ShiftTrainerModel> shiftList;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("url_photo_trainer")
    @Expose
    private String urlPhotoTrainer;
    @SerializedName("cover_pic_trainer")
    @Expose
    private String coverPicTrainer;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("has_package")
    @Expose
    private boolean hasPackage;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<ShiftTrainerModel> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<ShiftTrainerModel> shiftList) {
        this.shiftList = shiftList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUrlPhotoTrainer() {
        return urlPhotoTrainer;
    }

    public void setUrlPhotoTrainer(String urlPhotoTrainer) {
        this.urlPhotoTrainer = urlPhotoTrainer;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCoverPicTrainer() {
        return coverPicTrainer;
    }

    public void setCoverPicTrainer(String coverPicTrainer) {
        this.coverPicTrainer = coverPicTrainer;
    }

    public boolean isHasPackage() {
        return hasPackage;
    }

    public void setHasPackage(boolean hasPackage) {
        this.hasPackage = hasPackage;
    }
}
