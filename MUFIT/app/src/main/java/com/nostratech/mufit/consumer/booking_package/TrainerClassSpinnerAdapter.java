package com.nostratech.mufit.consumer.booking_package;

import android.content.Context;

import com.nostratech.mufit.consumer.base.BaseSpinnerAdapter;
import com.nostratech.mufit.consumer.model.PackageClassModel;

import java.util.List;

import io.reactivex.annotations.NonNull;

class TrainerClassSpinnerAdapter extends BaseSpinnerAdapter<PackageClassModel, String> {

    TrainerClassSpinnerAdapter(Context context, int layoutResId,
                                      @NonNull int textViewResId, List<String> labelList, List<PackageClassModel> data) {
        super(context, layoutResId, textViewResId, labelList, data );
    }

}
