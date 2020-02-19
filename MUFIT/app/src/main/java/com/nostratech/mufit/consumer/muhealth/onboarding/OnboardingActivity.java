package com.nostratech.mufit.consumer.muhealth.onboarding;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public class OnboardingActivity extends MyToolbarBackActivity implements OnboardingContract.View {

    @BindView(R.id.onboarding_toolbar)
    Toolbar toolbar;

    @BindView(R.id.onboarding_etWeight)
    EditText etWeight;

    @BindView(R.id.onboarding_etHeight)
    EditText etHeight;

    private OnboardingContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        presenter = new OnboardingPresenter(this, this, this);
    }

    @OnClick(R.id.onboarding_btnSubmit)
    public void onClick(){
        presenter.submitData(etWeight.getText().toString(), etHeight.getText().toString());
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
    public void showErrorDataEmpty() {
        showGenericError(getString(R.string.validation_onboarding_empty));
    }

    @Override
    public void showOnboardingSuccess() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.notice),
                getString(R.string.onboarding_success));

        dialog.getButton().setOnClickListener(l -> {
            finishActivity();
            dialog.dismiss();
        });

        dialog.show();
    }
}
