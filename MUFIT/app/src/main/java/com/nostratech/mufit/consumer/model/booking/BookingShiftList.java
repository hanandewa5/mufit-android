package com.nostratech.mufit.consumer.model.booking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingShiftList implements Parcelable{
    @SerializedName("shift_id")
    @Expose
    private String shiftId;

    public BookingShiftList(String shiftId) {
        this.shiftId = shiftId;
    }

    protected BookingShiftList(Parcel in) {
        shiftId = in.readString();
    }

    public static final Creator<BookingShiftList> CREATOR = new Creator<BookingShiftList>() {
        @Override
        public BookingShiftList createFromParcel(Parcel in) {
            return new BookingShiftList(in);
        }

        @Override
        public BookingShiftList[] newArray(int size) {
            return new BookingShiftList[size];
        }
    };

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shiftId);
    }
}
