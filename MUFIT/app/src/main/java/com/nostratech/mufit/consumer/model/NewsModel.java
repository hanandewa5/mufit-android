package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsModel implements Serializable {

    private String id;
    private String title;
    private String description;
    @SerializedName("image")
    private String imageUrl;
    private boolean important;
    private long startDate;
    private long endDate;
    private String source;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isImportant() {
        return important;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public String getSource() {
        return source;
    }
}
