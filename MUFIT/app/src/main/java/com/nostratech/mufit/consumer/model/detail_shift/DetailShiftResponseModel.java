package com.nostratech.mufit.consumer.model.detail_shift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailShiftResponseModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("email")
    @Expose
    private String email;
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
    private String noKtp;
    @SerializedName("no_npwp")
    @Expose
    private String noNpwp;
    @SerializedName("no_rek")
    @Expose
    private String noRek;
    @SerializedName("bank")
    @Expose
    private Bank bank;
    @SerializedName("url_photo_trainer")
    @Expose
    private String urlPhotoTrainer;
    @SerializedName("trainer_specialitys")
    @Expose
    private String trainerSpecialitys;
    @SerializedName("day")
    @Expose
    private Day day;
    @SerializedName("trainer_speciality")
    @Expose
    private List<TrainerSpeciality> trainerSpeciality = null;

    private boolean isChecked;

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

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoNpwp() {
        return noNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        this.noNpwp = noNpwp;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getUrlPhotoTrainer() {
        return urlPhotoTrainer;
    }

    public void setUrlPhotoTrainer(String urlPhotoTrainer) {
        this.urlPhotoTrainer = urlPhotoTrainer;
    }

    public String getTrainerSpecialitys() {
        return trainerSpecialitys;
    }

    public void setTrainerSpecialitys(String trainerSpecialitys) {
        this.trainerSpecialitys = trainerSpecialitys;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public List<TrainerSpeciality> getTrainerSpeciality() {
        return trainerSpeciality;
    }

    public void setTrainerSpeciality(List<TrainerSpeciality> trainerSpeciality) {
        this.trainerSpeciality = trainerSpeciality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
