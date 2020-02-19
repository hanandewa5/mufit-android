package com.nostratech.mufit.consumer.detailtrainer.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class DurationModel implements Parcelable {

    private String duration;

    public DurationModel(String duration) {
        this.duration = duration;
    }

    private DurationModel(Parcel in) {
        duration = in.readString();
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DurationModel)) return false;

        DurationModel durationModel = (DurationModel) o;
        return getDuration() != null ? getDuration().equals(durationModel.getDuration()) : durationModel.getDuration() == null;

    }

    @Override
    public int hashCode() {
        return getDuration() != null ? getDuration().hashCode() : 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DurationModel> CREATOR = new Creator<DurationModel>() {
        @Override
        public DurationModel createFromParcel(Parcel in) {
            return new DurationModel(in);
        }

        @Override
        public DurationModel[] newArray(int size) {
            return new DurationModel[size];
        }
    };
}
