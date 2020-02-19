package com.nostratech.mufit.consumer.my_voucher;

import com.nostratech.mufit.consumer.model.CheckVoucherResponseModel;
import com.nostratech.mufit.consumer.model.MyVoucherModel;

import java.util.List;

public interface MyVoucherContract {
    interface View {
        void showValidVoucher(CheckVoucherResponseModel model);
        void showVouchers(List<MyVoucherModel> myVoucherModel);
    }

    interface Presenter {
        void checkVoucher(String voucher);
        void getMyVoucher(int page, int limit);
    }
}
