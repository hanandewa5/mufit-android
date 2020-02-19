package com.nostratech.mufit.consumer.model.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryResponseModel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("version")
    private int version;
    @SerializedName("description")
    private String description;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("category_image")
    private String categoryImageUrl;
    @SerializedName("list_of_speciality")
    private List<CategorySpecialityModel> listSpeciality;

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public List<CategorySpecialityModel> getListSpeciality() {
        return listSpeciality;
    }
}
