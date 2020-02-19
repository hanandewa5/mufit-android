package com.nostratech.mufit.consumer.detailtrainer.schedule;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.TrainerAvailabilityModel;
import com.nostratech.mufit.consumer.model.detail_shift.Day;
import com.nostratech.mufit.consumer.model.detail_shift.DetailShiftResponseModel;
import com.nostratech.mufit.consumer.model.detail_shift.Shift;
import com.nostratech.mufit.consumer.model.trainer_package.PackageModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import id.mufit.singleweekcalendar.CalendarDay;
import retrofit2.Call;

class FragmentSchedulePresenter extends MyBasePresenter implements FragmentScheduleContract.Presenter {
    private FragmentScheduleContract.View fsView;
    private static final String TAG = FragmentSchedulePresenter.class.getSimpleName();

    private int monthCount = 0;
    private int MONTH_TO_QUERY = 3;
    private Call lastGetDetailShiftCallback;

    FragmentSchedulePresenter(Context context, MvpView mvpView, FragmentScheduleContract.View fsView) {
        super(context, mvpView);
        this.fsView = fsView;
    }

    /**
     * To disable shifts that are past the booking window
     */
    private void markShiftDisabledIfPastBookingWindow(Shift shift) {

        //To prevent disabled shifts from getting re-done
        if (!shift.getStatus()) return;

        Calendar rightNow = Calendar.getInstance();
        int currentHr = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMin = rightNow.get(Calendar.MINUTE);

        int shiftStartHr = shift.getStartTime().getHour();
        int timeDiff = (shiftStartHr - currentHr) * 60 - currentMin;

        Log.d("DisableShift", "timeDiff: " + timeDiff);

        //If the time between NOW and shift's start time is past the booking window, disable it
        //The booking window ends 5 hours before the start time of a shift
        if (timeDiff <= 300) {
            Log.d("DisableShift", "disabling Shift");
            shift.setStatus(false);
        } else {
            shift.setStatus(true);
        }

    }

    @Override
    public void getDetailShift(String trainerId, final String day, String bookDate, final Date selectedDate) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            final Call<StandardResponseModel> call = getApiService().getDetailShiftTrainer(getAccessToken(), trainerId, day, bookDate);

            //TODO: Think of a better solution to cancel running calls
            if(lastGetDetailShiftCallback != null) lastGetDetailShiftCallback.cancel();
            lastGetDetailShiftCallback = call;
            addRequest(call);

            call.enqueue(new RetrofitCallback<>(this,  response -> {
                removeRequest(call);
                lastGetDetailShiftCallback = null;
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();

                DetailShiftResponseModel shiftDetail = getGson().fromJson(jsonObject.toString(), DetailShiftResponseModel.class);

                //TODO: refactor and remove this temporary dirty fix
                Day dayModel = shiftDetail.getDay();
                if (dayModel == null) {
                    fsView.showEmptySchedule();
                    getMvpView().dismissLoading();
                    return;
                }

                List<Shift> shiftList = shiftDetail.getDay().getShift();

                if (shiftList.isEmpty()) {
                    fsView.showEmptySchedule();
                } else {

                    Date currentDate = new Date(System.currentTimeMillis());
                    //To check if the selected date is today
                    //Since selectedDate is today's Date at 00.00, it will return true if it's today at any time of the day
                    if (selectedDate.compareTo(currentDate) < 0) {
                        for (Shift s : shiftList) {
                            markShiftDisabledIfPastBookingWindow(s);
                        }
                    }

                    fsView.showContent();
                    fsView.showTrainerShiftsForSelectedDate(shiftList);

                    //If no shift is available, hide the checkboxes

                    boolean noShiftAvailable = true;
                    for (Shift s : shiftList) {
                        if (s.getStatus()) {
                            noShiftAvailable = false;
                            break;
                        }
                    }

                    fsView.setSpecialityList(shiftDetail.getTrainerSpeciality(), noShiftAvailable);
                }

                getMvpView().dismissLoading();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getTrainerSchedule(String trainerId, long startDate, long endDate) {
        if (isConnectedToInternet()) {
            final Call<StandardResponseModel> call = getApiService().getTrainerAvailableSchedule(trainerId, startDate, endDate, getAccessToken());
            addRequest(call);

            call.enqueue(new RetrofitCallback<>(this, response -> {
                removeRequest(call);
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type type = new TypeToken<Collection<TrainerAvailabilityModel>>() {
                }.getType();

                //Parse JSON
                List<TrainerAvailabilityModel> models = getGson().fromJson(jsonArray, type);

                //Note: booked means status = false
                //redDays stores CalendarDays where all shifts are booked
                List<CalendarDay> redDays = new ArrayList<>();

                //greenDays stores CalendarDays where at least one shift is not booked
                List<CalendarDay> greenDays = new ArrayList<>();


                for (TrainerAvailabilityModel schedule : models) {
                    Date scheduleDate = new Date(schedule.getTimestampMilliseconds());
                    if (schedule.isAvailable()) {
                        greenDays.add(CalendarDay.from(scheduleDate));
                    } else {
                        redDays.add(CalendarDay.from(scheduleDate));
                    }
                }

                //Pass to view
                fsView.setTrainerAvailabilityIndicators(redDays, greenDays);

                if(++monthCount < MONTH_TO_QUERY){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(endDate);
                    long startDateNextMonth = calendar.getTimeInMillis();
                    calendar.add(Calendar.MONTH, 1);
                    long endDateNextMonth = calendar.getTimeInMillis();
                    
                    getTrainerSchedule(trainerId, startDateNextMonth, endDateNextMonth);
                }

            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    //TODO: fristy - get all package
    @Override
    public void getAllPackage(String trainerId) {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getAllPackageByTrainerId(trainerId, getAccessToken());
            addRequest(call);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                removeRequest(call);
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<PackageModel>>() {
                }.getType();
                List<PackageModel> listPackage = getGson().fromJson(jsonArray, dataType);
                fsView.setListOfPackages(listPackage);
            }));

        } else {
            getMvpView().showNoInternetError();
        }
    }
}
