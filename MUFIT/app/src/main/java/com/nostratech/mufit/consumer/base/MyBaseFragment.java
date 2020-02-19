package com.nostratech.mufit.consumer.base;

import android.content.Intent;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.cache.MyAppCache;

import id.mufit.core.base.BaseFragment;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public abstract class MyBaseFragment extends BaseFragment {

    @Override
    public void onUserUnauthorized() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(getContext(),
                getString(R.string.notice),
                getString(R.string.session_has_expired_prompt));
        dialog.getButton().setOnClickListener(l -> clearCacheReloadRoot());
        dialog.show();
    }

    /**
     * Opens Root Activity and clears cache
     */
    protected void clearCacheReloadRoot(){
        MyAppCache appCache = new MyAppCache(getContext());
        appCache.clearSession();

        Intent root = new Intent(getContext(), RootActivity.class);
        root.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        showActivity(root);
    }

    protected void navigateToLogin(int flag){
        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, flag);
        startActivity(i);
    }

    protected void finishActivity() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    protected void showActivity(Intent intent) {
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


}
