package com.nostratech.mufit.consumer.service;

/**
 * Created by Ahmadan Ditiananda on 5/9/2018.
 */

/*
 * This class contains the URL for API endpoints used by MUFIT Consumer App
 * For list of all available endpoints, please refer to Swagger: https://dev-api.mufit.id/swagger-ui.html#/
 */

class UrlServices {

    static class PathVariables{
        static final String TRAINER_ID = "trainerId";
        static final String CLASS_ID = "classId";
        static final String EMAIL = "email";
        static final String BOOKING_ID = "bookingId";
        static final String PACKAGE_ID = "packageID";
    }

    static class Auth {
        static final String LOGIN = "api/auth/login";
        static final String LOGOUT= "api/auth/logout";
    }

    static class Booking {
        static final String CANCEL_BOOKING = "api/booking/cancel-booking";
        static final String CREATE_BOOKING = "api/booking/create-booking";
        static final String CREATE_BOOKING_EVENT = "api/booking/create-booking-event";
        static final String GET_DATE_SCHEDULE = "api/booking/get-schedule-list-booking";
        static final String GET_DETAIL_SCHEDULE = "api/booking/get-schedule-detail-booking";
        static final String UPLOAD_PAYMENT = "api/booking/upload-payment-photo";
        static final String HISTORY_BOOKING = "api/booking/get-list-booking-trainer";
        static final String HISTORY_BOOKING_DETAIL = "api/booking/get-detail-booking";
        static final String GET_TRAINER_SCHEDULE ="api/booking/get-list-available-date-by-trainer";
        static final String CANCEL_TRANSACTION = "api/booking/cancel-booking-unprocessed/{"+ PathVariables.BOOKING_ID +"}";
    }

    static class Event {
        static final String LIST_RUNNING_EVENT = "api/event/get-list-running-event";
        static final String GET_ALL_ACTIVE_EVENTS = "api/event/get-list-event-active/";
    }

    static class ForgotPassword {
        static final String FORGOT_PASSWORD = "api/forgot-password/send-email";
    }

    static class Image {
        static final String UPLOAD_IMAGE = "api/image/upload";
        static final String UPLOAD_PROFILE_IMAGE = "api/image/upload?type=CUSTOMER";
    }

    static class News {
        static final String GET_ACTIVE_NEWS = "api/news";
        static final String GET_LAST_NEWS = "/api/public/news/latest";
    }

    static class Public {
        static final String GET_LIST_ACTIVE_TRAINERS = "api/public/trainer/get-list-sorted";
        static final String GET_LIST_TRAINER_WITH_FILTERS = "api/public/trainer/get-advanced-list";
        static final String GET_DETAIL_SHIFT_TRAINER = "api/public/trainer/get-detail-shift";
        static final String GET_CATEGORY = "api/public/speciality/list-of-category";
        static final String LIST_SPECIALITY = "api/public/speciality/list-of-speciality";
        static final String GET_DETAIL_EVENT = "api/public/event/get-detail-event";
        static final String GET_DETAIL_VOUCHER = "api/public/voucher/get-detail-voucher";
        static final String GET_DETAIL_ADVERTISEMENT = "api/public/media/get-detail-media";
    }

    static class Package {
        static final String GET_ALL_PACKAGE = "api/package/list";
        static final String GET_ALL_PACKAGE_BY_TRAINER_ID = "api/package/by-trainer/{" + PathVariables.TRAINER_ID + "}";
        static final String GET_ALL_PACKAGE_CLASS_BY_TRAINER_ID = "api/package/class/{"+ PathVariables.TRAINER_ID +"}/";
        static final String GET_ALL_PACKAGE_BY_CLASS_ID = "api/package/{"+ PathVariables.TRAINER_ID +"}/{"+ PathVariables.CLASS_ID +"}/";
        static final String CREATE_BOOKING_PACKAGE = "api/package/booking/{"+ PathVariables.PACKAGE_ID +"}";
    }

    static class Profiles {
        static final String UPDATE_PROFILE = "api/profiles/update-customer";
        static final String GET_PROFILE = "api/profiles/get-detail-customer";
        static final String REGISTER = "api/profiles/registration-customer";
        static final String CHANGE_PASSWORD = "/api/profiles/user/change-password";
    }

    static class PaymentMethod {
//        static final String PAYMENT_METHOD = "api/payment-method/findAll";
        static final String CALCULATE_SERVICE_FEES = "api/payment-method/calculate";
    }

    static class Rating {
        static final String ADD_RATING = "api/rating/add-rating";
    }

    static class Registration {
        static final String CHECK_EMAIL = "api/registration/user/{"+ PathVariables.EMAIL +"}/";
    }

    static class Trainer {
        static final String GET_DETAIL = "api/trainer/get-detail";
        static final String GET_AVAILABLE_SCHEDULE = "api/trainer/available-schedule/{" + PathVariables.TRAINER_ID + "}";
    }

    static class Voucher {
        static final String GET_ACTIVE_VOUCHER = "api/voucher/get-list-voucher-active";
        static final String CHECK_VOUCHER = "api/voucher/check-voucher";
        static final String GET_DETAIL_MY_VOUCHER = "api/voucher/package/detail";
        static final String GET_TOTAL_MY_VOUCHER = "api/voucher/package/";
    }

    static class TestApi {
        static final String TESTAPIYA = "todos/1";
    }

}
