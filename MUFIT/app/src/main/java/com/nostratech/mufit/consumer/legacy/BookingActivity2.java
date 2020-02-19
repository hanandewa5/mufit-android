//package com.nostratech.mufit.consumer.legacy;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.method.LinkMovementMethod;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.widget.NestedScrollView;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.widget.Autocomplete;
//import com.google.android.libraries.places.widget.AutocompleteActivity;
//import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
//import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
//import com.midtrans.sdk.corekit.core.Constants;
//import com.midtrans.sdk.corekit.core.LocalDataHandler;
//import com.midtrans.sdk.corekit.core.MidtransSDK;
//import com.midtrans.sdk.corekit.core.PaymentMethod;
//import com.midtrans.sdk.corekit.core.TransactionRequest;
//import com.midtrans.sdk.corekit.models.BillingAddress;
//import com.midtrans.sdk.corekit.models.CustomerDetails;
//import com.midtrans.sdk.corekit.models.ExpiryModel;
//import com.midtrans.sdk.corekit.models.ItemDetails;
//import com.midtrans.sdk.corekit.models.UserAddress;
//import com.midtrans.sdk.corekit.models.UserDetail;
//import com.midtrans.sdk.corekit.models.snap.TransactionResult;
//import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
//import com.nostratech.mufit.consumer.booking.PaymentMethodSpinnerAdapter;
//import com.nostratech.mufit.consumer.model.MyVoucherModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingSpecialityRequestModel;
//import com.nostratech.mufit.consumer.model.booking.BookingShiftList;
//import com.nostratech.mufit.consumer.model.booking.BookingSpecialityList;
//import com.nostratech.mufit.consumer.model.booking.CreateBookingRequestModel;
//import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
//import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
//import com.nostratech.mufit.consumer.root.RootActivity;
//import com.nostratech.mufit.consumer.utils.FontUtils;
//import com.nostratech.mufit.consumer.utils.MufitUtils;
//import com.nostratech.mufit.consumer.utils.MySupportMapFragment;
//import com.nostratech.mufit.consumer.legacy.SessionManager;
//import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
//import com.nostratech.mufit.consumer.utils.TutorialBuilder;
//import com.nostratech.mufit.consumer.utils.TutorialManager;
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import id.mufit.core.data.AppCache;
//import id.mufit.core.dialog.MufitDialogOneButtonWithText;
//import id.mufit.core.dialog.MufitDialogTwoButtonsWithText;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.subjects.PublishSubject;
//import io.reactivex.subjects.Subject;
//import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
//
//import static java.security.AccessController.getContext;
//
//public class BookingActivity2 extends MyToolbarBackActivity implements BookingContract2.View,
//        OnMapReadyCallback,
//        GoogleApiClient.OnConnectionFailedListener,
//        TransactionFinishedCallback{
//
//    final int AUTOCOMPLETE_REQUEST_CODE = 22;
//
//    final String apiKey = "AIzaSyBNz22vZi56UJ0fep5OkjTWHDFzHv1E8oE";
//
//    List<Place.Field> fields = Arrays.asList(
//            Place.Field.ADDRESS,
//            Place.Field.LAT_LNG);
//
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.text_pick_location)
//    TextView textPickLabel;
//    @BindView(R.id.text_title_payment)
//    TextView textTitlePayment;
//    @BindView(R.id.button_confirm)
//    Button buttonConfirm;
//    @BindView(R.id.scroll_view)
//    NestedScrollView scrollView;
//    @BindView(R.id.txt_address_chosen)
//    TextView txtAddressChosen;
//
//    @BindView(R.id.button_my_voucher)
//    Button buttonMyVoucher;
//
//    //Parent layout yg isinya label voucher dan button voucher
//    @BindView(R.id.layout_voucher)
//    ViewGroup layoutVoucher;
//    @BindView(R.id.text_myVoucherName)
//    TextView textMyVoucherPackageName;
//
//    @BindView(R.id.button_show_voucher)
//    Button btnShowVoucher;
//    @BindView(R.id.button_cancel_voucher_use)
//    ImageButton buttonCancelVoucherUse;
//
//    @BindView(R.id.spinner_payment_method)
//    Spinner spinnerPaymentMethod;
//
//    @BindView(R.id.choose_payment_method_label)
//    TextView textChoosePaymentMethod;
//    @BindView(R.id.layout_choose_payment_method)
//    LinearLayout layoutChoosePaymentMethod;
//    @BindView(R.id.text_termAndCondition)
//    TextView textTermAndCondition;
//    @BindView(R.id.text_date_string)
//    TextView textDateString;
//    @BindView(R.id.text_shift_string)
//    TextView textShiftString;
//    @BindView(R.id.text_class_string)
//    TextView textClassString;
//    @BindView(R.id.text_trainer_string)
//    TextView textTrainerString;
//
//    @BindView(R.id.text_note_user)
//    TextView textNoteUser;
//
//    @BindView(R.id.layout_biaya_tambahan)
//    RelativeLayout layoutBiayaTambahan;
//    @BindView(R.id.layout_detail_booking)
//    LinearLayout layoutDetaiBooking;
//    @BindView(R.id.text_biaya_tambahan_string)
//    TextView textBiayaTambahanString;
//
//    @BindView(R.id.layout_discount)
//    RelativeLayout layoutDiscount;
//    @BindView(R.id.text_discount_string)
//    TextView textDiscountString;
//
//    @BindView(R.id.text_original_price)
//    TextView textOriginalPrice;
//
//    @BindView(R.id.text_final_price)
//    TextView textFinalPrice;
//    @BindView(R.id.text_title_order_detail)
//    TextView textTitleOrderDetail;
//    @BindView(R.id.view_map)
//    View view_map;
//
//    private PaymentMethodSpinnerAdapter paymentMethodSpinnerAdapter;
//    public static final String TAG = BookingActivity2.class.getSimpleName();
//
//    private static final float DEFAULT_ZOOM = 17f;
//    boolean mLocationPermissionsGranted = false;
//    private GoogleMap mMap;
//
//    //Initially null. Can only be set through onActivityResult
//    String voucherCode;
//    String discountType;
//    int originalPrice = 0;
//    int serviceFee = 0;
//    int discount = 0;
//
//    String bookingType = "", eventId;
//    String trainerId;
//    Boolean isJoinShifts;
//    Long timeStamp;
//    List<BookingShiftList> bookingShiftList;
//    List<BookingSpecialityList> bookingSpecialityList;
//    BookingPresenter2 bookingPresenter2;
//    CreateBookingRequestModel requestBooking;
//    TutorialManager tutorialManager;
//    private List<EventBookingSpecialityRequestModel> eventBookingSpecialityRequestModel = new ArrayList<>();
//    private String eventLocation;
//    private double latEvent, longEvent;
//
//    private boolean freeOfCharge = false;
//
//    NumberFormat rupiahFormatter;
//
//    Subject<String> mSubject = PublishSubject.create();
//    Disposable mObservable;
//
//    private AppCache appCache;
//
//    //Called whenever voucher code is updated, which updates voucher code, discount and final price texts
//    private void updateVoucherCode(String voucherCode, int discountValue, String discountType) {
//
//        //assign voucherCode
//        this.voucherCode = voucherCode;
//        this.discountType = discountType;
//        if(requestBooking != null) requestBooking.setVoucherCode(voucherCode);
//        // notify the Observable that the value just change
//        mSubject.onNext(voucherCode == null ? "" : voucherCode);
//
//        if (voucherCode == null){
//            buttonCancelVoucherUse.setVisibility(View.GONE);
//            btnShowVoucher.setVisibility(View.VISIBLE);
//            buttonMyVoucher.setEnabled(true);
//
//        }else{
//            buttonCancelVoucherUse.setVisibility(View.VISIBLE);
//            btnShowVoucher.setVisibility(View.GONE);
//            buttonMyVoucher.setEnabled(false);
//        }
//
//        //assign discount value
//        this.discount = discountValue;
//
//        calculateServiceFee();
//        updatePrice();
//    }
//
//    public void initVoucherObservable() {
//        mObservable = mSubject.map(value -> value)
//                .subscribe(value -> {
//                    if (!value.isEmpty()) {
//                        setVoucherCodeText(value);
//                        textMyVoucherPackageName.setVisibility(View.VISIBLE);
//                    } else {
//                        textMyVoucherPackageName.setVisibility(View.GONE);
//                    }
//                });
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking2);
//        ButterKnife.bind(this);
//        bookingPresenter2 = new BookingPresenter2(this, this, this);
//        tutorialManager = new TutorialManager(this);
//
//        if (!MufitUtils.locationCheck(this) && MufitUtils.hasGpsDevice(this)) {
//            MufitUtils.enableLoc(this);
//        }
//
//        Locale localeID = new Locale("in", "ID");
//        rupiahFormatter = NumberFormat.getInstance(localeID);
//        initVoucherObservable();
//        retrieveIntentExtras();
//
//        if (!tutorialManager.isTutorialBookingFinished()) {
//            presentShowcaseSequence();
//        }
//
//        initToolbar(toolbar);
//
//        initRequestBooking();
//        getLocationPermission();
//
//        Bundle data = getIntent().getExtras();
//        if (data != null) {
//            originalPrice = data.getInt("totalPrice");
//        }
//
//        textTermAndCondition.setMovementMethod(LinkMovementMethod.getInstance());
//
//        initPaymentMethodSpinner();
//
//        buildSDKMidtrans();
//    }
//
//
//    private void initPaymentMethodSpinner(){
//        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                String serviceFeeStr = paymentMethodSpinnerAdapter.getModel(position).getServiceFee();
//                serviceFee = (int) Double.parseDouble(serviceFeeStr);
//                setServiceFeesText(serviceFee);
//                updatePrice();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    private int calculatePriceAfterDiscount() {
//        int finalPrice;
//        int discountValue = discount;
//
//        if (discountType.equals(com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL)) {
//            finalPrice = originalPrice - discount;
//        } else {
//            discountValue = discount * originalPrice / 100;
//            finalPrice = originalPrice - discountValue;
//        }
//
//        setDiscountValueText(discountValue);
//
//        if (finalPrice < 0) finalPrice = 0;
//        return finalPrice;
//    }
//
//
//    private void updatePrice(){
//        int priceAfterDisc = calculatePriceAfterDiscount();
//
//        //If free, no need for service fee
//        if(priceAfterDisc == 0){
//            togglePaymentMethodVisibility(false);
//            setTotalPriceText(0);
//            freeOfCharge = true;
//        } else {
//            setTotalPriceText(priceAfterDisc + serviceFee);
//            freeOfCharge = false;
//        }
//    }
//
//    private void togglePaymentMethodVisibility(boolean visible){
//
//        if(visible){
//            textChoosePaymentMethod.setVisibility(View.VISIBLE);
//            layoutChoosePaymentMethod.setVisibility(View.VISIBLE);
//            layoutBiayaTambahan.setVisibility(View.VISIBLE);
//        } else {
//            textChoosePaymentMethod.setVisibility(View.GONE);
//            layoutChoosePaymentMethod.setVisibility(View.GONE);
//            layoutBiayaTambahan.setVisibility(View.GONE);
//        }
//    }
//
//    private void calculateServiceFee(){
//        bookingPresenter2.getPaymentMethodList(calculatePriceAfterDiscount());
//    }
//
//    private void setOriginalPriceText(int price) {
//        textOriginalPrice.setText("Rp "+rupiahFormatter.format(price));
//    }
//
//    private void setTotalPriceText(int price) {
//        textFinalPrice.setText(rupiahFormatter.format(price));
//    }
//
//    private void setDiscountValueText(int value) {
//        if (discount == 0){
//            textDiscountString.setText(" ");
//            layoutDiscount.setVisibility(View.GONE);
//        }else {
//            layoutDiscount.setVisibility(View.VISIBLE);
//            textDiscountString.setText("- Rp "+rupiahFormatter.format(value));
//        }
//
//    }
//
//    private void setVoucherCodeText(String voucherCode){
//        textMyVoucherPackageName.setText(voucherCode);
//    }
//
//    private void setServiceFeesText(int price) {
//        textBiayaTambahanString.setText("+ Rp "+rupiahFormatter.format(price));
//    }
//
//
//    private void retrieveIntentExtras() {
//        Bundle data = getIntent().getExtras();
//        if (data != null) {
//
//            String bookingTypeTemp = data.getString("type");
//
//            //Original price before discount
//            originalPrice = data.getInt("totalPrice");
//            trainerId = data.getString(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.TRAINER_ID);
//            int discountValue = data.getInt(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);
//            String voucherCode = data.getString(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_CODE, null);
//            String voucherDiscountType = data.getString(
//                    com.nostratech.mufit.consumer.utils.Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE,
//                    com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL);
//
//
//            String date = data.getString("date");
//            String shift = data.getString("shift");
//            String classname = data.getString("class");
//            String trainer = data.getString(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.TRAINER_NAME);
//
//            textTrainerString.setText(trainer);
//
//            //Set session date text
//            textDateString.setText(date);
//
//            //Set session time text
//            textShiftString.setText(shift);
//
//            //Set session class/speciality type text
//            textClassString.setText(classname);
//
//            //Set original price text
//            setOriginalPriceText(originalPrice);
//
//            //Set voucher code (if available)
//            updateVoucherCode(voucherCode, discountValue, voucherDiscountType);
//
//            //Handle event bookings differently
//            if (bookingTypeTemp.equalsIgnoreCase("event")) {
//                eventId = data.getString("eventId");
//                bookingType = "event";
//                eventLocation = data.getString("eventLocation");
//                latEvent = data.getDouble("latEvent");
//                longEvent = data.getDouble("longEvent");
//                ArrayList<String> eventBookingSpecialityRequestList = data.getStringArrayList("eventBookingSpecialityRequestList");
//                for (int i = 0; i < eventBookingSpecialityRequestList.size(); i++) {
//                    EventBookingSpecialityRequestModel requestModelTemp = new EventBookingSpecialityRequestModel(String.valueOf(String.valueOf(eventBookingSpecialityRequestList.get(i))));
//                    eventBookingSpecialityRequestModel.add(requestModelTemp);
//                }
//                textPickLabel.setVisibility(View.GONE);
//
//                //Hide voucher karena tidak bisa digunakan untuk event
//                layoutVoucher.setVisibility(View.GONE);
//                layoutDiscount.setVisibility(View.GONE);
//
//                //Hide user notes
//                textNoteUser.setVisibility(View.GONE);
//            } else {
//                timeStamp = data.getLong("timeStamp");
//                isJoinShifts = data.getBoolean("joinShifts");
//                bookingShiftList = data.getParcelableArrayList("bookingShift");
//                bookingSpecialityList = data.getParcelableArrayList("bookingSpeciality");
//            }
//        } else {
//            //Handle error no intent extras
//        }
//    }
//
//    private void getLocationPermission() {
//        Log.d(TAG, "getLocationPermission: getting location permissions");
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                mLocationPermissionsGranted = true;
//                initMap();
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        permissions,
//                        1234);
//            }
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    permissions,
//                    1234);
//        }
//    }
//
//    private void initMap() {
//        Log.d(TAG, "initMap: initializing map");
//        SupportMapFragment mapFragment = (MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(BookingActivity2.this);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        Log.d(TAG, "onMapReady: map is ready");
//        final Geocoder geocoder = new Geocoder(BookingActivity2.this);
//        mMap = googleMap;
//
//        ((MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                .setListener(new MySupportMapFragment.OnTouchListener() {
//                    @Override
//                    public void onTouch() {
//                        scrollView.requestDisallowInterceptTouchEvent(true);
//                    }
//                });
//
//
//        if (mLocationPermissionsGranted) {
//            getDeviceLocation();
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            mMap.setMyLocationEnabled(true);
//            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.getUiSettings().setZoomControlsEnabled(true);
//            if (!bookingType.equals("event")) {
//                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        try {
//                            Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
//                            txtAddressChosen.setText(address.getAddressLine(0));
//                            requestBooking.setAddress(address.getAddressLine(0));
//                            requestBooking.setLatitude(latLng.latitude);
//                            requestBooking.setLongitude(latLng.longitude);
//
//                            moveCamera(latLng, DEFAULT_ZOOM,
//                                    "");
//                        } catch (IOException e) {
//                            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
//                        }
//                    }
//                });
//            }
//            init();
//        }
//    }
//
//    private void init() {
//        Places.initialize(getApplicationContext(), apiKey);
//    }
//
//    private void getDeviceLocation() {
//        Log.d(TAG, "getDeviceLocation: getting the devices current location");
//
//        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try {
//            if (mLocationPermissionsGranted) {
//
//                final Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "onComplete: found location!");
//                            Location currentLocation = (Location) task.getResult();
//                            Geocoder geocoder = new Geocoder(BookingActivity2.this);
//                            try {
//                                if (!bookingType.equals("event")) {
//                                    txtAddressChosen.setText(geocoder.getFromLocation(currentLocation.getLatitude(),
//                                            currentLocation.getLongitude(), 1).get(0).getAddressLine(0));
//                                    requestBooking.setAddress(geocoder.getFromLocation(currentLocation.getLatitude(),
//                                            currentLocation.getLongitude(), 1).get(0).getAddressLine(0));
//                                    requestBooking.setLatitude(currentLocation.getLatitude());
//                                    requestBooking.setLongitude(currentLocation.getLongitude());
//
//                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
//                                            DEFAULT_ZOOM,
//                                            "");
//                                } else {
//                                    txtAddressChosen.setText(eventLocation);
//                                    requestBooking.setAddress(eventLocation);
//                                    requestBooking.setLatitude(latEvent);
//                                    requestBooking.setLongitude(longEvent);
//                                    moveCamera(new LatLng(latEvent, longEvent), DEFAULT_ZOOM, eventLocation);
//                                }
//                            } catch (Exception e) {
//                                MufitUtils.enableLoc(BookingActivity2.this);
//                            }
//
//                        } else {
//                            Log.d(TAG, "onComplete: current location is null");
//                            Toast.makeText(BookingActivity2.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
//        }
//    }
//
//    private void moveCamera(LatLng latLng, float zoom, String title) {
//        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title(title);
//        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinpoint_marker));
//        mMap.addMarker(options);
//        dismissKeyboard();
//    }
//
//
//    @Override
//    public void showLoading() {
//        runOnUiThread(() -> showProgressDialog(this));
//    }
//
//    @Override
//    public void dismissLoading() {
//        runOnUiThread(() -> dismissProgressDialog());
//    }
//
//    @Override
//    public void showNoInternetError() {
//        showGenericError(getString(R.string.no_internet));
//    }
//
//    @Override
//    public void initRequestBooking() {
//
//        requestBooking = new CreateBookingRequestModel();
//        requestBooking.setBookingShiftList(bookingShiftList);
//        requestBooking.setBookingSpecialityList(bookingSpecialityList);
//        requestBooking.setCustomerName(getSessionManager().getUserDetails().get(SessionManager.KEY_FULLNAME));
//        requestBooking.setCustomerPhone(getSessionManager().getUserDetails().get(SessionManager.KEY_PHONE));
//        requestBooking.setDateBooking(timeStamp);
//        requestBooking.setJoinShift(isJoinShifts);
//        requestBooking.setTrainer(trainerId);
//    }
//
//    @Override
//    public void showConfirmationDialog() {
//        dismissKeyboard();
//        dismissLoading();
//        if (bookingType.equals("event")) {
//
//            MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
//                    getString(R.string.confirmation),
//                    getString(R.string.create_booking_event_question));
//
//            dialog.getBtnPositive().setOnClickListener(l -> bookingPresenter2.createEventBooking(getSessionManager().getUserDetails()
//                    .get(SessionManager.KEY_FULLNAME), getSessionManager().getUserDetails()
//                    .get(SessionManager.KEY_PHONE), eventId, eventBookingSpecialityRequestModel, trainerId));
//            dialog.show();
//
//        } else {
//            MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
//                    getString(R.string.confirmation),
//                    getString(R.string.confirmation_booking));
//
//            dialog.getBtnPositive().setOnClickListener(v -> {
//                String notes = textNoteUser.getText().toString().trim();
//                if (notes.isEmpty()) notes = null;
//                requestBooking.setNotes(notes);
//
//                bookingPresenter2.createBooking(requestBooking);
//            });
//            dialog.show();
//        }
//    }
//
//    @Override
//    public void successBooking(final String orderId) {
//        dismissKeyboard();
//        //If final price to be paid is zero (no fees), skip Midtrans payment section
//        if(freeOfCharge){
//            dismissLoading();
//            MufitDialogOneButtonWithText dialog =  new MufitDialogOneButtonWithText(this,
//                    getString(R.string.dialog_success),
//                    getString(R.string.success_booking)
//                    //TODO: open history fragment in rootactivity
//                    );
//            dialog.getButton().setOnClickListener(l -> {
//                Intent i = new Intent(this, RootActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                showActivity(i);
//            });
//        } else {
//            processMidtrans(orderId);
//        }
//
//    }
//
//    public void processMidtrans(String booking_id) {
//
//        PaymentMethodModel paymentMethodModel =
//                paymentMethodSpinnerAdapter.getModel(spinnerPaymentMethod.getSelectedItemPosition());
//
//        buildSDKMidtrans();
//
//        UserDetail userDetail = new UserDetail();
//        userDetail.setUserFullName(getSessionManager().getUserDetails().get(SessionManager.KEY_FULLNAME));
//        userDetail.setEmail(getSessionManager().getUserDetails().get(SessionManager.KEY_EMAIL));
//        userDetail.setPhoneNumber(getSessionManager().getUserDetails().get(SessionManager.KEY_PHONE));
//        userDetail.setUserId(getSessionManager().getUserDetails().get(SessionManager.KEY_CLIENT_ID));
//
//        ArrayList<UserAddress> userAddresses = new ArrayList<>();
//
//        UserAddress userAddress = new UserAddress();
//        userAddress.setAddress(requestBooking.getAddress());
//        userAddress.setCity("Jakarta");
//        userAddress.setCountry("Indonesia");
//        userAddress.setZipcode("100000");
//        userAddress.setAddressType(Constants.ADDRESS_TYPE_BOTH);
//        userAddresses.add(userAddress);
//
//        userDetail.setUserAddresses(userAddresses);
//
//        LocalDataHandler.saveObject("user_details", userDetail);
//
//        TransactionRequest transactionRequest = new TransactionRequest(booking_id, 100000);
//        ItemDetails itemDetails1 = new ItemDetails("id", 100000, 1, "dummy");
//        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
//        itemDetailsList.add(itemDetails1);
//
//        transactionRequest.setItemDetails(itemDetailsList);
//
//        BillingAddress ba = new BillingAddress();
//        ba.setAddress("Jakarta");
//
//        CustomerDetails cd = new CustomerDetails();
//        cd.setEmail(getSessionManager().getUserDetails().get(SessionManager.KEY_EMAIL));
//        cd.setBillingAddress(ba);
//        cd.setFirstName(getSessionManager().getUserDetails().get(SessionManager.KEY_FULLNAME));
//        cd.setPhone(getSessionManager().getUserDetails().get(SessionManager.KEY_PHONE));
//
//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
//        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
//        String nowAsISO = df.format(System.currentTimeMillis());
//
//        ExpiryModel em = new ExpiryModel();
//        em.setStartTime(nowAsISO);
//        em.setDuration(2);
//        em.setUnit(ExpiryModel.UNIT_HOUR);
//        transactionRequest.setExpiry(em);
//
//        transactionRequest.setCustomerDetails(cd);
//
//        transactionRequest.setCustomField1(String.valueOf(paymentMethodModel.getId()));
//
//        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
//        Log.d("MIDTRANS", String.valueOf(spinnerPaymentMethod.getSelectedItem()));
//        dismissLoading();
//        if (paymentMethodModel.getId() == 1) {
//            MidtransSDK.getInstance().startPaymentUiFlow(BookingActivity2.this, PaymentMethod.BANK_TRANSFER);
//        } else if (paymentMethodModel.getId() == 2) {
//            MidtransSDK.getInstance().startPaymentUiFlow(BookingActivity2.this, PaymentMethod.CREDIT_CARD);
//        }else if (paymentMethodModel.getId() == 3 ){
//            MidtransSDK.getInstance().startPaymentUiFlow(BookingActivity2.this, PaymentMethod.GO_PAY);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                setMapLoc(place);
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i(TAG, status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//
//        //Voucher intent code
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                MyVoucherActivity.VoucherType voucherType =
//                        (MyVoucherActivity.VoucherType) data.getSerializableExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_TYPE);
//
//                String voucherCode = null;
//                String voucherDiscountType = null;
//                int discount = 0;
//
//                switch (voucherType) {
//                    case VOUCHER_CODE:
//                        voucherCode = data.getStringExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_CODE);
//                        discount = data.getIntExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);
//                        voucherDiscountType = data.getExtras().getString(com.nostratech.mufit.consumer.utils.Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE,
//                                com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL);
//                        break;
//                    case VOUCHER_PACKAGE:
//                        MyVoucherModel myVoucherModel = data.getParcelableExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_MODEL);
//
//                        voucherCode = myVoucherModel.getCode();
//                        discount = myVoucherModel.getValue();
//                        voucherDiscountType = com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL;
//                        break;
//                }
//
//                //Apply voucher
//                updateVoucherCode(voucherCode, discount, voucherDiscountType);
//            }
//        }
//    }
//
//    @OnClick({R.id.button_confirm, R.id.button_my_voucher, R.id.text_pick_location, R.id.view_clear_voucher})
//    public void onViewClicked(View view) {
//        removeFocusFromNotesAndHideKeyboard();
//        switch (view.getId()) {
//            case R.id.text_pick_location:
//                launchGooglePlacesSearch();
//                break;
//            case R.id.button_confirm:
//                //Set voucher code
//                if (voucherCode != null) {
//                    requestBooking.setVoucherCode(voucherCode);
//                }
//
//                if (requestBooking.getAddress() == null
//                        || requestBooking.getLatitude() == null || requestBooking.getLongitude() == null) {
//                    showGenericError(getResources().getString(R.string.choose_address));
//                } else {
//                    showConfirmationDialog();
//                }
//                break;
//
//            case R.id.button_my_voucher:
//                Intent i = new Intent(this, MyVoucherActivity.class);
//                String trainerSpecialityId = bookingSpecialityList.get(0).getIdTrainerSpeciality();
//                i.putExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.TRAINER_SPECIALITY_ID, trainerSpecialityId);
//                ActivityCompat.startActivityForResult(this, i, 1, null);
//                break;
//            case R.id.view_clear_voucher:
//                updateVoucherCode(null,0, discountType);
//                break;
//        }
//    }
//
//    private void launchGooglePlacesSearch() {
//        Intent intent = new Autocomplete.IntentBuilder(
//                AutocompleteActivityMode.FULLSCREEN, fields)
//                .setCountry("ID")
//                .build(this);
//        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
//    }
//
//
//    @Override
//    public void doShowPaymentMethod(List<PaymentMethodModel> paymentMethodModels) {
//        dismissLoading();
//        togglePaymentMethodVisibility(true);
//
//        List<String> listLabel = new ArrayList<>();
//        for(PaymentMethodModel model : paymentMethodModels){
//            Locale localeID = new Locale("in", "ID");
//            NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
//
//            String paymentName = model.getName();
//            String serviceFee = model.getServiceFee();
//            double serviceFeeDouble = Double.parseDouble(serviceFee);
//            String label = "%s (+Rp %s)";
//            listLabel.add(String.format(label, paymentName, formatRupiah.format(serviceFeeDouble)));
//        }
//
//        paymentMethodSpinnerAdapter =
//                new PaymentMethodSpinnerAdapter(this,  R.layout.booking_package_class_spinner,
//                        R.id.spinner_text, listLabel, paymentMethodModels);
//        spinnerPaymentMethod.setAdapter(paymentMethodSpinnerAdapter);
//    }
//
//    @Override
//    public void successCancelBookingUnprocessed() {
//        Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onTransactionFinished(TransactionResult result) {
//        if (result.getResponse() != null) {
//            switch (result.getStatus()) {
//                case TransactionResult.STATUS_SUCCESS:
//                    //TODO: open history fragment in rootactivity
//                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(this, RootActivity.class);
//                    intent.putExtra(RootActivity.OPEN_HISTORY,RootActivity.OPEN_HISTORY_BOOKING);
//                    showActivity(intent);
//                    break;
//                case TransactionResult.STATUS_PENDING:
//                    //TODO: open history fragment in rootactivity
//                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    Intent intent1 = new Intent(this, RootActivity.class);
//                    intent1.putExtra(RootActivity.OPEN_HISTORY,RootActivity.OPEN_HISTORY_BOOKING);
//                    showActivity(intent1);
//                    break;
//                case TransactionResult.STATUS_FAILED:
//                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
//                    break;
//            }
//            result.getResponse().getValidationMessages();
//        } else if (result.isTransactionCanceled()) {
//
//            bookingPresenter2.cancelBookingUnprocessed();
//        } else {
//            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
//                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
//            } else {
//                //TODO: open history fragment in rootactivity
//                Intent intent = new Intent(this, RootActivity.class);
//                intent.putExtra(RootActivity.OPEN_HISTORY,RootActivity.OPEN_HISTORY_BOOKING);
//                showActivity(intent);
//                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    public void buildSDKMidtrans() {
//        SdkUIFlowBuilder.init()
//                .setClientKey(MufitUtils.MIDTRANS_CLIENT_KEY) // client_key is mandatory
//                .setContext(BookingActivity2.this) // context is mandatory
//                .setTransactionFinishedCallback(BookingActivity2.this) // set transaction finish callback (sdk callback)
//                .setMerchantBaseUrl(MufitUtils.MIDTRANS_BASE_URL) //set merchant url (required)
//                .enableLog(true) // enable sdk log (optional)
////              .setColorTheme(new CustomColorTheme("#000000", "#2d89ef", "#00ff00")) // set theme. it will replace theme on snap theme on MAP ( optional)
//                .buildSDK();
//    }
//
//
//
//    private void setMapLoc(Place place){
//        LatLng latLng = place.getLatLng();
//        moveCamera(latLng, DEFAULT_ZOOM, "");
//        txtAddressChosen.setText(place.getAddress());
//        requestBooking.setAddress(place.getAddress());
//        requestBooking.setLatitude(latLng.latitude);
//        requestBooking.setLongitude(latLng.longitude);
//    }
//
//    private void removeFocusFromNotesAndHideKeyboard() {
//        dismissKeyboard();
//        textNoteUser.clearFocus();
//    }
//
//    private void presentShowcaseSequence() {
//
//        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));
//
//        tb.addSequenceItemRectangleShape(layoutDetaiBooking,
//                getString(R.string.next),
//                getString(R.string.booking_showcase_text),
//                null,
//                true);
//        if (textPickLabel.getVisibility() != View.GONE) {
//            tb.addSequenceItemRectangleShape(textPickLabel,
//                    getString(R.string.next),
//                    getString(R.string.location_showcase_text),
//                    null,
//                    true);
//        }
//        tb.addSequenceItemRectangleShape(view_map,
//                getString(R.string.next),
//                getString(R.string.map_showcase_text),
//                new ShowcaseDismissedListener() {
//                    @Override
//                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
//                        scrollView.post(new Runnable() {
//                            public void run() {
//                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//
//                                Handler handler = new Handler();
//
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (textNoteUser.getVisibility() == View.GONE) {
//                                            presentShowcaseSequence2();
//                                        } else {
//                                            showTutorialNotes();
//                                        }
//                                    }
//                                }, 100);
//                            }
//                        });
//                    }
//                },
//                true);
//
//        tb.start();
//    }
//
//    private void showTutorialNotes() {
//        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(getContext()));
//        tb.addSequenceItemRectangleShape(textNoteUser,
//                getString(R.string.next),
//                getString(R.string.text_note_user_showcase),
//                new ShowcaseDismissedListener() {
//                    @Override
//                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
//                        scrollView.post(new Runnable() {
//                            public void run() {
//                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//
//                                Handler handler = new Handler();
//
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        presentShowcaseSequence2();
//                                    }
//                                }, 100);
//                            }
//                        });
//                    }
//                },
//                true);
//        tb.start();
//    }
//
//    private void presentShowcaseSequence2() {
//        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));
//
//        tb.addSequenceItemRectangleShape(layoutChoosePaymentMethod,
//                getString(R.string.next),
//                getString(R.string.choose_payment_showcase),
//                null,
//                true);
//
//        if (layoutVoucher.getVisibility() != View.GONE) {
//            tb.addSequenceItemRectangleShape(buttonMyVoucher,
//                    getString(R.string.next),
//                    getString(R.string.voucher_showcase_text),
//                    null,
//                    true);
//        }
//        tb.addSequenceItemRectangleShape(buttonConfirm,
//                getString(R.string.finish_showcase),
//                getString(R.string.confirm_showcase_text),
//                new ShowcaseDismissedListener() {
//                    @Override
//                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
//                        tutorialManager.finishTutorialBooking();
//                    }
//                },
//                true);
//        tb.start();
//    }
//}
//
////    public void isServicesOK() {
////        Log.d(TAG, "isServicesOK: checking google services version");
////
////        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BookingActivity2.this);
////
////        if (available == ConnectionResult.SUCCESS) {
////            //everything is fine and the user can make map requests
////            Log.d(TAG, "isServicesOK: Google Play Services is working");
////            ;
////        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
////            //an error occured but we can resolve it
////            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
////            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(BookingActivity2.this, available, ERROR_DIALOG_REQUEST);
////            dialog.show();
////        } else {
////            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
////        }
////    }
//
////private void init(){
//
////        mGoogleApiClient = new GoogleApiClient
////                .Builder(this)
////                .addApi(Places.GEO_DATA_API)
////                .addApi(Places.PLACE_DETECTION_API)
////                .enableAutoManage(this, this)
////                .build();
////
////        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
////
////        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
////                LAT_LNG_BOUNDS, new AutocompleteFilter.Builder()
////                .setCountry("ID")
////                .build());
////
////        mSearchText.setAdapter(mPlaceAutocompleteAdapter);
////
////        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
////            @Override
////            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
////                if (actionId == EditorInfo.IME_ACTION_SEARCH
////                        || actionId == EditorInfo.IME_ACTION_DONE
////                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
////                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
////
////                    //execute our method for searching
////                    geoLocate();
////                }
////
////                return false;
////            }
////        });
///*
//        mGps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked gps icon");
//                getDeviceLocation();
//            }
//        });*/
////}
//
//
////    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
////        @Override
////        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////            hideKeyboard(getCurrentFocus());
////
////            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
////            final String placeId = item.getPlaceId();
////
////            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
////                    .getPlaceById(mGoogleApiClient, placeId);
////            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
////        }
////    };
//
////    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
////        @Override
////        public void onResult(@NonNull PlaceBuffer places) {
////            if (!places.getStatus().isSuccess()) {
////                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
////                places.release();
////                return;
////            }
////            final Place place = places.get(0);
////
////            try {
////                mPlace = new PlaceInfo();
////                mPlace.setName(place.getName().toString());
////                Log.d(TAG, "onResult: name: " + place.getName());
////                mPlace.setAddress(place.getAddress().toString());
////                Log.d(TAG, "onResult: address: " + place.getAddress());
////                mPlace.setId(place.getId());
////                Log.d(TAG, "onResult: id:" + place.getId());
////                mPlace.setLatlng(place.getLatLng());
////                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
////                mPlace.setRating(place.getRating());
////                Log.d(TAG, "onResult: rating: " + place.getRating());
////                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
////                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
////                mPlace.setWebsiteUri(place.getWebsiteUri());
////                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());
////
////                Log.d(TAG, "onResult: place: " + mPlace.toString());
////            } catch (NullPointerException e) {
////                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
////            }
////
////            if(bookingType.equals("event")){
////                moveCamera(new LatLng(latEvent,longEvent), DEFAULT_ZOOM, eventLocation);
////            }else {
////                moveCamera(new LatLng(place.getViewport().getCenter().latitude,
////                        place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());
////            }
////            mSearchText.setText("");
////            mSearchText.clearFocus();
////            txtAddressChosen.setText(place.getAddress().toString());
////            requestBooking.setAddress(place.getAddress().toString());
////            requestBooking.setLatitude(place.getViewport().getCenter().latitude);
////            requestBooking.setLongitude(place.getViewport().getCenter().longitude);
////            places.release();
////        }
////    };
//
//
////    private void geoLocate() {
////
////        if(bookingType.equals("event")){
////            moveCamera(new LatLng(latEvent, longEvent), DEFAULT_ZOOM,
////                    eventLocation);
////
////        }else {
////            Log.d(TAG, "geoLocate: geolocating");
////
////            String searchString = mSearchText.getText().toString();
////
////            Geocoder geocoder = new Geocoder(BookingActivity2.this);
////            List<Address> list = new ArrayList<>();
////            try {
////                list = geocoder.getFromLocationName(searchString, 1);
////            } catch (IOException e) {
////                Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
////            }
////
////            if (list.size() > 0) {
////                Address address = list.get(0);
////
////                Log.d(TAG, "geoLocate: found a location: " + address.toString());
////                //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
////
////                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
////                        address.getAddressLine(0));
////
//////
//////            requestBooking.setAddress(address.toString());
//////            requestBooking.setLatitude(address.getLatitude());
//////            requestBooking.setLongitude(address.getLongitude());
////            }
////        }
////    }
