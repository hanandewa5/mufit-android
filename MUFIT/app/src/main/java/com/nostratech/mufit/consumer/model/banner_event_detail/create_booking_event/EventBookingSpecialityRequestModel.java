package com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventBookingSpecialityRequestModel {
    @SerializedName("speciality_name")
    @Expose
    private String specialityName;

    public EventBookingSpecialityRequestModel(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
