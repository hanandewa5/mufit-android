package com.nostratech.mufit.consumer.model.banner_event_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainerListEventModel {
    @SerializedName("trainer_id")
    @Expose
    private String trainerId;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}
