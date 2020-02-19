package com.nostratech.mufit.consumer.service;

import com.nostratech.mufit.consumer.model.CancelBookingRequestModel;
import com.nostratech.mufit.consumer.model.ChangePasswordRequestModel;
import com.nostratech.mufit.consumer.model.ForgotPasswordRequestModel;
import com.nostratech.mufit.consumer.model.LoginRequestModel;
import com.nostratech.mufit.consumer.model.LogoutRequestModel;
import com.nostratech.mufit.consumer.model.ProfileUpdateRequestModel;
import com.nostratech.mufit.consumer.model.RateReviewRequestModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingRequestModel;
import com.nostratech.mufit.consumer.model.booking.CreateBookingRequestModel;
import com.nostratech.mufit.consumer.model.register.RegisterRequestModel;
import id.mufit.core.network.models.PaginatedResponseModel;

import id.mufit.core.network.models.StandardResponseModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST(UrlServices.Auth.LOGIN)
    Call<StandardResponseModel> login(@Body LoginRequestModel requestLogin);

    @POST(UrlServices.Auth.LOGOUT)
    Call<StandardResponseModel> logout(@Body LogoutRequestModel requestLogout);

    @GET(UrlServices.Registration.CHECK_EMAIL)
    Call<StandardResponseModel> checkEmail(@Path(UrlServices.PathVariables.EMAIL) String email);

    @POST(UrlServices.Profiles.REGISTER)
    Call<StandardResponseModel> register(@Body RegisterRequestModel requestRegister);

    @GET(UrlServices.Profiles.GET_PROFILE)
    Call<StandardResponseModel> getProfileDetail(@Query("access_token") String token);

    @PUT(UrlServices.Profiles.UPDATE_PROFILE)
    Call<StandardResponseModel> updateProfile(@Query("access_token") String token,
                                          @Body ProfileUpdateRequestModel profileUpdateRequestModel);


    @POST(UrlServices.ForgotPassword.FORGOT_PASSWORD)
    Call<StandardResponseModel> sendEmail(@Body ForgotPasswordRequestModel forgotPasswordRequestModel);


    @GET(UrlServices.Trainer.GET_DETAIL)
    Call<StandardResponseModel> getDetailTrainer(@Query("access_token") String token,
                                             @Query("id") String idTrainer);

    @GET(UrlServices.Event.LIST_RUNNING_EVENT)
    Call<StandardResponseModel> getListRunningEvent(@Query("access_token") String token);

    @GET(UrlServices.Event.GET_ALL_ACTIVE_EVENTS)
    Call<PaginatedResponseModel>getEvent(@Query("access_token")String token,
                                         @Query("page") int page,
                                         @Query("limit") int limit);


    @GET(UrlServices.Public.GET_DETAIL_SHIFT_TRAINER)
    Call<StandardResponseModel> getDetailShiftTrainer(@Query("access_token") String token,
                                                  @Query("id") String id,
                                                  @Query("day") String day,
                                                  @Query("book_date") String bookDate);

    @GET(UrlServices.Public.GET_LIST_TRAINER_WITH_FILTERS)
    Call<PaginatedResponseModel> getListTrainerWithFilter(@Query("access_token") String token,
                                                          @Query("name") String name,
                                                          @Query("speciality") String speciality,
                                                          @Query("gender") String gender,
                                                          @Query("day") String day,
                                                          @Query("time_start") String startTime,
                                                          @Query("time_end") String endTime,
                                                          @Query("book_date") String bookDate,
                                                          @Query("page") int currentPage,
                                                          @Query("limit") int limit) ;


    @GET(UrlServices.Public.GET_DETAIL_VOUCHER)
    Call<StandardResponseModel> getDetailVoucher(@Query("access_token") String token,
                                             @Query("id") String id);

    @GET(UrlServices.Public.GET_DETAIL_ADVERTISEMENT)
    Call<StandardResponseModel> getDetailAdvertisement(@Query("access_token") String token,
                                                   @Query("id") String id);

    @GET(UrlServices.Public.GET_LIST_ACTIVE_TRAINERS)
    Call<StandardResponseModel> getListTrainerForHome(@Query("access_token") String token,
                                                  @Query("page") int page,
                                                  @Query("limit") int limit);

    @GET(UrlServices.Public.LIST_SPECIALITY)
    Call<StandardResponseModel> getListSpecialityForHome(@Query("access_token") String token);

    @GET(UrlServices.Public.GET_DETAIL_EVENT)
    Call<StandardResponseModel> getDetailEvent(@Query("access_token") String token, @Query("id") String id);

    @GET(UrlServices.Public.GET_CATEGORY)
    Call<StandardResponseModel>getCategory();

    @GET(UrlServices.Voucher.CHECK_VOUCHER)
    Call<StandardResponseModel> checkVoucher(@Query("access_token") String token,
                                         @Query("code") String voucherCode);

    @GET(UrlServices.Booking.HISTORY_BOOKING)
    Call<PaginatedResponseModel> getHistoryBooking(@Query("access_token") String token,
                                                   @Query("status") String status,
                                                   @Query("page") int counterPage,
                                                   @Query("limit") int limit);

    @POST(UrlServices.Booking.CREATE_BOOKING)
    Call<StandardResponseModel> createBooking(@Query("access_token") String token,
                                          @Body CreateBookingRequestModel createBookingRequestModel);

    @POST(UrlServices.Booking.CANCEL_BOOKING)
    Call<StandardResponseModel> cancelBooking(@Query("access_token") String token,
                                          @Body CancelBookingRequestModel cancelBookingRequestModel);

    @POST(UrlServices.Booking.CANCEL_TRANSACTION)
    Call<StandardResponseModel>setCancelTransaction(@Path("bookingId") String bookingId,
                                                @Query("access_token") String token);

    @GET(UrlServices.Booking.HISTORY_BOOKING_DETAIL)
    Call<StandardResponseModel> getHistoryBookingDetail(@Query("access_token") String token,
                                                    @Query("id") String id);

    @POST(UrlServices.Booking.CREATE_BOOKING_EVENT)
    Call<StandardResponseModel> createEventBooking(@Query("access_token") String token,
                                               @Body EventBookingRequestModel eventBookingRequestModel);

    @GET(UrlServices.Booking.GET_DATE_SCHEDULE)
    Call<StandardResponseModel> getDateSchedule(@Query("access_token") String token);

    @GET(UrlServices.Booking.GET_DETAIL_SCHEDULE)
    Call<StandardResponseModel> getDetailSchedule(@Query("access_token") String token,
                                              @Query("book_date") long bookDate);

