package com.nostratech.mufit.consumer.history_detail;

import com.nostratech.mufit.consumer.cancel_booking.CancelBookingContract;
import com.nostratech.mufit.consumer.model.BookingSpecialityModel;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/28/2018.
 */

public interface HistoryDetailContract {

    interface View extends CancelBookingContract.View {
        void showTrainerInfo(String imageUrl, String coverUrl, String trainerName);
        void showTrainingDetail(String address, String trainingDate, String trainingTime);
        void showBookingStatus(String statusText);
        void showPaymentDetail(String voucherCode, String discountValue, String serviceFee, String totalPrice);
        void showCancelBookingButton();
        void hideCancelBookingButton();
        void showRatingAndReview(String rating, String review);
        void showPurchasedSpecialities(List<BookingSpecialityModel> list);

//        void uploadImageSuccess(String url);
//        void uploadPaymentSuccess();
    }

    interface HistoryDetailPresenter extends CancelBookingContract.Presenter {
        void getHistoryDetailBooking(String id);
//        void uploadImageFromDetail(ApiService apiService, MultipartBody.Part uploadImage, String type);
//        void uploadPaymentFromDetail(ApiService apiService, String token, String id,
//                                     String paymentPhoto, Integer version, String paymentDesc);
    }
}
