package com.nostratech.mufit.consumer.booking_package;

import android.content.Context;

import com.nostratech.mufit.consumer.base.BaseSpinnerAdapter;
import com.nostratech.mufit.consumer.model.PackagePriceModel;

import java.util.List;

public class TrainerPriceSpinnerAdapter extends BaseSpinnerAdapter<PackagePriceModel, String> {

    public TrainerPriceSpinnerAdapter(Context context, int layoutResourceId,
                                      int textViewResId, List<String> listOfLabels, List<PackagePriceModel> modelList) {
        super(context, layoutResourceId, textViewResId, listOfLabels, modelList);
    }
}
