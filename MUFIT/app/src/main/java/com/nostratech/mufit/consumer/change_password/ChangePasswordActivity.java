package com.nostratech.mufit.consumer.change_password;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public class ChangePasswordActivity extends MyToolbarBackActivity implements
        ChangePasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_currentPass)
    TextInputEditText etCurrentPass;
    @BindView(R.id.et_newPass)
    TextInputEditText etNewPass;
    @BindView(R.id.et_confirmNewPass)
    TextInputEditText etConfirmNewPass;

    ChangePasswordContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new ChangePasswordPresenter(this, this, this);
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

    @OnClick(R.id.btn_ChangePassword)
    public void onViewClicked() {
        presenter.changePassword(etCurrentPass.getText().toString(),
                etNewPass.getText().toString(),
                etConfirmNewPass.getText().toString());
    }

    @Override
    public void changePasswordSuccess() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.confirmation),
                getString(R.string.change_password_success));

        dialog.getButton().setOnClickListener(t -> {
            dialog.dismiss();
            finishActivity();
        });
        dialog.show();
    }

    @Override
    public void showValidationEmpty() {
        showGenericError(getString(R.string.edit_pass_empty_validation));
    }
}
