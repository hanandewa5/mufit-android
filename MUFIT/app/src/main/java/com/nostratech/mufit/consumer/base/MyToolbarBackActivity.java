package com.nostratech.mufit.consumer.base;

import android.content.Context;
import android.content.Intent;

import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.utils.LocaleManager;

import id.mufit.core.base.ToolbarBackActivity;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public abstract class MyToolbarBackActivity extends ToolbarBackActivity {

    private MufitDialogOneButtonWithText errorDialog;

    @Override
    public void onUserUnauthorized() {
        MufitDialogOneButtonWithText dialog =  new MufitDialogOneButtonWithText(this,
                "Notice",
                "Sorry, your session has expired. Please log into the system again.");
        dialog.getButton().setOnClickListener(l -> navigateToLoginSessionExpired());
        dialog.show();
    }

    /**
     * Opens login activity and closes all other activities
     */
    protected void navigateToLoginSessionExpired(){

        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, LoginActivity.BEHAVIOR_UNSPECIFIED);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        showActivity(i);
    }

    @Override
    public void showGenericError(String errorMessage) {
        super.showGenericError(errorMessage);
        dismissLoading();
    }

//    @Override
//    public void showGenericError(String errorMessage) {
//        dismissErrorDialog();
//        this.errorDialog = new MufitDialogOneButtonWithText(this, this.getString(R.string.error), errorMessage);
//        this.errorDialog.getButton().setOnClickListener((l) -> dismissErrorDialog());
//        this.errorDialog.show();
//    }
//
//    protected void dismissErrorDialog(){
//        if(errorDialog != null){
//            errorDialog.dismiss();
//            errorDialog = null;
//        }
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    protected void hideTitle(){
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
