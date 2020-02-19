package com.nostratech.mufit.consumer.booking;

import android.content.Context;

import com.nostratech.mufit.consumer.base.BaseSpinnerAdapter;
import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;

import java.util.List;

public class PaymentMethodSpinnerAdapter extends BaseSpinnerAdapter<PaymentMethodModel,String> {

    public PaymentMethodSpinnerAdapter(Context context, int layoutResourceId, int textViewResId,
                                       List<String> listOfLabels, List<PaymentMethodModel> modelList) {
        super(context, layoutResourceId, textViewResId, listOfLabels, modelList);
    }
}
