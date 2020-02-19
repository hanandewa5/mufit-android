//package com.nostratech.mufit.consumer.legacy;
//
//import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingSpecialityRequestModel;
//import com.nostratech.mufit.consumer.model.booking.CreateBookingRequestModel;
//import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
//
//import java.util.List;
//
//public interface BookingContract2 {
//    interface View {
//        void initRequestBooking();
////        void notValidVoucher();
////        void validVoucher(Integer price);
//        void showConfirmationDialog();
//        void successBooking(String booking_id);
////        void emptyVoucher();
//        void doShowPaymentMethod(List<PaymentMethodModel> paymentMethodModels);
//        void successCancelBookingUnprocessed();
//    }
//
//    interface Presenter {
////        void checkVoucher(String voucher);
////        void checkVoucherFromMyVoucher(String voucher, String trainerSpecialityID); //todo: fristy check voucher from my vouhcer
////        void observeVoucher(EditText textVoucher);
////        void observeVoucherFromMyVoucher(EditText textVoucher, String trainerSpecialityID); //todo: fristy check voucher from my vouhcer
//        void createBooking(CreateBookingRequestModel createBookingRequestModel);
//        void getPaymentMethodList(int price);
//        void createEventBooking(String customerName, String customerPhone,
//                                String event, List<EventBookingSpecialityRequestModel> specialityList,
//                                String trainer);
//        void cancelBookingUnprocessed();
//    }
//}
