package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.SerializedName;

public class CreateBookingResponseModel {

    @SerializedName("id")
    private String id;

    @SerializedName("order_id")
    private String orderId;

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }
}
