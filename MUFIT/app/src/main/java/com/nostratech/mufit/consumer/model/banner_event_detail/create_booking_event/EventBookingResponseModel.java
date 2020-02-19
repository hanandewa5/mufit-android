package com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventBookingResponseModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;

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
}
