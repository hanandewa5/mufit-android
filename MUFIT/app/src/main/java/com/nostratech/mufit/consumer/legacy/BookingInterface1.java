package com.nostratech.mufit.consumer.legacy;

import com.nostratech.mufit.consumer.model.detail_shift.Day;
import com.nostratech.mufit.consumer.model.detail_shift.Shift;
import com.nostratech.mufit.consumer.model.detail_shift.TrainerSpeciality;

import java.util.List;

public interface BookingInterface1 {
    interface View {
        void setSpinnerSchedule(List<String> dayNameList);
        void setCheckboxSchedule(List<Shift> shiftList);
        void setCheckboxSpeciality(List<TrainerSpeciality> trainerSpecialityList);

        void checkValidation();
        void getShifts();
        void getClasses();
        void setBank(String bankName, String noRek);
        void checkSchedule(int counter);
    }

    interface Presenter {
        void getDetailBooking(String access_token, String id, String day, String bookdate, Long timeStamp);
        int getDayNumber(String day);
    }
}
