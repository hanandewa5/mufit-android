package com.nostratech.mufit.consumer.banner.voucher;

class VoucherDetailContract {

    public interface View {

        void showVoucherDetail(String desc, String dateTime, String voucherCode, String discountStr);

        void copyVoucherCode(String voucherCode);

        void navigateToSearchTrainer(String voucherCode, int discountValue, String discountType);
    }

    public interface Presenter {

        void loadVoucherDetail(String id);

        void onCopyVoucherClick();

        void onBtnBookClick();

    }

}
