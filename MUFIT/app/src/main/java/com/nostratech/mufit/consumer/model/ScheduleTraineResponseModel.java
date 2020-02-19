package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleTraineResponseModel {
    @SerializedName("date_booking")
    @Expose
    private Long dateBooking;

    public Long getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(Long dateBooking) {
        this.dateBooking = dateBooking;
    }

}
