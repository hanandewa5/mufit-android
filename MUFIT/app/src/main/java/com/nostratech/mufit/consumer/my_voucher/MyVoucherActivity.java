package com.nostratech.mufit.consumer.my_voucher;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.booking.BookingSessionActivity;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.model.CheckVoucherResponseModel;
import com.nostratech.mufit.consumer.model.MyVoucherModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.EndlessScrollListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyVoucherActivity extends MyToolbarBackActivity implements MyVoucherContract.View {

    /**
     * VOUCHER_CODE = pake kode yg diketik
     * VOUCHER_PACKAGE = pake kupon dari beli paket (bagian bawah)
     */
    public enum VoucherType {
        VOUCHER_CODE,
        VOUCHER_PACKAGE
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefresh_my_voucher)
    SwipeRefreshLayout swipeRefreshMyVoucher;
    @BindView(R.id.rvListVoucher)
    RecyclerView rvListVoucher;
    @BindView(R.id.submit_voucher_edit_text)
    EditText etVoucherCode;
    @BindView(R.id.button_clear_voucher_code)
    ImageButton buttonClearCodeVoucher;
    @BindView(R.id.button_submit_voucher_code)
    Button buttonSubmitCodeVoucher;
    @BindView(R.id.layout_myVoucher)
    RelativeLayout layoutMyVoucher;
    @BindView(R.id.btn_UseVoucher)
    Button btnUseVoucher;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.text_Empty)
    TextView textEmpty;
    @BindView(R.id.layout_recycler)
    LinearLayout root;

    private EndlessScrollListener endlessScrollListener;
    private MyVoucherAdapter adapter;
    MyVoucherContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new MyVoucherPresenter(this, this, this);
        presenter.getMyVoucher(0, 100);

        configureRvAdapter();

        //swipe to refresh data

        swipeRefreshMyVoucher.setColorSchemeResources(R.color.orange);
        swipeRefreshMyVoucher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshMyVoucher.setRefreshing(false);
                adapter.clearList();
                endlessScrollListener.resetState();
                presenter.getMyVoucher(0, 100);
            }
        });


        setupEtVoucherCode();
        setupStealFocusTouchListeners(root, layoutMyVoucher);
    }

    private void setupEtVoucherCode() {
        etVoucherCode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etVoucherCode.addTextChangedListener(new VoucherCodeInputTextWatcher());
    }

    private void configureRvAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyVoucherActivity.this);
        rvListVoucher.setLayoutManager(layoutManager);

        adapter = new MyVoucherAdapter();
        rvListVoucher.setAdapter(adapter);

        //TODO: Proper Infinite Scroll
        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //presenter.getMyVoucher(apiService, getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN),0,100);
            }
        };
        rvListVoucher.addOnScrollListener(endlessScrollListener);



        //TODO: Receive voucher selected from Booking Session. Below is legacy code

