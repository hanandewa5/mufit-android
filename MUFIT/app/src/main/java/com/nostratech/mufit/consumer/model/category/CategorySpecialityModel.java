package com.nostratech.mufit.consumer.model.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategorySpecialityModel implements Serializable {
    @SerializedName("speciality_id")
    private String id;
    @SerializedName("speciality_name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
