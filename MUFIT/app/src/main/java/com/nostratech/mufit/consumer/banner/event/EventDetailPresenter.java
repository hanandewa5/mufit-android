package com.nostratech.mufit.consumer.banner.event;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.banner_event_detail.BannerEventDetailModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.SpecialityEventModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.TrainerListEventModel;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;
import com.nostratech.mufit.consumer.utils.date.DateUtils;

import java.util.ArrayList;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class EventDetailPresenter extends MyBasePresenter implements EventDetailContract.Presenter {

    private EventDetailContract.View view;
    private CurrencyFormatter currencyFormatter;

    private BannerEventDetailModel model;

    EventDetailPresenter(Context context, MvpView mvpView, EventDetailContract.View view) {
        super(context, mvpView);
        this.view = view;
        this.currencyFormatter = new CurrencyFormatter();
    }

    @Override
    public void loadEventDetail(String id) {
        getMvpView().showLoading();
        if(isConnectedToInternet()){
            Call<StandardResponseModel> call = getApiService().getDetailEvent(getAccessToken(), id);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                model = getGson().fromJson(jsonObject.toString()
                        ,BannerEventDetailModel.class);

                //Pass data to view
                setRegistrationPeriod(model);
                setEventDateTime(model);

                String availableSlots = model.getCurrentQuota() + " / " + model.getQuota();

                String price = "Rp. " + currencyFormatter.getRupiahString(model.getPrice());

                view.showEventDetail(model.getUrl(),
                        availableSlots,
                        model.getDescription(),
                        getTrainerName(model),
                        model.getSpecialities(),
                        price,
                        model.getAddress()
                );

                //If event is eligible for booking, show the btn booking, else hide it.
                if(DateUtils.isInBetween(System.currentTimeMillis(), model.getRegistrationStartDate(), model.getRegistrationEndDate())){
                    view.showBtnBook();
                } else {
                    view.hideBtnBook();
                }
                
                getMvpView().dismissLoading();
            }));
        }
        else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void bookEvent() {
        //If user is logged in
        if(getAccessToken() != null){
            ArrayList<String> specialities = new ArrayList<>();
            for (SpecialityEventModel model : model.getSpecialityList()){
                specialities.add(model.getSpecialityName());
            }

            String eventTime = model.getEventStartTime().getAsHHMM(".") + " - " + model.getEventEndTime().getAsHHMM(".");

            view.navigateToBookingEvent(model.getId(),
                    specialities,
                    model.getTrainerList().get(0).getTrainerId(),
                    getTrainerName(model),
                    model.getPrice(),
                    model.getAddress(),
                    model.getLatitude(),
                    model.getLongitude(),
                    DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, model.getEventDate()),
                    eventTime,
                    model.getSpecialities());
        } else {
            view.redirectToLogin(model.getId());
        }
    }

    private void setRegistrationPeriod(BannerEventDetailModel model){
        String registrationStart = DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, model.getRegistrationStartDate());
        String registrationEnd = DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, model.getRegistrationEndDate());

        view.showRegistrationPeriod(registrationStart, registrationEnd);
    }

    private void setEventDateTime(BannerEventDetailModel model){

        //Set event date time
        String eventDate = DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, model.getEventDate());
        String startTime = model.getEventStartTime().getAsHHMM(".");
        String endTime = model.getEventEndTime().getAsHHMM(".");

        view.showEventDateTime(eventDate, startTime, endTime);

    }

    private String getTrainerName(BannerEventDetailModel model){
        StringBuilder trainerNameBuilder = new StringBuilder();


        for(int i = 0; i < model.getTrainerList().size(); i++){
            final String delimiter = ", ";

            final TrainerListEventModel trainer = model.getTrainerList().get(i);
            trainerNameBuilder.append(trainer.getTrainerName());

            //On last trainer found, do not append delimiter
            if(i < model.getTrainerList().size() - 1) trainerNameBuilder.append(delimiter);
        }

        return trainerNameBuilder.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coordinates = new LatLng(model.getLatitude(), model.getLongitude());
        view.updateMap(googleMap, coordinates);
    }
}

