package com.nostratech.mufit.consumer.model.banner_event_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialityEventModel {
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
