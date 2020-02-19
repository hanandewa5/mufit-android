package com.nostratech.mufit.consumer.booking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.midtrans.sdk.corekit.core.LocalDataHandler;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.BillingAddress;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ExpiryModel;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.UserAddress;
import com.midtrans.sdk.corekit.models.UserDetail;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.CreateBookingResponseModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingRequestModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingSpecialityRequestModel;
import com.nostratech.mufit.consumer.model.booking.BookingShiftList;
import com.nostratech.mufit.consumer.model.booking.BookingSpecialityList;
import com.nostratech.mufit.consumer.model.booking.CreateBookingRequestModel;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

//TODO: Separate event and session logic
public class BookingSessionPresenter extends MyBasePresenter implements BookingSessionContract.Presenter, OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {

        view.setGoogleMap(googleMap);

        if (isEvent) {
            view.updateMap(eventLocation, eventLat, eventLng);
        } else {
            view.showUserCurrentLocation();
        }
    }

    class ActiveVoucher {
        private String voucherCode;
        private String discountType;
        private int discountValue;

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public void setDiscountValue(int discountValue) {
            this.discountValue = discountValue;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public String getDiscountType() {
            return discountType;
        }

        public int getDiscountValue() {
            return discountValue;
        }

        public void clear() {
            this.voucherCode = null;
            this.discountType = null;
            this.discountValue = 0;
        }
    }

    private BookingSessionContract.View view;

    private CreateBookingRequestModel requestBooking;

    private EventBookingRequestModel eventBookingRequestModel;

    private ActiveVoucher activeVoucher = new ActiveVoucher();

    private CurrencyFormatter currencyFormatter;

    private PaymentMethodModel activePaymentMethod;

    private boolean isEvent = false;

    private int originalPrice = 0;

    private String lastBookingSecureId;

    private String eventLocation;
    private double eventLat;
    private double eventLng;

    BookingSessionPresenter(Context context, MvpView mvpView, BookingSessionContract.View view) {
        super(context, mvpView);
        this.view = view;
        this.currencyFormatter = new CurrencyFormatter();
    }

    @Override
    public void initializeData(Bundle data) {

        Objects.requireNonNull(data);

        originalPrice = data.getInt("totalPrice");
        view.setOriginalPrice(currencyFormatter.getRupiahString(originalPrice));

        String trainerId = data.getString(Constants.IntentExtras.TRAINER_ID);

        //Trainer details (First segment)
        String trainingDate = data.getString("date");
        String trainingTime = data.getString("shift");
        String specialityName = data.getString("class");
        String trainerName = data.getString(Constants.IntentExtras.TRAINER_NAME);

        view.setTrainerDetails(trainerName, specialityName, trainingDate, trainingTime);

        String bookingType = data.getString("type");

        if (bookingType.equalsIgnoreCase("event")) {
            this.isEvent = true;
            String eventId = data.getString("eventId");
            ArrayList<String> eventBookingSpecialityRequestList = data.getStringArrayList("eventBookingSpecialityRequestList");
            List<EventBookingSpecialityRequestModel> specialityRequestModels = new ArrayList<>();

            for (String s : Objects.requireNonNull(eventBookingSpecialityRequestList)) {
                EventBookingSpecialityRequestModel eventSpecialityModel = new EventBookingSpecialityRequestModel(s);
                specialityRequestModels.add(eventSpecialityModel);
                eventBookingRequestModel = new EventBookingRequestModel(getmAppCache().getUserFullName(),
                        getmAppCache().getPhoneNumber(),
                        eventId,
                        specialityRequestModels,
                        trainerId);
            }

            view.showBookingEventUI();

            //Update map to show location of the event
            eventLocation = data.getString("eventLocation");
            eventLat = data.getDouble("latEvent");
            eventLng = data.getDouble("longEvent");

        } else {
            long timeStamp = data.getLong("timeStamp");
            boolean isJoinShifts = data.getBoolean("joinShifts");
            List<BookingShiftList> bookingShiftList = data.getParcelableArrayList("bookingShift");
            List<BookingSpecialityList> bookingSpecialityList = data.getParcelableArrayList("bookingSpeciality");

            requestBooking = new CreateBookingRequestModel();
            requestBooking.setBookingShiftList(bookingShiftList);
            requestBooking.setBookingSpecialityList(bookingSpecialityList);
            requestBooking.setCustomerName(getmAppCache().getUserFullName());
            requestBooking.setCustomerPhone(getmAppCache().getPhoneNumber());
            requestBooking.setDateBooking(timeStamp);
            requestBooking.setJoinShift(isJoinShifts);
            requestBooking.setTrainer(trainerId);


        }

        int discountValue = data.getInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);
        String voucherCode = data.getString(Constants.IntentExtras.VOUCHER_CODE, null);
        String voucherDiscountType = data.getString(
                Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE,
                Constants.Voucher.DISCOUNT_NOMINAL);
        //Set voucher code (if available)
        updateVoucherCode(voucherCode, voucherDiscountType, discountValue);
    }

    /**
     * Changes the address, latitude and longitude of the user's desired training location
     * <p>
     * In the case of event booking, location cannot be changed. Instead, it is set only once.
     *
     * @param address - Full address of the training location
     * @param lat     - Latitude of the location
     * @param lng     - Longitude of the location
     * @see #initializeData(Bundle)
     */
    @Override
    public void setTrainingLocation(String address, double lat, double lng) {

        if (isEvent) return;

        requestBooking.setAddress(address);
        requestBooking.setLatitude(lat);
        requestBooking.setLongitude(lng);
        view.updateMap(address, lat, lng);
    }

    /**
     * Calculates service fee and final price, and stores the payment method used in memory to be used later when POSTing booking request.
     * Then, notify {@link BookingSessionContract.View} to update its UI with the service fee and final price.
     * <p>
     * If final price is <= 0, hide payment methods and service fee to indicate that this booking is free of charge.
     *
     * @param model - selected payment method POJO
     */
    @Override
    public void onPaymentMethodSelected(PaymentMethodModel model) {
        this.activePaymentMethod = model;

        int priceAfterDisc = calculatePriceAfterDiscount();
        int serviceFee = (int) Double.parseDouble(model.getServiceFee());
        int finalPrice;

        if (priceAfterDisc == 0) {
            view.hidePaymentMethodsAndServiceFee();
            finalPrice = 0;
        } else {
            finalPrice = priceAfterDisc + serviceFee;
        }

        view.setFinalPriceAndServiceFee(currencyFormatter.getRupiahString(finalPrice),
                currencyFormatter.getRupiahString(serviceFee));
    }

    @Override
    public void getPaymentMethodList(int price) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getPaymentMethod(price, getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<PaymentMethodModel>>() {
                }.getType();
                ArrayList<PaymentMethodModel> paymentMethods = getGson().fromJson(jsonArray, dataType);
                view.showPaymentMethods(paymentMethods);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void cancelBookingUnprocessed() {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().setCancelTransaction(lastBookingSecureId, getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> view.successCancelBookingUnprocessed()));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void onBookingClick() {

        if (isEvent) {
            view.showConfirmationDialogEvent();
        } else {

            if (requestBooking.getAddress() == null || requestBooking.getLatitude() == null || requestBooking.getLongitude() == null) {
                view.onAddressNotSet();
                return;
            }
            view.showConfirmationDialogSession();
        }
    }

    @Override
    public void onChooseVoucherClick() {
        String trainerSpecialityId = requestBooking.getBookingSpecialityList().get(0).getIdTrainerSpeciality();
        view.openVoucherSelectionScreen(trainerSpecialityId);
    }

    @Override
    public void postBookingSession(String notes) {
        requestBooking.setNotes(notes);
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().createBooking(getAccessToken(), requestBooking);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                CreateBookingResponseModel model = getGson().fromJson(jsonObject, CreateBookingResponseModel.class);
                lastBookingSecureId = model.getId();
                String orderId = model.getOrderId();
                onPostBookingSuccess(orderId);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    /**
     * Call when voucher has changed.
     * Will update the currently active voucher (which will be used when POSTing a request to our REST API)
     * and all price values .
     *
     * @param voucherCode   - code of the voucher being used
     * @param discountType  - Type of the discount, as defined in {@link Constants.Voucher}
     * @param discountValue - Nominal of discount as percentage or whole number
     */
    @Override
    public void updateVoucherCode(@Nullable String voucherCode, @Nullable String discountType, int discountValue) {
        if (!isEvent) {

            requestBooking.setVoucherCode(voucherCode);

            if (voucherCode == null) {
                view.onVoucherRemoved();
                activeVoucher.clear();
            } else {
                activeVoucher.setDiscountType(discountType);
                activeVoucher.setDiscountValue(discountValue);
                activeVoucher.setVoucherCode(voucherCode);

                view.onVoucherUsed(currencyFormatter.getRupiahString(calculateDiscount()), voucherCode);
            }

            getPaymentMethodList(calculatePriceAfterDiscount());
        } else {
            getPaymentMethodList(originalPrice);
        }
    }

    private int calculateDiscount() {
        if (activeVoucher.getDiscountType() == null) return 0;

        return activeVoucher.getDiscountType().equalsIgnoreCase(Constants.Voucher.DISCOUNT_NOMINAL)
                ? activeVoucher.getDiscountValue()
                : activeVoucher.getDiscountValue() * originalPrice / 100;
    }

    private int calculatePriceAfterDiscount() {
        int priceAfterDiscount = originalPrice - calculateDiscount();

        //Price can never go below 0
        if (priceAfterDiscount < 0) priceAfterDiscount = 0;

        return priceAfterDiscount;
    }


    @Override
    public void postBookingEvent() {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().createEventBooking(getAccessToken(), eventBookingRequestModel);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                CreateBookingResponseModel model = getGson().fromJson(jsonObject, CreateBookingResponseModel.class);
                lastBookingSecureId = model.getId();
                onPostBookingSuccess(model.getOrderId());
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    private void onPostBookingSuccess(String orderId) {
        getMvpView().dismissLoading();

        //If final price to be paid is zero (no fees), skip Midtrans payment section
        if (calculatePriceAfterDiscount() == 0) {
            view.onBookingSuccessFreeOfCharge();
        } else {
            setUpBookingForMidtrans(orderId, activePaymentMethod.getId());
        }
    }

    private void setUpBookingForMidtrans(String bookingId, int paymentMethodId) {

        String fullName = getmAppCache().getUserFullName();
        String email = getmAppCache().getEmail();
        String phoneNumber = getmAppCache().getPhoneNumber();

        UserDetail userDetail = new UserDetail();
        userDetail.setUserFullName(fullName);
        userDetail.setEmail(email);
        userDetail.setPhoneNumber(phoneNumber);
        userDetail.setUserId("customer");

        ArrayList<UserAddress> userAddresses = new ArrayList<>();

        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(requestBooking == null ? null : requestBooking.getAddress());
        userAddress.setCity("Jakarta");
        userAddress.setCountry("Indonesia");
        userAddress.setZipcode("100000");
        userAddress.setAddressType(com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH);
        userAddresses.add(userAddress);

        userDetail.setUserAddresses(userAddresses);

        //TODO: check what is the purpose of UserDetail
        LocalDataHandler.saveObject("user_details", userDetail);

        TransactionRequest transactionRequest = new TransactionRequest(bookingId, 100000);
        ItemDetails itemDetails1 = new ItemDetails("id", 100000, 1, "dummy");
        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
        itemDetailsList.add(itemDetails1);

        transactionRequest.setItemDetails(itemDetailsList);

        BillingAddress ba = new BillingAddress();
        ba.setAddress("Jakarta");

        //TODO: Research more on CustomerDetails
        CustomerDetails cd = new CustomerDetails();
        cd.setEmail(email);
        cd.setBillingAddress(ba);
        cd.setFirstName(fullName);
        cd.setPhone(phoneNumber);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        String nowAsISO = df.format(System.currentTimeMillis());

        ExpiryModel em = new ExpiryModel();
        em.setStartTime(nowAsISO);
        em.setDuration(2);
        em.setUnit(ExpiryModel.UNIT_HOUR);
        transactionRequest.setExpiry(em);

        transactionRequest.setCustomerDetails(cd);

        transactionRequest.setCustomField1(String.valueOf(paymentMethodId));

        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
        getMvpView().dismissLoading();

        switch (paymentMethodId) {
            case 1:
                view.openBankTransferPaymentScreen();
                break;
            case 2:
                view.openCreditCardPaymentScreen();
                break;
            case 3:
                view.openGoPayTransferPaymentScreen();
                break;
        }
    }
}
