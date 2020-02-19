package com.nostratech.mufit.consumer.model.trainer_package;

import com.google.gson.annotations.SerializedName;

class PackageDiscount {

    private String id;
    private int version;
    private String name;
    private String description;
    private int quantity;
    @SerializedName("discount")
    private int discountPercentage;
    private int expired;

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public int getExpired() {
        return expired;
    }
}
