package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainerAvailabilityModel {


    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("available")
    @Expose
    private boolean available;

    public long getTimestamp() {
        return timestamp;
    }

    public long getTimestampMilliseconds(){
        return timestamp * 1000;
    }

    public boolean isAvailable() {
        return available;
    }
}
