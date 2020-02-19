package com.nostratech.mufit.consumer.schedule;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.schedule.DetailScheduleModel;
import com.nostratech.mufit.consumer.model.schedule.ListScheduleModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class SchedulePresenter extends MyBasePresenter implements ScheduleContract.Presenter {
    private ScheduleContract.View view;

    private static final String TAG = SchedulePresenter.class.getSimpleName();

    SchedulePresenter(Context context, MvpView mvpView, ScheduleContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void getUserOverallSchedule() {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getDateSchedule(getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<ListScheduleModel>>() {
                }.getType();

                List<ListScheduleModel> models = getGson().fromJson(jsonArray, dataType);

                List<CalendarDay> dates = convertModelIntoCalendarDays(models);

                view.showScheduleOnCalendar(dates);
                getMvpView().dismissLoading();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    /**
     * Convert Models received from Backend into {@link CalendarDay}
     * used by {@link com.prolificinteractive.materialcalendarview.MaterialCalendarView}
     *
     * The Model has a field date_booking which is a Date String in the form of 'yyyy-MM-dd'
     */
    private List<CalendarDay> convertModelIntoCalendarDays(List<ListScheduleModel> models){
        List<CalendarDay> calendarDays = new ArrayList<>();

        for (ListScheduleModel model : models) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(df.parse(model.getDateBooking()));
                CalendarDay day = CalendarDay.from(cal);
                calendarDays.add(day);
            } catch (ParseException e) {
                getMvpView().showGenericError("Failed to parse date");
            }
        }

        return calendarDays;
    }

    @Override
    public void getScheduleForSelectedDate(long bookDate) {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getDetailSchedule(getAccessToken(), bookDate);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<DetailScheduleModel>>() {
                }.getType();
                List<DetailScheduleModel> models = getGson().fromJson(jsonArray, dataType);

                if (models == null || models.isEmpty()) {
                    view.showNoScheduleForSelectedDate();
                } else {
                    view.showScheduleForSelectedDate(models);
                }

            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }
}
