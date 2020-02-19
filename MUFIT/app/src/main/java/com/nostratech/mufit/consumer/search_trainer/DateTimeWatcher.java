package com.nostratech.mufit.consumer.search_trainer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class DateTimeWatcher implements TextWatcher {

    private View btnCancelFilter;

    public DateTimeWatcher(View btnCancelFilter) {
        this.btnCancelFilter = btnCancelFilter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            btnCancelFilter.setVisibility(View.VISIBLE);
        } else {
            btnCancelFilter.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
