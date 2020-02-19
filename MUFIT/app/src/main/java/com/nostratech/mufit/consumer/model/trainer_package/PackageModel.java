package com.nostratech.mufit.consumer.model.trainer_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("trainer_speciality")
    @Expose
    private PackageTrainerSpeciality packageTrainerSpeciality;
    @SerializedName("discount")
    @Expose
    private PackageDiscount packageDiscountInfo;

    public String getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public PackageTrainerSpeciality getPackageTrainerSpeciality() {
        return packageTrainerSpeciality;
    }

    public PackageDiscount getPackageDiscountInfo() {
        return packageDiscountInfo;
    }
}
