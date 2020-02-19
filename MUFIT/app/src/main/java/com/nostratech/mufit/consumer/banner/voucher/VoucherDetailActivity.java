package com.nostratech.mufit.consumer.banner.voucher;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoucherDetailActivity extends MyToolbarBackActivity implements VoucherDetailContract.View {

    @BindView(R.id.voucherDetail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.voucherDetail_image)
    RoundedImageView imageVoucher;

    @BindView(R.id.voucherDetail_textVoucherDescription)
    TextView textDesc;

    @BindView(R.id.voucherDetail_textDateTime)
    TextView textDateTime;

    @BindView(R.id.voucherDetail_textVoucherCode)
    TextView textCode;

    @BindView(R.id.voucherDetail_textDiscount)
    TextView textDiscount;

    @BindView(R.id.voucherDetail_btnCopyClipboard)
    Button btnCopyClipboard;

    @BindView(R.id.voucherDetail_btnBook)
    Button btnBook;

    private VoucherDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_detail);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        String voucherId = getIntent().getStringExtra(Constants.Voucher.ID);

        String image = getIntent().getStringExtra(Constants.Voucher.IMAGE);

        GlideApp.with(this)
                .load(image)
                .placeholder(R.drawable.image_preview_bg)
                .into(imageVoucher);

        presenter = new VoucherDetailPresenter(this, this, this);
        presenter.loadVoucherDetail(voucherId);
    }

    @OnClick({R.id.voucherDetail_btnCopyClipboard, R.id.voucherDetail_btnBook})
    public void onClick(View v){
        switch(v.getId()){
            case R.id.voucherDetail_btnCopyClipboard:
                presenter.onCopyVoucherClick();
                break;
            case R.id.voucherDetail_btnBook:
                presenter.onBtnBookClick();
                break;
        }

    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showVoucherDetail(String desc, String dateTime, String voucherCode, String discountStr) {
        textDesc.setText(desc);
        textDateTime.setText(dateTime);
        textCode.setText(voucherCode);
        textDiscount.setText(discountStr);
    }

    @Override
    public void copyVoucherCode(String voucherCode) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Voucher Code", voucherCode);
        clipboard.setPrimaryClip(clip);
        showToastMessageLong(getString(R.string.voucher_code_copied));
    }

    @Override
    public void navigateToSearchTrainer(String voucherCode, int discountValue, String discountType) {
        Intent i = new Intent(this, SearchTrainerActivity.class);
        i.putExtra(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
        i.putExtra(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, discountValue);
        i.putExtra(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, discountType);
        showActivity(i);
    }
}
