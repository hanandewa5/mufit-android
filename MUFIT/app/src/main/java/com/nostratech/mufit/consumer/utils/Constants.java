package com.nostratech.mufit.consumer.utils;

import java.util.Locale;

public class Constants {

    public static class Advertisement {
        public static final String ID = "advertisementId";
        public static final String IMAGE = "advertisementImage";
    }

    public static class B2B {
        public static final String KEY_USER_ID = "b2bUserId";
    }

    public static class HealthComponent {
        public static final String ID = "healthComponentId";
    }

    public static class Event {
        public static final String ID = "eventId";
        public static final String IMAGE = "eventImage";
    }

    public static class EventType {
        public static final String EVENT = "event";
        public static final String ADVERTISEMENT = "advertisement";
        public static final String VOUCHER = "voucher";
    }

    public static class Voucher {
        public static final String ID = "voucherId";
        public static final String IMAGE = "voucherImage";
        public static final String INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE = "voucherDiscountType";
        public static final String DISCOUNT_NOMINAL = "nominal";
        public static final String DISCOUNT_PERCENT = "percent";
    }

    public static class HttpResponse {
        public static final int OK = 200;
        public static final int UNAUTHORIZED = 401;
        public static final int PAGE_NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int SERVICE_NOT_AVAILABLE = 503;
    }

    /**
     * Represents the fields available in Firebase Messaging "Data" payload
     * Not to be confused with Firebase Messaging "Notification" payload
     */
    public static class FirebaseNotificationDataPayload{
        public static final String BOOKING_ID = "booking_id";
        public static final String TITLE = "title";
        public static final String BODY = "body";
        public static final String ACTION = "action";
    }

    public static class NotificationAction{
        public static final String OPEN_HOME= "OPEN_HOME";
        public static final String OPEN_NEWS = "OPEN_NEWS";
        public static final String OPEN_BOOKING = "OPEN_BOOKING";
        public static final String CHAT_ACTIVATED = "CHAT_ACTIVATED";
    }

    public static class RootNavigation {
        public static final String EXTRA_NAVIGATE_TO = "navigateTo";
    }


    public static final String TAG_OK = "OK";
    public static final String TAG_ERROR = "ERROR";
    public static final Locale INDONESIA = new Locale("in", "ID");

    public static class IntentExtras{
        public static final String TRAINER_ID = "trainerId";
        public static final String VOUCHER_CODE = "voucherCode";
        public static final String VOUCHER_DISCOUNT_VALUE = "discountValue";
        public static final String TRAINER_NAME = "trainerName";
        public static final String VOUCHER_TYPE = "voucherType";
        public static final String VOUCHER_MODEL = "voucherModel";
        public static final String TRAINER_SPECIALITY_ID = "trainerSpecialityId";
        public static final String VOUCHER_ACTIVITY = "HomeActivity";
        public static final String SPECIALITY_ID = "specialityId";
    }

    public static final String INTENT_SUB_CATEGORIES_SPECIALITY = "subCategoriesspeciality";
    public static final String INTENT_SUB_CATEGORIES_GENDER = "subCategoriesgender";
    public static final String INTENT_SUB_CATEGORIES_DATE = "subCategoriesdate";

    public static final int RESULT_VOUCHER_CANCELLED = 1000;

    public static final int ONE_DAY_IN_MILLIS = 86400000;
}
