//package com.nostratech.mufit.consumer.legacy;
//
//import android.Manifest;
//import android.app.Dialog;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.Toolbar;
//import androidx.cardview.widget.CardView;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.material.appbar.AppBarLayout;
//import com.makeramen.roundedimageview.RoundedImageView;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
//import com.nostratech.mufit.consumer.booking.BookingSessionActivity;
//import com.nostratech.mufit.consumer.login.LoginActivity;
//import com.nostratech.mufit.consumer.model.banner_event_detail.BannerEventDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.advertisement.AdvertisementDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingSpecialityRequestModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.voucher_detail.VoucherDetailModel;
//import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
//import com.nostratech.mufit.consumer.utils.Constants;
//
//import java.text.DateFormat;
//import java.text.NumberFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.mufit.core.dialog.MufitDialogOneButtonWithText;
//
///**
// * Parameters passed through Intent extras are:
// * -eventType,
// * -eventId
// */
//public class BannerEventDetailActivity extends MyToolbarBackActivity implements BannerEventDetailInterface.View,
//        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
//
//    public static final String EXTRA_EVENT_TYPE = "event_type";
//    public static final String EXTRA_EVENT_ID = "event_id";
//
//
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.appbar_layout)
//    AppBarLayout appbarLayout;
//    @BindView(R.id.text_availableSlotTitle)
//    TextView textAvailableSlotTitle;
//    @BindView(R.id.card_AvailableSlot)
//    CardView cardAvailableSlot;
//    @BindView(R.id.text_EventDetailsTitle)
//    TextView textEventDetailsTitle;
//    @BindView(R.id.line_eventDetails)
//    View lineEventDetails;
//    @BindView(R.id.text_eventDesc)
//    TextView textEventDesc;
//    @BindView(R.id.text_startRegis_title)
//    TextView textStartRegisTitle;
//    @BindView(R.id.text_startRegis)
//    TextView textStartRegis;
//    @BindView(R.id.text_endRegis_title)
//    TextView textEndRegisTitle;
//    @BindView(R.id.text_endRegis)
//    TextView textEndRegis;
//    @BindView(R.id.text_dateTimeTitle)
//    TextView textDateTimeTitle;
//    @BindView(R.id.text_dateTime)
//    TextView textDateTime;
//    @BindView(R.id.text_classShiftTitle)
//    TextView textClassShiftTitle;
//    @BindView(R.id.text_classSpeciality)
//    TextView textClassSpeciality;
//    @BindView(R.id.text_price)
//    TextView textPrice;
//    @BindView(R.id.text_location)
//    TextView textLocation;
//    @BindView(R.id.card_EventDetails)
//    CardView cardEventDetails;
//    @BindView(R.id.btn_bookingEventDetail)
//    Button btnBookingEventDetail;
//    @BindView(R.id.layout_ParentEventDetail)
//    RelativeLayout layoutParentEventDetail;
//    @BindView(R.id.text_availableSlot)
//    TextView textAvailableSlot;
//    @BindView(R.id.text_priceTitle)
//    TextView textPriceTitle;
//    @BindView(R.id.text_locationTitle)
//    TextView textLocationTitle;
//    @BindView(R.id.text_trainerNameTitle)
//    TextView textTrainerNameTitle;
//    @BindView(R.id.text_trainerName)
//    TextView textTrainerName;
//    @BindView(R.id.image_Event)
//    RoundedImageView imageEvent;
//    @BindView(R.id.text_noInternet)
//    TextView textNoInternet;
//    @BindView(R.id.layout_noInternet)
//    LinearLayout layoutNoInternet;
//    @BindView(R.id.progressBar_loading)
//    ProgressBar progressBarLoading;
//    @BindView(R.id.layout_scroll)
//    ScrollView layoutScroll;
//    @BindView(R.id.swipeRefresh_event)
//    SwipeRefreshLayout swipeRefreshEvent;
//    @BindView(R.id.btn_copyClipboard)
//    Button btnCopyClipboard;
//    @BindView(R.id.layout_classAndVoucherCode)
//    RelativeLayout layoutClassAndVoucherCode;
//
//    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
//    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
//    private static final int LOCATION_REQUEST = 123;
//    private static final int ERROR_DIALOG_REQUEST = 456;
//
//    private BannerEventDetailInterface.Presenter bannerDetailEventPresenter;
//    private String trainerId, voucherCodeTemp,eventLocation,eventDate,eventShift,eventClass;
//    private List<EventBookingSpecialityRequestModel> eventBookingSpecialityRequestModel = new ArrayList<>();
//    private EventBookingSpecialityRequestModel requestModelTemp;
//    private RequestOptions requestOptions = new RequestOptions();
//    private DateFormat formatter;
//    private Date currentDate;
//    private ArrayList<String> eventBookingSpecialityRequestList = new ArrayList<String>();
//    private int totalPrice;
//    private String trainerName;
//
//    double latEvent, longEvent;
//    GoogleMap gMap;
//    GoogleApiClient googleApiClient;
//    private SupportMapFragment mapFragment;
//    LatLng eventLoc;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.legacy_activity_banner_event_detail);
//        ButterKnife.bind(this);
//
//        initToolbar(toolbar);
//        //get current date
//
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Event);
//
//        formatter = new SimpleDateFormat("dd MMMM yyyy");
//        currentDate = Calendar.getInstance().getTime();
//        formatter.format(currentDate);
//
//
//        //Get data from intent
//        //TODO: Rename these variables to bannerId and bannerType
//        Intent intent = getIntent();
//        String eventId = intent.getStringExtra(EXTRA_EVENT_ID);
//        String eventType = intent.getStringExtra(EXTRA_EVENT_TYPE);
//
//        getLocationPermission();
//        bannerDetailEventPresenter = new BannerEventDetailPresenter(this, this, this);
//        bannerDetailEventPresenter.loadBanner(eventType, eventId);
//
//        //Set up swipe refresh listener
//        setupSwipeRefreshLayout(eventType, eventId);
//
//
//        //Load banner image
//        String eventImageUrl = intent.getStringExtra("event_image");
//        Glide.with(this).load(eventImageUrl)
//                .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(1280, 800))
//                .into(imageEvent);
////        googleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API)
////                .addApi(Places.PLACE_DETECTION_API).enableAutoManage(this, this).build();
//    }
//
//    private void setupSwipeRefreshLayout(String eventId, String eventType){
//        swipeRefreshEvent.setColorSchemeResources(R.color.orange);
//        swipeRefreshEvent.setOnRefreshListener(() -> {
//            layoutScroll.setVisibility(View.VISIBLE);
//            layoutNoInternet.setVisibility(View.GONE);
//            bannerDetailEventPresenter.loadBanner(eventType, eventId);
//            swipeRefreshEvent.setRefreshing(false);
//        });
//    }
//
//    @Override
//    public void showLoading() {
//        progressBarLoading.setVisibility(View.VISIBLE);
//        layoutScroll.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void dismissLoading() {
//        progressBarLoading.setVisibility(View.GONE);
//        layoutScroll.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void showNoInternetError() {
//        dismissLoading();
//        layoutScroll.setVisibility(View.GONE);
//        layoutNoInternet.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void showMap() {
//        if(isLocationEnabled()){
//            mapFragment.getMapAsync(this);
//        } else {
//            getLocationPermission();
//        }
//    }
//
//    @Override
//    public void hideMap() {
//        mapFragment.getView().setVisibility(View.GONE);
//    }
//
//    @Override
//    public void navigateToBookingEvent(String eventId) {
//        Intent itn = new Intent(this, BookingSessionActivity.class);
//        itn.putExtra("eventId", eventId);
//        itn.putStringArrayListExtra("eventBookingSpecialityRequestList", eventBookingSpecialityRequestList);
//        itn.putExtra(Constants.IntentExtras.TRAINER_ID, trainerId);
//        itn.putExtra(Constants.IntentExtras.TRAINER_NAME, this.trainerName);
//        itn.putExtra("type", "event");
//        itn.putExtra("totalPrice", totalPrice);
//        itn.putExtra("eventLocation", eventLocation);
//        itn.putExtra("latEvent", latEvent);
//        itn.putExtra("longEvent", longEvent);
//        itn.putExtra("date", eventDate);
//        itn.putExtra("shift", eventShift);
//        itn.putExtra("class", eventClass);
//        startActivity(itn);
//    }
//
//    @Override
//    public void navigateToLogin(String eventId, String eventType) {
//        Intent i = new Intent(this, LoginActivity.class);
//        i.putExtra(LoginActivity.FLAG, LoginActivity.OPEN_BANNER_EVENT);
//        i.putExtra(EXTRA_EVENT_ID, eventId);
//        i.putExtra(EXTRA_EVENT_TYPE, eventType);
//        showActivity(i);
//    }
//
//    @Override
//    public void navigateToSearchTrainer(String voucherCode, String voucherDiscountType, int discountValue) {
//        Intent i = new Intent(this, SearchTrainerActivity.class);
//        i.putExtra(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
//        i.putExtra(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, discountValue);
//        i.putExtra(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, voucherDiscountType);
//        showActivity(i);
//    }
//
//    private void getLocationPermission() {
//        if(!isLocationEnabled()){
//            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST);
//        }
//    }
//
//    private boolean isLocationEnabled(){
//        return (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//        && (ContextCompat.checkSelfPermission(this, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case LOCATION_REQUEST:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                    startActivity(new Intent(this, SearchTrainerActivity.class));
//                }
//        }
//    }
//
//    @Override
//    public void doShowEventDetail(BannerEventDetailModel bannerEventDetailModel) {
//        dismissLoading();
//
//        Glide.with(this).load(bannerEventDetailModel.getUrl())
//                .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(1280, 800))
//                .into(imageEvent);
//
//        Long startRegis = bannerEventDetailModel.getRegistrationStartDate();
//        DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String dateFormate = df.format(startRegis);
//        textStartRegis.setVisibility(View.VISIBLE);
//        textStartRegisTitle.setVisibility(View.VISIBLE);
//        textStartRegis.setText(dateFormate);
//
//        Long endRegis = bannerEventDetailModel.getRegistrationEndDate();
//        DateFormat mDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String mDate = mDateFormat.format(endRegis);
//        textEndRegis.setVisibility(View.VISIBLE);
//        textEndRegisTitle.setVisibility(View.VISIBLE);
//        textEndRegis.setText(mDate);
//
//        long date = bannerEventDetailModel.getEventDate();
//        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String dateFormatted = dateFormat.format(date);
//        DateFormat dateFormat2 = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
//        String dateFormatted2 = dateFormat2.format(date);
//        eventDate = dateFormatted2; //TODO: parse date into boooking for order detail
//
//        long startDate = bannerEventDetailModel.getRegistrationStartDate();
//        DateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String a = dateFormat1.format(startDate);
//
//        Date registrationDate = new Date();
//
//        try {
//            registrationDate = formatter.parse(a);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if (currentDate.after(registrationDate) || currentDate.equals(registrationDate)) {
//            btnBookingEventDetail.setVisibility(View.VISIBLE);
//        } else if (currentDate.before(registrationDate)) {
//            btnBookingEventDetail.setVisibility(View.GONE);
//        }
//
//        String hourStartTime = bannerEventDetailModel.getEventStartTime().getHour().toString();
//        String minuteStartTime = bannerEventDetailModel.getEventStartTime().getMinute().toString();
//        String startTime = String.valueOf(hourStartTime + "." + minuteStartTime);
//
//        String hourEndTime = bannerEventDetailModel.getEventEndTime().getHour().toString();
//        String minuteEndTime = bannerEventDetailModel.getEventEndTime().getMinute().toString();
//        String endTime = String.valueOf(hourEndTime + "." + minuteEndTime);
//
//        textAvailableSlot.setText(String.valueOf(bannerEventDetailModel.getCurrentQuota())+" / "+bannerEventDetailModel.getQuota());
//        textEventDesc.setText(bannerEventDetailModel.getDescription());
//        btnBookingEventDetail.setText(getResources().getString(R.string.booking_now));
//
//        for (int i = 0; i < bannerEventDetailModel.getTrainerList().size(); i++) {
//            if (bannerEventDetailModel.getTrainerList().size() == 1) {
//                String name = bannerEventDetailModel.getTrainerList().get(i).getTrainerName();
//                textTrainerName.setText(name);
//                this.trainerName = name;
//            } else {
//                textTrainerName.append(bannerEventDetailModel.getTrainerList().get(i).getTrainerName());
//                textTrainerName.append("  ");
//            }
//        }
//
//        textDateTime.setText(String.valueOf(dateFormatted + " , " +  addDigit(startTime)
//                + " - " + addDigit(endTime)));
//        eventShift = addDigit(startTime) + " - " + addDigit(endTime);//TODO: parse date into boooking for order detail
//        textClassSpeciality.setText(bannerEventDetailModel.getSpecialities());
//        eventClass = bannerEventDetailModel.getSpecialities();//TODO: parse date into boooking for order detail
//
//        Locale localeID = new Locale("in", "ID");
//        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
//        String price = formatRupiah.format(bannerEventDetailModel.getPrice());
//        textPrice.setText(String.valueOf("Rp " + price));
//        totalPrice = bannerEventDetailModel.getPrice();
//
//        textLocation.setText(bannerEventDetailModel.getAddress());
//        eventLocation = bannerEventDetailModel.getAddress();
//
//        trainerId = bannerEventDetailModel.getTrainerList().get(0).getTrainerId();
//
//        for (int i = 0; i < bannerEventDetailModel.getSpecialityList().size(); i++) {
//            eventBookingSpecialityRequestList.add(String.valueOf(bannerEventDetailModel
//                    .getSpecialityList().get(i).getSpecialityName()));
//            //requestModelTemp = new EventBookingSpecialityRequestModel(String.valueOf(bannerEventDetailModel
//            //        .getSpecialityList().get(i).getSpecialityName()));
//            //eventBookingSpecialityRequestModel.add(requestModelTemp);
//        }
//
//        latEvent = bannerEventDetailModel.getLatitude();
//        longEvent = bannerEventDetailModel.getLongitude();
//        eventLoc = new LatLng(latEvent, longEvent);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (gMap == null){
//                    handler.postDelayed(this, 1000);
//                }  else {
//                    gMap.getUiSettings().setZoomControlsEnabled(true);
//                    MarkerOptions marker = new MarkerOptions().position(eventLoc).title("Event Location");
//                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinpoint_marker));
//                    gMap.addMarker(marker);
//                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLoc, 15f));
//                    gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                        @Override
//                        public void onMapClick(LatLng latLng) {
//                            String url = "https://www.google.com/maps/dir/?api=1&destination=" + latEvent + "," +
//                                    longEvent + "&travelmode=driving";
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            intent.setPackage("com.google.android.apps.maps");
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        }, 1000);
//
//    }
//
//    @Override
//    public void doShowVoucherDetail(VoucherDetailModel voucherDetailModel) {
//        dismissLoading();
//        btnCopyClipboard.setVisibility(View.VISIBLE);
//
//        cardAvailableSlot.setVisibility(View.GONE);
////        textAvailableSlot.setText(String.valueOf(voucherDetailModel.getCurrentQuantity()));
//        textEventDetailsTitle.setText(getResources().getString(R.string.voucher_details_title));
//        textEventDesc.setText(String.valueOf(voucherDetailModel.getDescription()));
//        textTrainerNameTitle.setVisibility(View.GONE);
//        textTrainerName.setVisibility(View.GONE);
//        textEndRegisTitle.setVisibility(View.GONE);
//        textEndRegis.setVisibility(View.GONE);
//        textStartRegisTitle.setVisibility(View.GONE);
//        textStartRegis.setVisibility(View.GONE);
//
//        toolbar.setTitle(getResources().getString(R.string.voucher_toolbar_title));
//
//        long startDate = voucherDetailModel.getStartDate();
//        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String startDateFormatted = dateFormat.format(startDate);
//
//        long endDate = voucherDetailModel.getEndDate();
//        DateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//        String endDateFormatted = dateFormat1.format(endDate);
//
//        textDateTime.setText(String.valueOf(startDateFormatted + " - " + endDateFormatted));
//
//        textClassShiftTitle.setText(getResources().getString(R.string.voucher_details_code_title));
//        textClassSpeciality.setText(voucherDetailModel.getCode());
//
//        voucherCodeTemp = String.valueOf(voucherDetailModel.getCode());
//
//        Locale localeID = new Locale("in", "ID");
//        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
//        String price = formatRupiah.format(voucherDetailModel.getValue());
//
//        textPrice.setText(String.valueOf("Rp. " + price));
//
//        textLocationTitle.setVisibility(View.GONE);
//        textLocation.setVisibility(View.GONE);
//        btnBookingEventDetail.setText(getResources().getString(R.string.voucher_details_btn_text));
//    }
//
//    @Override
//    public void doShowAdvertisementDetail(AdvertisementDetailModel advertisementDetailModel) {
//        dismissLoading();
//
//        cardAvailableSlot.setVisibility(View.GONE);
//        btnBookingEventDetail.setVisibility(View.GONE);
//        textClassSpeciality.setVisibility(View.GONE);
//        textClassShiftTitle.setVisibility(View.GONE);
//        textLocationTitle.setVisibility(View.GONE);
//        textLocation.setVisibility(View.GONE);
//        textPriceTitle.setVisibility(View.GONE);
//        textPrice.setVisibility(View.GONE);
//        textEndRegisTitle.setVisibility(View.GONE);
//        textEndRegis.setVisibility(View.GONE);
//        textStartRegisTitle.setVisibility(View.GONE);
//        textStartRegis.setVisibility(View.GONE);
//        textDateTime.setVisibility(View.GONE);
//        textDateTimeTitle.setVisibility(View.GONE);
//
//
//        toolbar.setTitle(getString(R.string.advertisement));
//
////        toolbarTitle.setText(getResources().getString(R.string.ads_toolbar_title));
//
//        textEventDetailsTitle.setText(getResources().getString(R.string.ads_detail_title));
//        textEventDesc.setText(String.valueOf(advertisementDetailModel.getName()));
//        textTrainerNameTitle.setText(getResources().getString(R.string.ads_name_title));
//        textTrainerName.setText(String.valueOf(advertisementDetailModel.getDescription()));
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        gMap = googleMap;
//    }
//
//    public void isServiceOK() {
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
//        if (available == ConnectionResult.SUCCESS) {
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }
//    }
//
//    public static String addDigit(String number) {
//        String[] arrays = number.split("\\.");
//        String result = "";
//
//        for (int i = 0; i < arrays.length; i++) {
//            if (arrays[i].length() == 1) {
//                result = result + "0" + arrays[i];
//            } else {
//                result = result + arrays[i];
//            }
//            if (i != arrays.length - 1) {
//                result = result + ".";
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        MufitDialogOneButtonWithText dialog =  new MufitDialogOneButtonWithText(this,
//                getString(R.string.error),
//                getString(R.string.maps_failed)
//        );
//        dialog.getButton()
//                .setOnClickListener(l -> showActivity(new Intent(this, SearchTrainerActivity.class)));
//        dialog.show();
//    }
//
//    @OnClick({R.id.btn_copyClipboard, R.id.btn_bookingEventDetail})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_copyClipboard:
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText("voucher_code", voucherCodeTemp);
//                clipboard.setPrimaryClip(clip);
//                showToastMessageLong("Voucher code is copied to clipboard");
//                break;
//            case R.id.btn_bookingEventDetail:
//                dismissLoading();
//                bannerDetailEventPresenter.bookEvent();
//                break;
//        }
//    }
//}