//        Intent intent = getIntent();
//        String previousActivity = intent.getStringExtra("previousActivity");
//        if (previousActivity != null && previousActivity.equals("BookingActivity2")) {
//            Bundle data = getIntent().getExtras();
//            String voucherID = data.getString("voucherID");
//            adapter = new MyVoucherAdapter();
//        } else {
//            adapter = new RecyclerViewAdvancedAdapter_my_voucher(MyVoucherActivity.this,
//                    getSessionManager());
//        }


    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        runOnUiThread(() -> dismissProgressDialog());
    }

    @Override
    public void showNoInternetError() {
        //TODO: No internet layout
        showGenericError(getString(R.string.error));
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    //Called when submitted voucher code is correct
    //If MyVoucherActivity is called by BookingActivity2, return the voucher code and discount value as Intent extras as ActivityResult
    //Else if called by HomeActivity, open ListTrainer activity and pass voucher code and discount value as Intent extras
    @Override
    public void showValidVoucher(CheckVoucherResponseModel model) {

        //TODO: Remove unnecessary code
        //TODO: Think of an elegant way to pass voucher around. Maybe persist locally?
        dismissLoading();

        String voucherCode = etVoucherCode.getText().toString();

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String previousActivity = data.getString("previousActivity", Constants.IntentExtras.VOUCHER_ACTIVITY);

        //Null if MyVoucherActivity is not started with startActivityForResult
        if (getCallingActivity() != null) {
            String callingClass = getCallingActivity().getClassName();

            //If called from BookingActivity2
            if (callingClass.equals(BookingSessionActivity.class.getName())) {

                //Pass the voucher code and discount value back to BookingActivity2
                Intent i = new Intent();

                i.putExtra(Constants.IntentExtras.VOUCHER_TYPE, VoucherType.VOUCHER_CODE);
                i.putExtra(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
                i.putExtra(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, model.getValue());
                i.putExtra(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, model.getType());
                setResult(RESULT_OK, i);
                finish();
                return;
            }
        }

        //legacy code
        if (previousActivity == null) {
            return;
        }

        //Legacy code
        //Comes from home
        if (previousActivity.equals("HomeActivity")) {
            Intent intent2 = new Intent(this, SearchTrainerActivity.class);
            intent2.putExtra(Constants.IntentExtras.VOUCHER_TYPE, VoucherType.VOUCHER_CODE);
            intent2.putExtra(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
            intent2.putExtra(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, model.getValue());
            intent2.putExtra(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, model.getType());
            intent2.putExtra("isFromHome", false);
            intent2.putExtra("isFromMyVoucher", true);
            showActivity(intent2);
        } else {
            showToastMessageLong("previousActivity undefined " + previousActivity);
        }
    }

    @Override
    public void showGenericError(String errorMessage) {
        super.showGenericError(errorMessage);
    }

    @Override
    public void showVouchers(List<MyVoucherModel> models) {
        dismissLoading();
        if (models.size() > 0) {
            layoutEmpty.setVisibility(View.GONE);
            rvListVoucher.setVisibility(View.VISIBLE);
            btnUseVoucher.setVisibility(View.VISIBLE);
            adapter.clearList();
            adapter.insertItem(models);
        } else {
            rvListVoucher.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
            btnUseVoucher.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.button_submit_voucher_code, R.id.btn_UseVoucher, R.id.button_clear_voucher_code})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.button_submit_voucher_code:
                dismissKeyboard();
                presenter.checkVoucher(etVoucherCode.getText().toString());
                break;

            case R.id.btn_UseVoucher:
                int index = adapter.getSelectedItemIndex();

                if (index == RecyclerView.NO_POSITION) {
                    showGenericError(getString(R.string.please_select_voucher_to_use));
                    return;
                }

                //Get chosen voucher
                MyVoucherModel model = this.adapter.getItem(index);

                if (getCallingActivity() != null) {
                    String callingClass = getCallingActivity().getClassName();

                    //If calling class was BookingSessionActivity
                    if (callingClass.equals(BookingSessionActivity.class.getName())) {

                        //Get trainerSpecialityId, which should be supplied by BookingSessionActivity
                        Bundle data = getIntent().getExtras();
                        String trainerSpecialityId = data.getString(Constants.IntentExtras.TRAINER_SPECIALITY_ID, null);

                        //Require trainerSpecialityId to be not null
                        Objects.requireNonNull(trainerSpecialityId, "TrainerSpecialityId is null. Please check BookingSessionActivity");

                        //Check if voucher speciality matches the speciality chosen in BookingSessionActivity


                        if (!trainerSpecialityId.equals(model.getTrainerSpecialityId())) {
                            showGenericError(getString(R.string.voucher_choosen_not_match));
                            return;
                        }

                        //Return the VoucherModel to BookingActivity2 if chosen voucher matches the trainer's speciality Id
                        Intent i = new Intent();
                        i.putExtra(Constants.IntentExtras.VOUCHER_TYPE, VoucherType.VOUCHER_PACKAGE);
                        i.putExtra(Constants.IntentExtras.VOUCHER_MODEL, model);
                        setResult(RESULT_OK, i);
                        finish();
                    }

                } else {

                    //Normal usage from HomeActivity
                    Intent i = new Intent(this, DetailTrainerActivity.class);
                    i.putExtra(Constants.IntentExtras.TRAINER_ID, model.getTrainerId());
                    i.putExtra(Constants.IntentExtras.VOUCHER_TYPE, VoucherType.VOUCHER_PACKAGE);
                    i.putExtra(Constants.IntentExtras.VOUCHER_MODEL, model);
                    i.putExtra(Constants.IntentExtras.TRAINER_SPECIALITY_ID, model.getTrainerSpecialityId());
                    showActivity(i);
                }

                break;
            case R.id.button_clear_voucher_code:
                dismissLoading();
                dismissKeyboard();
                etVoucherCode.clearFocus();
                etVoucherCode.setText("");
                break;
        }
    }

    private void setupStealFocusTouchListeners(android.view.View root, android.view.View... views) {
        root.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    dismissKeyboard();
                }
            }
        });
        for (android.view.View v : views) {
            v.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    dismissKeyboard();
                    root.requestFocus();
                }
            });
        }
    }

    private class VoucherCodeInputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String voucherCode = etVoucherCode.getText().toString();

            if (!voucherCode.isEmpty()) {
                //enable submit
                buttonSubmitCodeVoucher.setEnabled(true);
                buttonClearCodeVoucher.setVisibility(View.VISIBLE);
            } else {
                buttonSubmitCodeVoucher.setEnabled(false);
                buttonClearCodeVoucher.setVisibility(View.GONE);
            }
        }
    }
}
