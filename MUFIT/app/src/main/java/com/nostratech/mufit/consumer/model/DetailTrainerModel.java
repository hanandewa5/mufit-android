package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailTrainerModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reviews")
    @Expose
    private int reviews;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;
    @SerializedName("no_npwp")
    @Expose
    private String no_npwp;
    @SerializedName("no_rek")
    @Expose
    private String no_rek;
    @SerializedName("bank")
    @Expose
    private Object bank;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("url_photo_trainer")
    @Expose
    private String url_photo_trainer;
    @SerializedName("url_photo_ktp")
    @Expose
    private String url_photo_ktp;
    @SerializedName("url_photo_npwp")
    @Expose
    private String url_photo_npwp;
    @SerializedName("addrtrainer_specialitysess")
    @Expose
    private String trainer_specialitys;
    @SerializedName("day")
    @Expose
    private Object day;
    @SerializedName("trainer_speciality")
    @Expose
    private Object trainer_speciality;
    @SerializedName("has_package")
    @Expose
    private Boolean has_package;
    @SerializedName("trainer_certificate")
    @Expose
    private List<TrainerCertificateModel> listtrainer_certificate = null;
    @SerializedName("trainer_image")
    @Expose
    private List<TrainerImageModel> listTrainerImageModel = null;
    @SerializedName("trainer_speciality_package")
    @Expose
    private List<String> trainerSpecialityPackage = null;
    @SerializedName("cover_pic_trainer")
    @Expose
    private String coverPicTrainer;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getNo_npwp() {
        return no_npwp;
    }

    public void setNo_npwp(String no_npwp) {
        this.no_npwp = no_npwp;
    }

    public String getNo_rek() {
        return no_rek;
    }

    public void setNo_rek(String no_rek) {
        this.no_rek = no_rek;
    }

    public Object getBank() {
        return bank;
    }

    public void setBank(Object bank) {
        this.bank = bank;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUrl_photo_trainer() {
        return url_photo_trainer;
    }

    public void setUrl_photo_trainer(String url_photo_trainer) {
        this.url_photo_trainer = url_photo_trainer;
    }

    public String getUrl_photo_ktp() {
        return url_photo_ktp;
    }

    public void setUrl_photo_ktp(String url_photo_ktp) {
        this.url_photo_ktp = url_photo_ktp;
    }

    public String getUrl_photo_npwp() {
        return url_photo_npwp;
    }

    public void setUrl_photo_npwp(String url_photo_npwp) {
        this.url_photo_npwp = url_photo_npwp;
    }

    public String getTrainer_specialitys() {
        return trainer_specialitys;
    }

    public void setTrainer_specialitys(String trainer_specialitys) {
        this.trainer_specialitys = trainer_specialitys;
    }

    public Object getDay() {
        return day;
    }

    public void setDay(Object day) {
        this.day = day;
    }

    public Object getTrainer_speciality() {
        return trainer_speciality;
    }

    public void setTrainer_speciality(Object trainer_speciality) {
        this.trainer_speciality = trainer_speciality;
    }

    public Boolean getHas_package() {
        return has_package;
    }

    public void setHas_package(Boolean has_package) {
        this.has_package = has_package;
    }

    public List<TrainerCertificateModel> getListtrainer_certificate() {
        return listtrainer_certificate;
    }

    public void setListtrainer_certificate(List<TrainerCertificateModel> listtrainer_certificate) {
        this.listtrainer_certificate = listtrainer_certificate;
    }

    public List<TrainerImageModel> getListTrainerImageModel() {
        return listTrainerImageModel;
    }

    public void setListTrainerImageModel(List<TrainerImageModel> listTrainerImageModel) {
        this.listTrainerImageModel = listTrainerImageModel;
    }

    public List<String> getTrainerSpecialityPackage() {
        return trainerSpecialityPackage;
    }

    public void setTrainerSpecialityPackage(List<String> trainerSpecialityPackage) {
        this.trainerSpecialityPackage = trainerSpecialityPackage;
    }

    public String getCoverPicTrainer() {
        return coverPicTrainer;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
