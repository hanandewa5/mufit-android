package com.nostratech.mufit.consumer.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListScheduleModel {
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }
}
