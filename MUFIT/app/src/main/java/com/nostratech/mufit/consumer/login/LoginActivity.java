package com.nostratech.mufit.consumer.login;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.banner.event.EventDetailActivity;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
import com.nostratech.mufit.consumer.forgot_password.ForgotPasswordActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
import com.nostratech.mufit.consumer.register.RegisterActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.Constants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MyToolbarBackActivity implements LoginContract.View {

    //Flag to determine which Activity to open after successful login
    public static final int OPEN_HOME = 0;
    public static final int OPEN_DETAIL_TRAINER = 1;
    public static final int OPEN_SCHEDULE = 2;
    public static final int OPEN_HISTORY = 3;
    public static final int OPEN_LIST_TRAINER = 4;
    public static final int OPEN_BANNER_EVENT = 5;
    public static final int OPEN_VOUCHER_ACTIVITY = 6;
    public static final int OPEN_MUHEALTH = 7;
    public static final int BEHAVIOR_UNSPECIFIED = 999;

    public static final String FLAG = "flagActivity";

    @BindView(R.id.etEmailPhone)
    EditText etEmailPhone;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvToRegister)
    TextView tvToRegister;
    @BindView(R.id.text_welcome)
    TextView textWelcome;
    @BindView(R.id.text_toRegister2)
    TextView textToRegister2;
    @BindView(R.id.layoutLogin)
    RelativeLayout layoutLogin;
    @BindView(R.id.text_toForgotPass2)
    TextView textToForgotPass2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @BindView(R.id.root_menu)
    ViewGroup root;

    private LoginPresenter loginPresenter;
    private int flagActivity;
    private String trainerId;
//    private String eventId;
//    private String eventImgUrl;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        token = FirebaseInstanceId.getInstance().getToken();
        Intent intent = getIntent();
        flagActivity = intent.getIntExtra("flagActivity", 0);
        trainerId = intent.getStringExtra(Constants.IntentExtras.TRAINER_ID);
