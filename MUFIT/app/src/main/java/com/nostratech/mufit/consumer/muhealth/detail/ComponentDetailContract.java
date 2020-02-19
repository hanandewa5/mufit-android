package com.nostratech.mufit.consumer.muhealth.detail;

import com.nostratech.mufit.consumer.muhealth.weightbar.BarData;

import java.util.List;

class ComponentDetailContract {

    interface View {
        void showChartProgress();

        void showChartLeaderboard();

        void showBarIdealValue(double minVal, double maxVal, double currentVal, List<BarData> barData );
    }

    interface Presenter {
        void loadComponentDetail(int id);
    }

}