//    @POST(UrlServices.Booking.UPLOAD_PAYMENT)
//    Call<StandardResponseModel> uploadPayment(@Query("access_token") String token, @Body UploadPaymentRequestModel uploadPaymentRequestModel);

    //    @GET(UrlServices.Booking.GET_TRAINER_SCHEDULE)
//    Call<StandardResponseModel> getScheduleTrainer(@Query("id") String id, @Query("access_token") String token);

    @Headers("Content-Type: application/json")
    @POST(UrlServices.Rating.ADD_RATING)
    Call<StandardResponseModel> rateReview(@Query("access_token") String token,
                                       @Body RateReviewRequestModel rateReviewRequestModel);


//    @Multipart
//    @POST(UrlServices.UPLOAD_IMAGE)
//    Call<StandardResponseModel> uploadImage(@Part MultipartBody.Part file, @Query("type") String type);



//    @Headers("Content-Type: application/json")
//    @GET(UrlServices.PaymentMethod.PAYMENT_METHOD)
//    Call<StandardResponseModel> getPaymentMethods(@Query("access_token") String token);

    @GET(UrlServices.Package.GET_ALL_PACKAGE)
    Call<StandardResponseModel> getAllPackage(@Query("access_token") String token);

    @GET(UrlServices.Package.GET_ALL_PACKAGE_BY_TRAINER_ID)
    Call<StandardResponseModel> getAllPackageByTrainerId(@Path(UrlServices.PathVariables.TRAINER_ID) String trainerId,
                                                     @Query("access_token") String token);

    @GET(UrlServices.Package.GET_ALL_PACKAGE_CLASS_BY_TRAINER_ID)
    Call<StandardResponseModel> getAllPackageClassByTrainerId(@Path(UrlServices.PathVariables.TRAINER_ID) String trainerId,
                                                          @Query("access_token") String token);

    @GET(UrlServices.Package.GET_ALL_PACKAGE_BY_CLASS_ID)
    Call<StandardResponseModel> getAllPackageByClassId(@Path(UrlServices.PathVariables.TRAINER_ID) String trainerId,
                                                   @Path(UrlServices.PathVariables.CLASS_ID) String classId,
                                                   @Query("access_token") String token);

    @GET(UrlServices.Voucher.GET_DETAIL_MY_VOUCHER)
    Call<StandardResponseModel> getDetailMyVoucher(@Query("access_token") String token,
                                               @Query("page") int page,
                                               @Query("limit") int limit);

    @GET(UrlServices.Voucher.GET_TOTAL_MY_VOUCHER)
    Call<StandardResponseModel> getTotalMyVoucher(@Query("access_token") String token);

//    @POST(UrlServices.Package.CREATE_BOOKING_PACKAGE)
//    Call<StandardResponseModel> createBookingPackage(@Path(UrlServices.PathVariables.PACKAGE_ID) String packageID, @Query("access_token") String token);
//
//    @GET(UrlServices.Voucher.CHECK_VOUCHER)
//    Call<StandardResponseModel> checkVoucherFromMyVoucher(@Query("access_token") String token, @Query("code") String voucherCode, @Query("trainer_speciality_id") String trainer_speciality_id);

    @GET(UrlServices.Trainer.GET_AVAILABLE_SCHEDULE)
    Call<StandardResponseModel> getTrainerAvailableSchedule(@Path(UrlServices.PathVariables.TRAINER_ID) String trainerId,
                                                        @Query("start_date") long startDate,
                                                        @Query("end_date") long endDate,
                                                        @Query("access_token") String token);

    @Multipart
    @POST(UrlServices.Image.UPLOAD_PROFILE_IMAGE)
    Call<StandardResponseModel> uploadProfileImageCustomer(@Part MultipartBody.Part file,
                                                       @Query("access_token") String token);

    @GET(UrlServices.Voucher.GET_ACTIVE_VOUCHER)
    Call<PaginatedResponseModel> getActiveVouchers(@Query("access_token") String token,
                                                   @Query("page") int page,
                                                   @Query("limit") int limit,
                                                   @Query("usage_type") String usageType);

    @GET(UrlServices.News.GET_ACTIVE_NEWS)
    Call<PaginatedResponseModel> getActiveNews(@Query("access_token") String token,
                                               @Query("page") int page,
                                               @Query("limit") int limit);

    @GET(UrlServices.PaymentMethod.CALCULATE_SERVICE_FEES)
    Call<StandardResponseModel>getPaymentMethod(@Query("price") int price,
                                            @Query("access_token") String token);

    @GET(UrlServices.News.GET_LAST_NEWS)
    Call<PaginatedResponseModel>getLastNews();

    @POST(UrlServices.Profiles.CHANGE_PASSWORD)
    Call<StandardResponseModel> changePassword(@Query("access_token") String token,
                                               @Body ChangePasswordRequestModel model);

}
