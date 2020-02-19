package com.nostratech.mufit.consumer.schedule;

import com.nostratech.mufit.consumer.model.schedule.DetailScheduleModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

public interface ScheduleContract {

    interface View {
        void showScheduleOnCalendar(List<CalendarDay> dates);

        void showScheduleForSelectedDate(List<DetailScheduleModel> detailScheduleList);

        void showNoScheduleForSelectedDate();
    }

    interface Presenter {
        void getUserOverallSchedule();

        void getScheduleForSelectedDate(long bookDate);
    }
}
