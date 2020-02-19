package com.nostratech.mufit.consumer.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    private NumberFormat rupiahFormat;

    public CurrencyFormatter(){
        Locale localeID = new Locale("in", "ID");
        rupiahFormat = NumberFormat.getInstance(localeID);
    }

    public String getRupiahString(int value){
        return rupiahFormat.format(value);
    }

}
