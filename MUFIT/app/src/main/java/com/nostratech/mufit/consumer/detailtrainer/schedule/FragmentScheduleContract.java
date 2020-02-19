package com.nostratech.mufit.consumer.detailtrainer.schedule;

import com.nostratech.mufit.consumer.model.detail_shift.Shift;
import com.nostratech.mufit.consumer.model.detail_shift.TrainerSpeciality;
import com.nostratech.mufit.consumer.model.trainer_package.PackageModel;

import java.util.Date;
import java.util.List;

import id.mufit.singleweekcalendar.CalendarDay;

public interface FragmentScheduleContract {
    interface View {
        void showEmptySchedule();

        void showContent();

        void showTrainerShiftsForSelectedDate(List<Shift> shift);

        void setSpecialityList(List<TrainerSpeciality> specialityList, boolean noShiftAvailable);

        void setTrainerAvailabilityIndicators(List<CalendarDay> redDays, List<CalendarDay> greenDays);

        void setListOfPackages(List<PackageModel> listPackageModelOld);
    }

    interface Presenter {
        void getDetailShift(String id, String day, String bookdate, Date date);

        void getAllPackage(String trainerId);

        void getTrainerSchedule(String trainerId, long startDate, long endDate);
    }
}
