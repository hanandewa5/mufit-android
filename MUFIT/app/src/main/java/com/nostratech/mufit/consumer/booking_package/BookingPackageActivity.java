package com.nostratech.mufit.consumer.booking_package;


import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.booking.PaymentMethodSpinnerAdapter;
import com.nostratech.mufit.consumer.model.PackageClassModel;
import com.nostratech.mufit.consumer.model.PackagePriceModel;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.MufitUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogTwoButtonsWithText;

public class BookingPackageActivity extends MyToolbarBackActivity implements BookingPackageContract.View,
        TransactionFinishedCallback {

    public static final String EXTRA_TRAINER_ID = "trainerId";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_class_list)
    Spinner spinnerPackageClass;
    @BindView(R.id.spinner_package_list)
    Spinner spinnerPackagePrice;
    @BindView(R.id.spinner_payment_method)
    Spinner spinnerPaymentMethod;
    @BindView(R.id.text_dicount_desc)
    TextView textDiscountDesc;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;
    @BindView(R.id.progress_bar_package)
    ProgressBar progressBar;
    @BindView(R.id.text_trainer)
    TextView textTrainerName;

    @BindView(R.id.text_original_price)
    TextView textOriginalPrice;
    @BindView(R.id.text_biaya_tambahan_string)
    TextView textBiayaTambahanString;
    @BindView(R.id.layout_biaya_tambahan)
    RelativeLayout layoutBiayaTambahan;

    @BindView(R.id.text_termAndCondition)
    TextView textTermAndCondition;

    BookingPackageContract.Presenter presenter;

    private TrainerClassSpinnerAdapter trainerClassSpinnerAdapter;
    private TrainerPriceSpinnerAdapter trainerPriceSpinnerAdapter;
    private PaymentMethodSpinnerAdapter paymentMethodSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_package);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new BookingPackagePresenter(this, this, this);
        presenter.initializeData(getIntent().getExtras());
        presenter.getAllPackageByTrainerId();

        textTermAndCondition.setMovementMethod(LinkMovementMethod.getInstance());
