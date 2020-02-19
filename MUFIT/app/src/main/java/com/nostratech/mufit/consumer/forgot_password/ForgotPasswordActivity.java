package com.nostratech.mufit.consumer.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

//TODO: Fix layout to use padding on root layout instead of adding margin to every view to adapt to larger screens
public class ForgotPasswordActivity extends MyToolbarBackActivity implements ForgotPasswordContract.View {

    @BindView(R.id.forgotPass_etEmail)
    EditText etForgotPass;
    @BindView(R.id.forgotPass_btnForgot)
    Button btnForgotPassword;
    @BindView(R.id.forgotPass_scrollView)
    ScrollView layoutForgotPassword;
    @BindView(R.id.forgotPass_title)
    TextView textTitle;
    @BindView(R.id.forgotPass_text1)
    TextView textForgotPass1;
    @BindView(R.id.forgotPass_text2)
    TextView textForgotPass2;
    @BindView(R.id.forgotPass_text3)
    TextView textForgotPass3;
    @BindView(R.id.forgotPass_textPromptRegister)
    TextView textToRegister;
    @BindView(R.id.forgotPass_text4)
    TextView textForgotPass4;
    @BindView(R.id.forgotPass_textEmailInvalidAlert)
    TextView textEmailAlert;
    @BindView(R.id.forgotPass_btnForgotDisabled)
    Button buttonDisable;
    ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        forgotPasswordPresenter = new ForgotPasswordPresenter(this, this, this);
    }

    @Override
    public void showNoInternetError() {
        dismissKeyboard();
        dismissLoading();
        //TODO: update internet error
        showGenericError(getString(R.string.no_internet));
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
    public void showValidationEmpty() {
        dismissKeyboard();
        dismissLoading();
        showGenericError(getString(R.string.forgot_pass_validation_empty));
    }

    @Override
    public void sendEmailSuccess() {
        dismissKeyboard();
        dismissLoading();
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.dialog_success),
                getString(R.string.forgot_pass_success));
        dialog.getButton().setOnClickListener(v -> finishActivity());
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        forgotPasswordPresenter.observeEmail(etForgotPass);
    }

    @Override
    public void showEmailAlert(boolean value) {
        if (etForgotPass.getText().toString().isEmpty()) {
            textEmailAlert.setVisibility(android.view.View.GONE);
            showButtonEnable(!value);
        } else if (value) {
            textEmailAlert.setVisibility(android.view.View.VISIBLE);
            showButtonEnable(!value);
        } else {
            textEmailAlert.setVisibility(android.view.View.GONE);
            showButtonEnable(!value);
        }
    }

    @Override
    public void showButtonEnable(boolean value) {
        if (value) {
            buttonDisable.setVisibility(android.view.View.GONE);
            btnForgotPassword.setVisibility(android.view.View.VISIBLE);
        } else {
            buttonDisable.setVisibility(android.view.View.VISIBLE);
            btnForgotPassword.setVisibility(android.view.View.GONE);
        }
    }

    @OnClick({R.id.forgotPass_btnForgot, R.id.forgotPass_textPromptRegister})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.forgotPass_btnForgot:
                dismissKeyboard();
                dismissLoading();
                forgotPasswordPresenter.sendEmail(etForgotPass.getText().toString());
                break;
            case R.id.forgotPass_textPromptRegister:
                Intent i = new Intent(this, RegisterActivity.class);
                showActivity(i);
                break;
        }
    }
}
