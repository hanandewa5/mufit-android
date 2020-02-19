package com.nostratech.mufit.consumer.base;

import android.content.Context;
import android.content.Intent;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.LocaleManager;
import com.nostratech.mufit.consumer.utils.cache.MyAppCache;

import id.mufit.core.base.BaseActivity;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public abstract class MyBaseActivity extends BaseActivity {

    @Override
    public void onUserUnauthorized() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.notice),
                getString(R.string.session_has_expired_prompt));
        dialog.getButton().setOnClickListener(l -> clearCacheReloadRoot());
        dialog.show();
    }

    /**
     * Opens Root Activity and clears cache
     */
    protected void clearCacheReloadRoot(){
        MyAppCache appCache = new MyAppCache(this);
        appCache.clearSession();

        Intent root = new Intent(this, RootActivity.class);
        root.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        showActivity(root);
    }
    
    protected void showActivityForResult(Intent i, int reqCode){
        startActivityForResult(i, reqCode);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    protected void navigateToLogin(int flag) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, flag);
        showActivity(i);
    }

    @Override
    public void showGenericError(String errorMessage) {
        super.showGenericError(errorMessage);
        dismissLoading();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }
}