//        specialityPackageIDSelected = intent.getStringExtra("specialityPackageIDSelected");
//        packageIDSelectedParse = intent.getStringExtra("packageIDSelected");

        setUpSpinnerPackageClass();
        setUpSpinnerPackagePrice();
        setUpSpinnerPayment();
        buildSDKMidtrans();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        if (trainerPriceSpinnerAdapter != null) {
            trainerPriceSpinnerAdapter.clear();
        }
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
        getErrorDialog().getButton().setOnClickListener(l -> finishActivity());
    }

    @Override
    public void showPackageOriginalPrice(String s) {
        textOriginalPrice.setText(s);
    }

    @Override
    public void setDiscountAndExpiry(int sessions, int expiryDays) {
        String discountMsg = getString(R.string.book_package_expiry_date, sessions, expiryDays);
        textDiscountDesc.setVisibility(View.VISIBLE);
        textDiscountDesc.setText(discountMsg);
    }

    @Override
    public void showFinalPrice(String finalPrice) {
        textPrice.setText(finalPrice);
    }

    @Override
    public void showServiceFee(String price) {
        textBiayaTambahanString.setText("+ Rp " + price);
        layoutBiayaTambahan.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideServiceFee() {
        textBiayaTambahanString.setText("");
        layoutBiayaTambahan.setVisibility(View.GONE);
    }

    @Override
    public void showTrainerName(String trainerName) {
        textTrainerName.setText(trainerName);
    }

    @Override
    public void doShowPaymentMethod(List<PaymentMethodModel> paymentMethodModels) {
        dismissLoading();
        List<String> listLabel = new ArrayList<>();
        for (PaymentMethodModel model : paymentMethodModels) {
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getInstance(localeID);

            String paymentName = model.getName();
            String serviceFee = model.getServiceFee();
            double serviceFeeDouble = Double.parseDouble(serviceFee);
            String label = "%s (+Rp %s)";
            listLabel.add(String.format(label, paymentName, formatRupiah.format(serviceFeeDouble)));
        }

        paymentMethodSpinnerAdapter = new PaymentMethodSpinnerAdapter(this,
                R.layout.booking_package_class_spinner,
                R.id.spinner_text,
                listLabel,
                paymentMethodModels);
        spinnerPaymentMethod.setAdapter(paymentMethodSpinnerAdapter);
    }

    @Override
    public void showConfirmationDialog() {
        dismissKeyboard();

        MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(BookingPackageActivity.this,
                getString(R.string.confirmation),
                getResources().getString(R.string.confirmation_booking));
        dialog.getBtnPositive().setOnClickListener(l -> {
            presenter.onCreateBookingConfirmed();
            dialog.dismiss();
        });
        dialog.show();
    }

    @OnClick(R.id.button_confirm)
    public void onClick() {
        showConfirmationDialog();
    }

    public void buildSDKMidtrans() {
        SdkUIFlowBuilder.init()
                .setClientKey(MufitUtils.MIDTRANS_CLIENT_KEY) // client_key is mandatory
                .setContext(BookingPackageActivity.this) // context is mandatory
                .setTransactionFinishedCallback(BookingPackageActivity.this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(MufitUtils.MIDTRANS_BASE_URL) //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
                .buildSDK();
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    showActivityFinishClearTask(getContext(), MyVoucherActivity.class);
                    openMyVoucherWithHomeParentStack();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
//                    showActivityFinishClearTask(getContext(), MyVoucherActivity.class);
                    openMyVoucherWithHomeParentStack();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(this, RootActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showActivity(i);
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void showTrainerClassList(List<PackageClassModel> packageClassModel) {
        dismissLoading();

        List<String> listLabel = new ArrayList<>();
        for (PackageClassModel packageModel : packageClassModel) {
            listLabel.add(packageModel.getClassName());
        }

        trainerClassSpinnerAdapter =
                new TrainerClassSpinnerAdapter(this, R.layout.booking_package_class_spinner,
                        R.id.spinner_text, listLabel, packageClassModel);
        spinnerPackageClass.setAdapter(trainerClassSpinnerAdapter);
    }

    @Override
    public void addIntoPriceList(List<PackagePriceModel> packagePriceModel) {
        dismissLoading();

        List<String> priceLabelList = new ArrayList<>();
        for (PackagePriceModel model : packagePriceModel) {
            priceLabelList.add(model.getPackageName());
        }

        trainerPriceSpinnerAdapter = new TrainerPriceSpinnerAdapter(this, R.layout.booking_package_class_spinner,
                R.id.spinner_text, priceLabelList, packagePriceModel);
        spinnerPackagePrice.setAdapter(trainerPriceSpinnerAdapter);

    }

    @Override
    public void openBankTransferPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.BANK_TRANSFER);
    }

    @Override
    public void openCreditCardPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.CREDIT_CARD);
    }

    @Override
    public void openGoPayTransferPaymentScreen() {
        MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.GO_PAY);
    }

    //Clears the previous Task in the backstack and creates a new Task
    //with HomeActivity as the root activity, and MyVoucherActivity as the second activity.
    //This way, when user presses back button they will navigate back to HomeActivity
    private void openMyVoucherWithHomeParentStack() {

        Intent homeIntent = new Intent(this, RootActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent mvaIntent = new Intent(this, MyVoucherActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(homeIntent);
        stackBuilder.addNextIntent(mvaIntent);
        stackBuilder.startActivities();
    }

    private void setUpSpinnerPackagePrice() {
        spinnerPackagePrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onPackagePriceSelected(trainerPriceSpinnerAdapter.getModel(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void setUpSpinnerPayment() {
        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onPaymentMethodSelected(paymentMethodSpinnerAdapter.getModel(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUpSpinnerPackageClass() {
        spinnerPackageClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onPackageClassSelected(trainerClassSpinnerAdapter.getModel(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }


}