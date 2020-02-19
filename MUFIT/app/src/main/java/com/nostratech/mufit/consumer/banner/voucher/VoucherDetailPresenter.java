package com.nostratech.mufit.consumer.banner.voucher;

import android.content.Context;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.banner_event_detail.voucher_detail.VoucherDetailModel;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;
import com.nostratech.mufit.consumer.utils.date.DateUtils;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class VoucherDetailPresenter extends MyBasePresenter implements VoucherDetailContract.Presenter {

    private VoucherDetailContract.View view;

    private VoucherDetailModel voucherModel;

    public VoucherDetailPresenter(Context context, MvpView mvpView, VoucherDetailContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    //TODO: Differentiate %age and flat discount
    @Override
    public void loadVoucherDetail(String id) {
        getMvpView().showLoading();
        if(isConnectedToInternet()){
            Call<StandardResponseModel> call = getApiService().getDetailVoucher(getAccessToken(), id);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                voucherModel = getGson().fromJson(jsonObject.toString(),
                        VoucherDetailModel.class);

                //Get Date & Time
                String startDate = DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, voucherModel.getStartDate());
                String endDate = DateUtils.parseLong(DateUtils.Format.dd_MMMM_yyyy, voucherModel.getEndDate());
                String dateTime = startDate + " - " + endDate;

                CurrencyFormatter currencyFormatter = new CurrencyFormatter();

                view.showVoucherDetail(voucherModel.getDescription(),
                        dateTime,
                        voucherModel.getCode(),
                        "Rp. " + currencyFormatter.getRupiahString(voucherModel.getValue()));

                getMvpView().dismissLoading();
            }));
        }
        else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void onCopyVoucherClick() {
        view.copyVoucherCode(voucherModel.getCode());
    }

    @Override
    public void onBtnBookClick() {
        view.navigateToSearchTrainer(voucherModel.getCode(), voucherModel.getValue(), voucherModel.getType());
    }
}
