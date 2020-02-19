package com.nostratech.mufit.consumer.booking_package;

import android.os.Bundle;

import com.nostratech.mufit.consumer.model.PackageClassModel;
import com.nostratech.mufit.consumer.model.PackagePriceModel;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;

import java.util.List;

public interface BookingPackageContract {
    interface View {

        void doShowPaymentMethod(List<PaymentMethodModel> paymentMethodModels);

        void showConfirmationDialog();

        void showTrainerClassList(List<PackageClassModel> packageClassModel);

        void addIntoPriceList(List<PackagePriceModel> packagePriceModel);

        void openBankTransferPaymentScreen();

        void openCreditCardPaymentScreen();

        void openGoPayTransferPaymentScreen();

        void showTrainerName(String trainerName);

        void showPackageOriginalPrice(String originalPrice);

        void setDiscountAndExpiry(int sessions, int expiryDays);

        void showFinalPrice(String finalPrice);

        void showServiceFee(String serviceFee);

        void hideServiceFee();
    }

    interface Presenter {
        void initializeData(Bundle bundle);

        void getPaymentMethodList(int price);

        void getAllPackageByTrainerId();

        void getAllPackageByClassId(String classId);

        void onPaymentMethodSelected(PaymentMethodModel model);

        void onPackageClassSelected(PackageClassModel model);

        void onPackagePriceSelected(PackagePriceModel model);

        void onCreateBookingConfirmed();
    }
}