//        eventId = intent.getStringExtra("event_id");
//        eventImgUrl = intent.getStringExtra("event_image");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        loginPresenter = new LoginPresenter(this, this, this);

        setupStealFocusTouchListeners(root);
    }

    @OnClick({R.id.btnLogin, R.id.tvToRegister, R.id.text_toForgotPass2})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                dismissLoading();
                dismissKeyboard();
                loginPresenter.login(etEmailPhone.getText().toString(),
                        etPassword.getText().toString(), token);
                break;
            case R.id.tvToRegister:
                dismissLoading();
                showActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.text_toForgotPass2:
                dismissLoading();
                showActivity(new Intent(this, ForgotPasswordActivity.class));
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
        dismissLoading();
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showValidationEmpty() {
        dismissLoading();
        showGenericError(getString(R.string.snackbar_validation_empty));
    }

    /*
        Expected navigation behavior after logging in:

        - If user previously clicked on a trainer pic in Home, then show them the Trainer details
          and add a new Home Activity intent to the bottom of the stack. This forces the page to refresh
          and update the "Login" menu to "Others" menu. (OPEN_DETAIL_TRAINER)

        - If user previously clicked on a card in ListTrainer, then simply return to ListTrainer (OPEN_LIST_TRAINER)

        - If user previously clicked on "Schedule" or "History" menu, open the corresponding Activity (OPEN_SCHEDULE, OPEN_HISTORY)

        - For others that are not mentioned, by default it will simply finish the LoginActivity and return
          to previous activity (BEHAVIOR_UNSPECIFIED)

         */
    @Override
    public void loginSuccess() {
        dismissLoading();

        switch (flagActivity){
            case OPEN_DETAIL_TRAINER:
                //Refresh home screen
                Intent homeIntent = new Intent(this, RootActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                //Open detail trainer with passed trainerId
                Intent detailTrainerIntent = new Intent(this, DetailTrainerActivity.class);
                detailTrainerIntent.putExtra(Constants.IntentExtras.TRAINER_ID, trainerId);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(homeIntent);
                stackBuilder.addNextIntent(detailTrainerIntent);
                stackBuilder.startActivities();

                break;
            case OPEN_SCHEDULE:
                Intent rootIntent = new Intent(this, RootActivity.class);
                rootIntent.putExtra(Constants.RootNavigation.EXTRA_NAVIGATE_TO, RootActivity.NavigateTo.SCHEDULE);
                rootIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showActivity(rootIntent);
                break;
            case OPEN_HISTORY:
                Intent i = new Intent(this, RootActivity.class);
                i.putExtra(Constants.RootNavigation.EXTRA_NAVIGATE_TO, RootActivity.NavigateTo.HISTORY);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showActivity(i);
                break;
            case OPEN_HOME:
                Intent i2 = new Intent(this, RootActivity.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showActivity(i2);
                break;
            case OPEN_LIST_TRAINER:
                //Refresh home screen
                Intent homeIntent2 = new Intent(this, RootActivity.class);
                homeIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                //Open detail trainer with passed trainerId
                Intent listTrainerIntent = new Intent(this, SearchTrainerActivity.class);

                TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(this);
                stackBuilder2.addNextIntent(homeIntent2);
                stackBuilder2.addNextIntent(listTrainerIntent);
                stackBuilder2.startActivities();
                break;
            case OPEN_BANNER_EVENT:
                Intent homeIntent3 = new Intent(this, RootActivity.class);
                homeIntent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent bannerEventIntent = new Intent(this, EventDetailActivity.class);
                Objects.requireNonNull(getIntent().getExtras(), "BannerEvent Extras is null");
                bannerEventIntent.putExtras(getIntent().getExtras());
                TaskStackBuilder stackBuilder3 = TaskStackBuilder.create(this);
                stackBuilder3.addNextIntent(homeIntent3);
                stackBuilder3.addNextIntent(bannerEventIntent);
                stackBuilder3.startActivities();
                break;
            case OPEN_VOUCHER_ACTIVITY:
                Intent homeIntent4 = new Intent(this, RootActivity.class);
                homeIntent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent voucherIntent = new Intent(this, MyVoucherActivity.class);
                Objects.requireNonNull(getIntent().getExtras(), "Voucher activity");
                voucherIntent.putExtras(getIntent().getExtras());
                TaskStackBuilder stackBuilder4 = TaskStackBuilder.create(this);
                stackBuilder4.addNextIntent(homeIntent4);
                stackBuilder4.addNextIntent(voucherIntent);
                stackBuilder4.startActivities();
                break;
            case OPEN_MUHEALTH:
                Intent rootIntent2 = new Intent(this, RootActivity.class);
                rootIntent2.putExtra(Constants.RootNavigation.EXTRA_NAVIGATE_TO, RootActivity.NavigateTo.MUHEALTH);
                rootIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showActivity(rootIntent2);
                break;
            default:
                finishActivity();
                break;
        }
//        if (flagActivity == OPEN_HOME) {
//            finishActivity();
////            showActivityFinishClearTask(LoginActivity.this, HomeActivity_New.class);
//        } else if (flagActivity == OPEN_DETAIL_TRAINER) {
//            backActivityExtra(LoginActivity.this, DetailTrainerActivity.class, "id", trainerId);
//        } else if (flagActivity == OPEN_SCHEDULE) {
//            showActivityFinishClearTask(LoginActivity.this, ScheduleActivity.class);
//        } else if (flagActivity == OPEN_HISTORY) {
//            showActivityFinishClearTask(LoginActivity.this, HistoryBookingActivity.class);
//        } else if (flagActivity == OPEN_LIST_TRAINER) {
//            backActivity(LoginActivity.this, SearchTrainerActivity.class);
//        }
//
////        else if (flagActivity == 5) {
////            backActivityEventExtra(LoginActivity.this, BannerEventDetailActivity.class, eventId, eventImgUrl);
////        }
//
//        else {
//            finishActivity();
//        }

    }

    @Override
    public void loginAsAdmin() {
        dismissLoading();
        showGenericError(getResources().getString(R.string.login_as_admin_validation));
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void setupStealFocusTouchListeners(android.view.View root) {
        root.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    dismissKeyboard();
                }
            }
        });
    }

}
