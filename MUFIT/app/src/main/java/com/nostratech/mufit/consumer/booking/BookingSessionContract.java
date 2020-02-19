package com.nostratech.mufit.consumer.booking;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;

import java.util.List;

class BookingSessionContract {

    interface View {
        void showBookingEventUI();

        void setTrainerDetails(String trainerName,
                               String specialityName,
                               String trainingDate,
                               String trainingTime);

        void setOriginalPrice(String originalPrice);

        void setFinalPriceAndServiceFee(String finalPrice,
                                        String serviceFee);

        void updateMap(String address,
                       double lat,
                       double lng);

        void onVoucherUsed(String discountValue, String voucherCode);

        void onVoucherRemoved();

        void showPaymentMethods(List<PaymentMethodModel> paymentMethodModels);

        void hidePaymentMethodsAndServiceFee();

        void onBookingSuccessFreeOfCharge();

        void openBankTransferPaymentScreen();

        void openCreditCardPaymentScreen();

        void openVoucherSelectionScreen(String trainerSpecialityId);

        void openGoPayTransferPaymentScreen();

        void successCancelBookingUnprocessed();

        void onAddressNotSet();

        void showConfirmationDialogEvent();

        void showConfirmationDialogSession();

        void setGoogleMap(GoogleMap googleMap);

        void showUserCurrentLocation();

        void navigateToHistory();
    }

    interface Presenter {
        void initializeData(Bundle bundle);

        void setTrainingLocation(String address,
                                 double lat,
                                 double lng);

        void onPaymentMethodSelected(PaymentMethodModel model);

        void updateVoucherCode(String voucherCode, String discountType, int discountValue);

        void postBookingEvent();

        void postBookingSession(String notes);

        void getPaymentMethodList(int price);

        void cancelBookingUnprocessed();

        void onBookingClick();

        void onChooseVoucherClick();
    }

}
