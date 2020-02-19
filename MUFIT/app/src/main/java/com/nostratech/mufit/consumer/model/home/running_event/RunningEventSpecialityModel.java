package com.nostratech.mufit.consumer.model.home.running_event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunningEventSpecialityModel {
    @SerializedName("speciality_name")
    @Expose
    private String specialityName;

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
