package com.nostratech.mufit.consumer.booking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.model.MyVoucherModel;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.MufitUtils;
import com.nostratech.mufit.consumer.utils.MySupportMapFragment;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;
import id.mufit.core.dialog.MufitDialogTwoButtonsWithText;
import id.mufit.core.utils.TypefaceHelper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

//TODO: Change layout view names and variable names
public class BookingSessionActivity extends MyToolbarBackActivity implements BookingSessionContract.View,
        TransactionFinishedCallback {

    public static final String TAG = BookingSessionActivity.class.getSimpleName();

    private static final int AUTOCOMPLETE_REQUEST_CODE = 22;

    private static final float DEFAULT_ZOOM = 17f;

    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private static final String API_KEY = "AIzaSyBNz22vZi56UJ0fep5OkjTWHDFzHv1E8oE";

    List<Place.Field> fields = Arrays.asList(
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG);

    @BindView(R.id.bookSession_toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_pick_location)
    TextView textPickLabel;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;
    @BindView(R.id.bookSession_scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.txt_address_chosen)
    TextView txtAddressChosen;

    @BindView(R.id.button_my_voucher)
    Button buttonMyVoucher;

    //Parent layout yg isinya label voucher dan button voucher
    @BindView(R.id.layout_voucher)
    ViewGroup layoutVoucher;
    @BindView(R.id.text_myVoucherName)
    TextView textMyVoucherPackageName;

    @BindView(R.id.button_show_voucher)
    Button btnShowVoucher;
    @BindView(R.id.button_cancel_voucher_use)
    ImageButton buttonCancelVoucherUse;

    @BindView(R.id.spinner_payment_method)
    Spinner spinnerPaymentMethod;


    @BindView(R.id.choose_payment_method_label)
    TextView textChoosePaymentMethod;
    @BindView(R.id.layout_choose_payment_method)
    LinearLayout layoutChoosePaymentMethod;
    @BindView(R.id.text_termAndCondition)
    TextView textTermAndCondition;
    @BindView(R.id.text_date_string)
    TextView textDateString;
    @BindView(R.id.text_shift_string)
    TextView textShiftString;
    @BindView(R.id.text_class_string)
    TextView textClassString;
    @BindView(R.id.text_trainer_string)
    TextView textTrainerString;

    @BindView(R.id.text_note_user)
    TextView textNoteUser;

    @BindView(R.id.layout_biaya_tambahan)
    RelativeLayout layoutBiayaTambahan;
    @BindView(R.id.layout_detail_booking)
    LinearLayout layoutDetaiBooking;
    @BindView(R.id.text_biaya_tambahan_string)
    TextView textBiayaTambahanString;

    @BindView(R.id.layout_discount)
    RelativeLayout layoutDiscount;
    @BindView(R.id.text_discount_string)
    TextView textDiscountString;

    @BindView(R.id.text_original_price)
    TextView textOriginalPrice;

    @BindView(R.id.text_final_price)
    TextView textFinalPrice;

    //View that mimics the shape of Google Maps fragment. Used for show tutorial
    @BindView(R.id.view_map)
    View view_map;

    private BookingSessionPresenter presenter;

    private PaymentMethodSpinnerAdapter paymentMethodSpinnerAdapter;

    private GoogleMap mMap;
    private MySupportMapFragment mapFragment;

    TutorialManager tutorialManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_session);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        if (!MufitUtils.locationCheck(this) && MufitUtils.hasGpsDevice(this)) {
            MufitUtils.enableLoc(this);
        }
        getLocationPermission();
        mapFragment = ((MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

        presenter = new BookingSessionPresenter(this, this, this);
        presenter.initializeData(getIntent().getExtras());
        mapFragment.getMapAsync(presenter);

        tutorialManager = new TutorialManager(this);
        showTutorial();

        textTermAndCondition.setMovementMethod(LinkMovementMethod.getInstance());

        initPaymentMethodSpinner();

        buildSDKMidtrans();
    }

    @OnClick({R.id.button_confirm, R.id.button_my_voucher, R.id.text_pick_location, R.id.view_clear_voucher})
    public void onViewClicked(View view) {
        dismissKeyboard();
        textNoteUser.clearFocus();
        switch (view.getId()) {
            case R.id.text_pick_location:
                launchGooglePlacesSearch();
                break;
            case R.id.button_confirm:
                presenter.onBookingClick();
                break;
            case R.id.button_my_voucher:
                presenter.onChooseVoucherClick();
                break;
            case R.id.view_clear_voucher:
                presenter.updateVoucherCode(null, null, 0);
                break;
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        final Geocoder geocoder = new Geocoder(this);
//        mMap = googleMap;
//
//        ((MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                .setListener(new MySupportMapFragment.OnTouchListener() {
//                    @Override
//                    public void onTouch() {
//                        //To avoid scrollView from receiving touch events
//                        scrollView.requestDisallowInterceptTouchEvent(true);
//                    }
//                });
//
//
//        Places.initialize(getApplicationContext(), API_KEY);
//
//        if (isLocationPermissionGranted()) {
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
//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    try {
//                        Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
//                        presenter.setTrainingLocation(address.getAddressLine(0),
//                                latLng.latitude,
//                                latLng.longitude);
//                    } catch (IOException e) {
//                        Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
//                    }
//                }
//            });
//        }
//    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        Geocoder geocoder = new Geocoder(BookingSessionActivity.this);
                        try {
                            String address = geocoder.getFromLocation(currentLocation.getLatitude(),
                                    currentLocation.getLongitude(), 1).get(0).getAddressLine(0);
                            double latitude = currentLocation.getLatitude();
                            double longitude = currentLocation.getLongitude();
                            presenter.setTrainingLocation(address, latitude, longitude);
                        } catch (Exception e) {
                            MufitUtils.enableLoc(BookingSessionActivity.this);
                        }

                    } else {
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(BookingSessionActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng) {
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.training_location));
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinpoint_marker));
        mMap.addMarker(options);
        dismissKeyboard();
    }


    @Override
    public void updateMap(String address, double lat, double lng) {
        txtAddressChosen.setText(address);
        moveCamera(new LatLng(lat, lng));
    }

    @Override
    public void setOriginalPrice(String originalPrice) {
        textOriginalPrice.setText(originalPrice);
    }

    @Override
    public void setFinalPriceAndServiceFee(String finalPrice, String serviceFee) {
        textFinalPrice.setText(finalPrice);
        textBiayaTambahanString.setText(serviceFee);
    }

    public void buildSDKMidtrans() {
        SdkUIFlowBuilder.init()
                .setClientKey(MufitUtils.MIDTRANS_CLIENT_KEY) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(MufitUtils.MIDTRANS_BASE_URL) //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
//              .setColorTheme(new CustomColorTheme("#000000", "#2d89ef", "#00ff00")) // set theme. it will replace theme on snap theme on MAP ( optional)
                .buildSDK();
    }

    @Override
    public void onVoucherUsed(String discountValue, String voucherCode) {

        //Show discount text
        textDiscountString.setText(discountValue);
        layoutDiscount.setVisibility(View.VISIBLE);

        //Show X button
        buttonCancelVoucherUse.setVisibility(View.VISIBLE);

        //Show ">" arrow button
        btnShowVoucher.setVisibility(View.GONE);

        //Allow click on Voucher button
        buttonMyVoucher.setEnabled(false);

        //Show voucher code
        textMyVoucherPackageName.setText(voucherCode);
        textMyVoucherPackageName.setVisibility(View.VISIBLE);
    }

    @Override
    public void onVoucherRemoved() {
        //Hide discount text
        textDiscountString.setText(" ");
        layoutDiscount.setVisibility(View.GONE);

        //Hide X button
        buttonCancelVoucherUse.setVisibility(View.GONE);

        //Show ">" arrow button
        btnShowVoucher.setVisibility(View.VISIBLE);

        //Allow click on Voucher button
        buttonMyVoucher.setEnabled(true);

        //Hide voucher code
        textMyVoucherPackageName.setText(" ");
        textMyVoucherPackageName.setVisibility(View.GONE);
    }

    @Override
    public void showBookingEventUI() {
        textPickLabel.setVisibility(View.GONE);

        //Hide voucher karena tidak bisa digunakan untuk event
        layoutVoucher.setVisibility(View.GONE);
        layoutDiscount.setVisibility(View.GONE);

        //Hide user notes
        textNoteUser.setVisibility(View.GONE);
    }

    @Override
    public void showPaymentMethods(List<PaymentMethodModel> paymentMethodModels) {
        dismissLoading();
        textChoosePaymentMethod.setVisibility(View.VISIBLE);
        layoutChoosePaymentMethod.setVisibility(View.VISIBLE);
        layoutBiayaTambahan.setVisibility(View.VISIBLE);

        List<String> listLabel = new ArrayList<>();
        for (PaymentMethodModel model : paymentMethodModels) {
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getInstance(localeID);

            String paymentName = model.getName();
            String serviceFee = model.getServiceFee();
            double serviceFeeDouble = Double.parseDouble(serviceFee);
            String label = "%s (+Rp %s)";
            listLabel.add(String.format(label, paymentName, formatRupiah.format(serviceFeeDouble)));
        }

        paymentMethodSpinnerAdapter =
                new PaymentMethodSpinnerAdapter(this, R.layout.booking_package_class_spinner,
                        R.id.spinner_text, listLabel, paymentMethodModels);
        spinnerPaymentMethod.setAdapter(paymentMethodSpinnerAdapter);
    }

    @Override
    public void hidePaymentMethodsAndServiceFee() {
        textChoosePaymentMethod.setVisibility(View.GONE);
        layoutChoosePaymentMethod.setVisibility(View.GONE);
        layoutBiayaTambahan.setVisibility(View.GONE);
    }

    @Override
    public void setTrainerDetails(String trainerName, String specialityName, String trainingDate, String trainingTime) {
        textTrainerString.setText(trainerName);
        textDateString.setText(trainingDate);
        textShiftString.setText(trainingTime);
        textClassString.setText(specialityName);
    }

    @Override
    public void onBookingSuccessFreeOfCharge() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.dialog_success),
                getString(R.string.success_booking)
        );

        dialog.getButton().setOnClickListener(l -> {
            //TODO: Open History fragment
            Intent i = new Intent(this, RootActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(Constants.RootNavigation.EXTRA_NAVIGATE_TO, RootActivity.NavigateTo.HISTORY);
            showActivity(i);
        });

        dialog.show();
    }

    @Override
    public void openBankTransferPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER);
    }

    @Override
    public void openCreditCardPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.CREDIT_CARD);
    }

    @Override
    public void openGoPayTransferPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.GO_PAY);
    }

    @Override
    public void openVoucherSelectionScreen(String trainerSpecialityId) {
        Intent i = new Intent(this, MyVoucherActivity.class);
        i.putExtra(Constants.IntentExtras.TRAINER_SPECIALITY_ID, trainerSpecialityId);
        startActivityForResult(i, 1);
    }

    @Override
    public void showConfirmationDialogEvent() {
        MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
                getString(R.string.confirmation),
                getString(R.string.create_booking_event_question));

        dialog.getBtnPositive().setOnClickListener(l -> {
            presenter.postBookingEvent();
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void showConfirmationDialogSession() {
        MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
                getString(R.string.confirmation),
                getString(R.string.confirmation_booking));

        dialog.getBtnPositive().setOnClickListener(v -> {
            String notes = textNoteUser.getText().toString().trim();
            if (notes.isEmpty()) notes = null;

            presenter.postBookingSession(notes);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void setGoogleMap(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //To avoid scrollView from receiving touch events
        mapFragment.setListener(() ->  scrollView.requestDisallowInterceptTouchEvent(true));
    }

    @Override
    public void showUserCurrentLocation() {

        Places.initialize(getApplicationContext(), API_KEY);

        final Geocoder geocoder = new Geocoder(this);

        if (isLocationPermissionGranted()) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    try {
                        Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                        presenter.setTrainingLocation(address.getAddressLine(0),
                                latLng.latitude,
                                latLng.longitude);
                    } catch (IOException e) {
                        Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void navigateToHistory() {
        Intent intent = new Intent(this, RootActivity.class);
        intent.putExtra(RootActivity.OPEN_HISTORY, RootActivity.OPEN_HISTORY_BOOKING);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showActivity(intent);
    }

    @Override
    public void successCancelBookingUnprocessed() {
        Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddressNotSet() {
        showGenericError(getResources().getString(R.string.choose_address));
    }

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


    private void launchGooglePlacesSearch() {
        if(!Places.isInitialized()){
            getLocationPermission();
            return;
        }
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("ID")
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, REQUIRED_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, REQUIRED_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED;
    }

    private void getLocationPermission() {
        if (!isLocationPermissionGranted()) {
            ActivityCompat.requestPermissions(this,
                    REQUIRED_PERMISSIONS,
                    1234);
        }
    }

    private void initPaymentMethodSpinner() {
        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onPaymentMethodSelected(paymentMethodSpinnerAdapter.getModel(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            String orderId = result.getResponse().getOrderId();
            String message = null;
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    message = "Transaction Finished. ID: " + orderId;
                    navigateToHistory();
                    break;
                case TransactionResult.STATUS_PENDING:
                    message = "Transaction Pending. ID: " + orderId;
                    navigateToHistory();
                    break;
                case TransactionResult.STATUS_FAILED:
                    message = "Transaction Failed. ID: " + orderId + ". Message: " + result.getResponse().getStatusMessage();
                    break;
            }

            showToastMessageLong(message);
//            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {

            presenter.cancelBookingUnprocessed();

        } else {

            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                showToastMessageLong("Transaction Invalid");
            } else {
                showToastMessageLong("Transaction Finished with failure.");
                navigateToHistory();
            }

        }
    }

    private void showTutorial() {

        if (tutorialManager.isTutorialBookingFinished()) return;

        TutorialBuilder tb = new TutorialBuilder(this, TypefaceHelper.get(TypefaceHelper.TRUENO_MEDIUM, this));

        tb.addSequenceItemRectangleShape(layoutDetaiBooking,
                getString(R.string.next),
                getString(R.string.booking_showcase_text),
                null,
                true);
        if (textPickLabel.getVisibility() != View.GONE) {
            tb.addSequenceItemRectangleShape(textPickLabel,
                    getString(R.string.next),
                    getString(R.string.location_showcase_text),
                    null,
                    true);
        }
        tb.addSequenceItemRectangleShape(view_map,
                getString(R.string.next),
                getString(R.string.map_showcase_text),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                                Handler handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (textNoteUser.getVisibility() == View.GONE) {
                                            showTutorialPayment();
                                        } else {
                                            showTutorialNotes();
                                        }
                                    }
                                }, 100);
                            }
                        });
                    }
                },
                true);

        tb.start();
    }

    private void showTutorialPayment() {
        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));

        tb.addSequenceItemRectangleShape(layoutChoosePaymentMethod,
                getString(R.string.next),
                getString(R.string.choose_payment_showcase),
                null,
                true);

        if (layoutVoucher.getVisibility() != View.GONE) {
            tb.addSequenceItemRectangleShape(buttonMyVoucher,
                    getString(R.string.next),
                    getString(R.string.voucher_showcase_text),
                    null,
                    true);
        }
        tb.addSequenceItemRectangleShape(buttonConfirm,
                getString(R.string.finish_showcase),
                getString(R.string.confirm_showcase_text),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        tutorialManager.finishTutorialBooking();
                    }
                },
                true);
        tb.start();
    }

    private void showTutorialNotes() {
        TutorialBuilder tb = new TutorialBuilder(this, TypefaceHelper.get(TypefaceHelper.TRUENO_MEDIUM, this));
        tb.addSequenceItemRectangleShape(textNoteUser,
                getString(R.string.next),
                getString(R.string.text_note_user_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);

                                Handler handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showTutorialPayment();
                                    }
                                }, 100);
                            }
                        });
                    }
                },
                true);
        tb.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                presenter.setTrainingLocation(place.getAddress(), latLng.latitude, latLng.longitude);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        //Voucher intent code
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                MyVoucherActivity.VoucherType voucherType =
                        (MyVoucherActivity.VoucherType) data.getSerializableExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_TYPE);

                String voucherCode = null;
                String voucherDiscountType = null;
                int discount = 0;

                switch (voucherType) {
                    case VOUCHER_CODE:
                        voucherCode = data.getStringExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_CODE);
                        discount = data.getIntExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);
                        voucherDiscountType = data.getExtras().getString(com.nostratech.mufit.consumer.utils.Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE,
                                com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL);
                        break;
                    case VOUCHER_PACKAGE:
                        MyVoucherModel myVoucherModel = data.getParcelableExtra(com.nostratech.mufit.consumer.utils.Constants.IntentExtras.VOUCHER_MODEL);

                        voucherCode = myVoucherModel.getCode();
                        discount = myVoucherModel.getValue();
                        voucherDiscountType = com.nostratech.mufit.consumer.utils.Constants.Voucher.DISCOUNT_NOMINAL;
                        break;
                }

                //Apply voucher
                presenter.updateVoucherCode(voucherCode, voucherDiscountType, discount);
            }
        }
    }

}
