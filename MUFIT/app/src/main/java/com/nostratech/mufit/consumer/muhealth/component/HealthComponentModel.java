package com.nostratech.mufit.consumer.muhealth.component;

import com.google.gson.annotations.SerializedName;

public class HealthComponentModel {

    @SerializedName("id")
    private int id;

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("value")
    private double value;

    @SerializedName("change_value")
    private double changeValue;

    @SerializedName("unit")
    private String unit;

    private ValueChange changeType;


    public HealthComponentModel(String iconUrl, String title, double value, double changeValue, String unit) {
        this.iconUrl = iconUrl;
        this.title = title;
        this.value = value;
        this.changeValue = changeValue;
        this.unit = unit;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    public ValueChange getChangeType() {
        if(changeType == null) {
            if(changeValue == 0) changeType = ValueChange.NEUTRAL;
            else if (changeValue < 0) changeType = ValueChange.DECREASE;
            else changeType = ValueChange.INCREASE;
        }
        return changeType;
    }

    public double getChangeValue() {
        return changeValue;
    }

    public String getUnit() {
        return unit;
    }

    public int getId() {
        return id;
    }
}
