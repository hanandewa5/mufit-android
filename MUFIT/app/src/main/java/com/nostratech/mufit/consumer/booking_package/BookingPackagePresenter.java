package com.nostratech.mufit.consumer.booking_package;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.JsonArray;
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
import com.nostratech.mufit.consumer.model.PackageClassModel;
import com.nostratech.mufit.consumer.model.PackagePriceModel;
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
import java.util.TimeZone;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

class BookingPackagePresenter extends MyBasePresenter implements BookingPackageContract.Presenter {

    private BookingPackageContract.View view;
    private List<PaymentMethodModel> paymentMethodModels;
    private List<PackageClassModel> packageClassModels;
    private List<PackagePriceModel> packagePriceModels;

    private CurrencyFormatter currencyFormatter;
    private CreateBookingRequestModel requestBooking;
    private String trainerId;

    private PackageClassModel selectedPackageClass;
    private PackagePriceModel selectedPackagePrice;
    private PaymentMethodModel selectedPaymentMethod;

    BookingPackagePresenter(Context context, MvpView mvpView, BookingPackageContract.View view) {
        super(context, mvpView);
        this.view = view;
        currencyFormatter = new CurrencyFormatter();
    }

    @Override
    public void initializeData(Bundle bundle) {
        String trainer = bundle.getString(Constants.IntentExtras.TRAINER_NAME);
        view.showTrainerName(trainer);
        trainerId = bundle.getString(Constants.IntentExtras.TRAINER_ID);

        requestBooking = new CreateBookingRequestModel();
        requestBooking.setTrainer(trainerId);
    }

    @Override
    public void getPaymentMethodList(int price) {
//        baseView.showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getPaymentMethod(price, getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<PaymentMethodModel>>() {
                }.getType();
                paymentMethodModels = getGson().fromJson(jsonArray, dataType);
                view.doShowPaymentMethod(paymentMethodModels);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getAllPackageByTrainerId() {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getAllPackageClassByTrainerId(trainerId, getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<PackageClassModel>>() {
                }.getType();
                packageClassModels = getGson().fromJson(jsonArray, dataType);
                view.showTrainerClassList(packageClassModels);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getAllPackageByClassId(String classId) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getAllPackageByClassId(trainerId, classId, getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<PackagePriceModel>>() {
                }.getType();
                packagePriceModels = getGson().fromJson(jsonArray, dataType);
                view.addIntoPriceList(packagePriceModels);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void onPackageClassSelected(PackageClassModel model) {
        this.selectedPackageClass = model;
        getAllPackageByClassId(model.getClassId());
    }

    @Override
    public void onPackagePriceSelected(PackagePriceModel model) {
        this.selectedPackagePrice = model;

        int packagePrice = model.getTotal();

        view.showPackageOriginalPrice(currencyFormatter.getRupiahString(packagePrice));
        view.setDiscountAndExpiry(model.getSessions(), model.getExpired());
        getPaymentMethodList(packagePrice);
    }

    @Override
    public void onPaymentMethodSelected(PaymentMethodModel model) {
        this.selectedPaymentMethod = model;

        double serviceFee = Double.parseDouble(model.getServiceFee());
        if(serviceFee <= 0 ){
            view.hideServiceFee();
        } else {
            view.showServiceFee(currencyFormatter.getRupiahString((int) serviceFee));
        }

        int finalPrice = (int) (selectedPackagePrice.getTotal() + serviceFee);
        view.showFinalPrice(currencyFormatter.getRupiahString(finalPrice));
    }

    @Override
    public void onCreateBookingConfirmed() {
        String packageId = selectedPackagePrice.getPackageId();
        int paymentMethodId = selectedPaymentMethod.getId();

        setUpBookingForMidtrans(packageId, paymentMethodId);
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
        userAddress.setAddress(requestBooking.getAddress());
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
