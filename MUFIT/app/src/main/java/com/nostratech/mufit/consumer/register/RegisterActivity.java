package com.nostratech.mufit.consumer.register;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public class RegisterActivity extends MyBaseActivity implements RegisterContract.View {
    @BindView(R.id.text_welcome)
    TextView textWelcome;
    @BindView(R.id.text_email)
    EditText textEmail;
    @BindView(R.id.text_phone)
    EditText textPhone;
    @BindView(R.id.text_password)
    EditText textPassword;
    @BindView(R.id.text_confirm_password)
    EditText textConfirmPassword;
    @BindView(R.id.button_register)
    Button buttonRegister;
    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.text_ask_account_1)
    TextView textAskAccount1;
    @BindView(R.id.text_ask_account_2)
    TextView textAskAccount2;
    @BindView(R.id.layout_register)
    ScrollView layoutRegister;
    @BindView(R.id.text_fullname)
    EditText textFullname;
    @BindView(R.id.text_email_alert)
    TextView textEmailAlert;
    @BindView(R.id.text_password_confirmation_alert)
    TextView textPasswordConfirmationAlert;
    @BindView(R.id.text_phone_alert)
    TextView textPhoneAlert;
    @BindView(R.id.text_password_alert)
    TextView textPasswordAlert;
    @BindView(R.id.button_disable)
    Button buttonDisable;

    @BindView(R.id.root)
    ViewGroup root;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerPresenter = new RegisterPresenter(this, this, this);

        setupStealFocusTouchListeners(root);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerPresenter.observeInvalidStream(textFullname, textEmail, textPhone, textPassword, textConfirmPassword);
    }

    @OnClick({R.id.button_register, R.id.text_login})
    public void onClick(View view) {
        dismissKeyboard();
        switch (view.getId()) {
            case R.id.button_register:
                registerPresenter.doRegister(textEmail.getText().toString(), textPhone.getText().toString(),
                        textPassword.getText().toString(), textConfirmPassword.getText().toString(), textFullname.getText().toString());
                break;
            case R.id.text_login:
                goToLoginActivity();
                break;
        }
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showValidationEmpty() {
        showGenericError(getString(R.string.empty_form_registration));
    }

    @Override
    public void showPasswordNotMatch() {
        showGenericError(getString(R.string.password_not_match));
    }
    @Override
    public void showSuccessRegistration() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(RegisterActivity.this,
                getResources().getString(R.string.dialog_success),
                getResources().getString(R.string.check_email_activate));
        dialog.getButton().setOnClickListener(t -> goToLoginActivity());
        dialog.show();
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
    public void goToLoginActivity() {
        finishActivity();
    }

    @Override
    public void showEmailAlert(boolean value) {
        if (textEmail.getText().toString().isEmpty()) {
            textEmailAlert.setVisibility(View.GONE);
        } else if (value) {
            textEmailAlert.setVisibility(View.VISIBLE);
        } else {
            textEmailAlert.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPhoneAlert(boolean value) {
        if (textPhone.getText().toString().isEmpty()) {
            textPhoneAlert.setVisibility(View.GONE);
        } else if (value) {
            textPhoneAlert.setVisibility(View.VISIBLE);
        } else {
            textPhoneAlert.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPasswordAlert(boolean value) {
        if (textPassword.getText().toString().isEmpty()) {
            textPasswordAlert.setVisibility(View.GONE);
        } else if (value) {
            textPasswordAlert.setVisibility(View.VISIBLE);
        } else {
            textPasswordAlert.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPasswordConfirmationAlert(boolean value) {
        if (value) {
            textPasswordConfirmationAlert.setVisibility(View.VISIBLE);
        } else {
            textPasswordConfirmationAlert.setVisibility(View.GONE);
        }
    }

    @Override
    public void showButtonEnabled(boolean value) {
        if (value) {
            buttonDisable.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.VISIBLE);
        } else {
            buttonDisable.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.GONE);
        }
    }

    private void setupStealFocusTouchListeners(View root) {
        root.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dismissKeyboard();
                }
            }
        });
    }
}