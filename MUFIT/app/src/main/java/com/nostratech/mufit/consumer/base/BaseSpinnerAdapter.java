package com.nostratech.mufit.consumer.base;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public abstract class BaseSpinnerAdapter<ModelType, LabelType> extends ArrayAdapter<LabelType> {

    // Your POJOs
    private List<ModelType> listOfModels;

    public BaseSpinnerAdapter(Context context, int layoutResourceId,
                              int textViewResId, List<LabelType> listOfLabels, List<ModelType> modelList) {
        super(context, layoutResourceId, textViewResId, listOfLabels);
        this.listOfModels = modelList;
    }

    public ModelType getModel(int pos){
        return listOfModels.get(pos);
    }

}
