package com.nostratech.mufit.consumer.banner.event;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.booking.BookingSessionActivity;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailActivity extends MyToolbarBackActivity implements EventDetailContract.View {

    @BindView(R.id.eventDetail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.eventDetail_image)
    RoundedImageView imageEvent;

    @BindView(R.id.eventDetail_textAvailableSlots)
    TextView textAvailableSlots;

    @BindView(R.id.eventDetail_textDesc)
    TextView textDescription;

    @BindView(R.id.eventDetail_textRegisStart)
    TextView textRegistrationStart;

    @BindView(R.id.eventDetail_textRegisEnd)
    TextView textRegistrationEnd;

    @BindView(R.id.eventDetail_textTrainerName)
    TextView textTrainerName;

    @BindView(R.id.eventDetail_textDateTime)
    TextView textDateTime;

    @BindView(R.id.eventDetail_textSpecialityName)
    TextView textSpecialityName;

    @BindView(R.id.eventDetail_textPrice)
    TextView textPrice;

    @BindView(R.id.eventDetail_textLocation)
    TextView textLocation;

    @BindView(R.id.eventDetail_btnBook)
    Button btnBook;

    private final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int LOCATION_REQUEST = 123;

    private SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    private EventDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        //get current date

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.eventDetail_map);

        Intent intent = getIntent();
        String eventId = intent.getStringExtra(Constants.Event.ID);

        getLocationPermission();
        presenter = new EventDetailPresenter(this, this, this);
        presenter.loadEventDetail(eventId);

    }

    @Override
    public void showEventDetail(String imageUrl, String availableSlots, String desc,
                                String trainerName, String speciality,
                                String price, String location) {


        GlideApp.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.image_preview_bg)
                .into(imageEvent);

        textAvailableSlots.setText(availableSlots);
        textDescription.setText(desc);

        textTrainerName.setText(trainerName);

        textSpecialityName.setText(speciality);
        textPrice.setText(price);
        textLocation.setText(location);

        mapFragment.getMapAsync(presenter);
    }

    @Override
    public void showRegistrationPeriod(String registrationStart, String registrationEnd) {
        textRegistrationStart.setText(registrationStart);
        textRegistrationEnd.setText(registrationEnd);
    }

    @Override
    public void showEventDateTime(String eventDate, String startTime, String endTime) {
        String dateTime = getString(R.string.format_event_date, eventDate, startTime, endTime);
        textDateTime.setText(dateTime);
    }

    @Override
    public void showBtnBook() {
        btnBook.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnBook() {
        btnBook.setVisibility(View.GONE);
    }

    @Override
    public void updateMap(GoogleMap gMap, LatLng coordinates) {
        this.googleMap = gMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        MarkerOptions marker = new MarkerOptions().position(coordinates).title("Event Location");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinpoint_marker));
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f));
        googleMap.setOnMapClickListener(latLng -> {
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + coordinates.latitude + "," +
                    coordinates.longitude + "&travelmode=driving";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });
    }

    @Override
    public void navigateToBookingEvent(String eventId, ArrayList<String> specialityList,
                                       String trainerId, String trainerName,
                                       int price, String address,
                                       double lat, double lng,
                                       String eventDate, String eventTime, String speciality) {
        Intent itn = new Intent(this, BookingSessionActivity.class);
        itn.putExtra("eventId", eventId);
        itn.putStringArrayListExtra("eventBookingSpecialityRequestList", specialityList);
        itn.putExtra(Constants.IntentExtras.TRAINER_ID, trainerId);
        itn.putExtra(Constants.IntentExtras.TRAINER_NAME, trainerName);
        itn.putExtra("type", "event");
        itn.putExtra("totalPrice", price);
        itn.putExtra("eventLocation", address);
        itn.putExtra("latEvent", lat);
        itn.putExtra("longEvent", lng);
        itn.putExtra("date", eventDate);
        itn.putExtra("shift", eventTime);
        itn.putExtra("class", speciality);
        startActivity(itn);
    }

    @Override
    public void redirectToLogin(String eventId) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, LoginActivity.OPEN_BANNER_EVENT);
        i.putExtra(Constants.Event.ID, eventId);
        showActivity(i);
    }

    //TODO: Apply circular progress bar
    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    private void getLocationPermission(){
        if(!isLocationEnabled()){
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST);
        }
    }

    private boolean isLocationEnabled(){
        return (ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    startActivity(new Intent(this, SearchTrainerActivity.class));
                }
        }
    }

    @OnClick(R.id.eventDetail_btnBook)
    public void onClick(View v){
        presenter.bookEvent();
    }
}
