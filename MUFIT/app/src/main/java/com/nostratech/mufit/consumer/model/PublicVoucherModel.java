package com.nostratech.mufit.consumer.model;

public class PublicVoucherModel {

    private String id;
    private String code;
    private int quantity;
    private int currentQuantity;
    private String type;
    private int value;
    private long startDate;
    private long endDate;
    private long dateCreated;
    private String urlBanner;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String getUrlBanner() {
        return urlBanner;
    }
}
