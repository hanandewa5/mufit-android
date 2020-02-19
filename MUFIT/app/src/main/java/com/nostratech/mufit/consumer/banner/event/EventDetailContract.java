package com.nostratech.mufit.consumer.banner.event;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

class EventDetailContract {

    interface View {
        void showEventDetail(String imageUrl, String availableSlots, String desc,
                             String trainerName, String speciality,
                             String price, String location);

        void showRegistrationPeriod(String registrationStart, String registrationEnd);

        void showEventDateTime(String eventDate, String startTime, String endTime);

        void showBtnBook();

        void hideBtnBook();

        void updateMap(GoogleMap gMap, LatLng coordinates);

        void navigateToBookingEvent(String eventId, ArrayList<String> specialityList,
                                    String trainerId, String trainerName,
                                    int price, String address,
                                    double lat, double lng,
                                    String eventDate, String eventTime, String speciality);

        void redirectToLogin(String eventId);

    }

    interface Presenter extends OnMapReadyCallback {
        void loadEventDetail(String id);

        void bookEvent();
    }

}
