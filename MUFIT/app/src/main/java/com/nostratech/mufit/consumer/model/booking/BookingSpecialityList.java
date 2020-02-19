package com.nostratech.mufit.consumer.model.booking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingSpecialityList implements Parcelable {
    @SerializedName("id_trainer_speciality")
    @Expose
    private String idTrainerSpeciality;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public BookingSpecialityList(String idTrainerSpeciality, Integer quantity) {
        this.idTrainerSpeciality = idTrainerSpeciality;
        this.quantity = quantity;
    }

    protected BookingSpecialityList(Parcel in) {
        idTrainerSpeciality = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
    }

    public static final Creator<BookingSpecialityList> CREATOR = new Creator<BookingSpecialityList>() {
        @Override
        public BookingSpecialityList createFromParcel(Parcel in) {
            return new BookingSpecialityList(in);
        }

        @Override
        public BookingSpecialityList[] newArray(int size) {
            return new BookingSpecialityList[size];
        }
    };

    public String getIdTrainerSpeciality() {
        return idTrainerSpeciality;
    }

    public void setIdTrainerSpeciality(String idTrainerSpeciality) {
        this.idTrainerSpeciality = idTrainerSpeciality;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTrainerSpeciality);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
    }
}
