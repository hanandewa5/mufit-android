package com.nostratech.mufit.consumer.muhealth.detail;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.muhealth.weightbar.BarData;
import com.nostratech.mufit.consumer.muhealth.weightbar.MultiColorBar;
import com.nostratech.mufit.consumer.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComponentDetailActivity extends MyToolbarBackActivity implements ComponentDetailContract.View{

    @BindView(R.id.componentDetail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.componentDetail_chartProgress)
    LineChart chartProgress;

    @BindView(R.id.componentDetail_chartLeaderboard)
    LineChart chartLeaderboard;

    @BindView(R.id.componentDetail_barIdealValue)
    MultiColorBar barIdealValue;

    private ComponentDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_detail);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new ComponentDetailPresenter(this, this, this);

        initializeIntentExtras();
        configureChartProgress();
        configureChartLeaderboard();
    }

    private void initializeIntentExtras(){
        Bundle data = getIntent().getExtras();
        Objects.requireNonNull(data);

        int id = data.getInt(Constants.HealthComponent.ID);
        presenter.loadComponentDetail(id);
    }

    private void configureChartProgress(){
        // if disabled, scaling can be done on x- and y-axis separately
        chartProgress.setPinchZoom(false);
        chartProgress.getLegend().setEnabled(false);

        chartProgress.setDrawGridBackground(false);
        chartProgress.getDescription().setEnabled(false);

        XAxis x = chartProgress.getXAxis();
        x.setEnabled(false);

        YAxis yLeft = chartProgress.getAxisLeft();
        yLeft.setEnabled(false);

        YAxis yRight = chartProgress.getAxisRight();
        yRight.setEnabled(false);
    }

    private void configureChartLeaderboard(){
        // if disabled, scaling can be done on x- and y-axis separately
        chartLeaderboard.setPinchZoom(false);
        chartLeaderboard.getLegend().setEnabled(false);

        chartLeaderboard.setDrawGridBackground(false);
        chartLeaderboard.getDescription().setEnabled(false);

        XAxis x = chartLeaderboard.getXAxis();
        x.setAxisMaximum(98f);
        x.setAxisMinimum(46f);

        YAxis yLeft = chartLeaderboard.getAxisLeft();
        yLeft.setEnabled(false);

        YAxis yRight = chartLeaderboard.getAxisRight();
        yRight.setEnabled(false);
    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showChartProgress() {
        List<Entry> entries = new ArrayList<Entry>();

        //Points in the line chartProgress
        entries.add(new Entry(1, 20f));
        entries.add(new Entry(2, 19f));
        entries.add(new Entry(3, 20.5f));
        entries.add(new Entry(4, 21.5f));
        entries.add(new Entry(5, 23.5f));
        entries.add(new Entry(6, 20.5f));
        entries.add(new Entry(7, 19.7f));

        LineDataSet set1 = new LineDataSet(entries, "BMI"); // add entries to dataset
        set1.setColor(Color.WHITE);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawCircles(true);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setColor(Color.WHITE);
        set1.setFillColor(Color.WHITE);

        LineData lineData = new LineData(set1);
        lineData.setDrawValues(false);

        chartProgress.setData(lineData);
        chartProgress.invalidate(); // refres
    }

    @Override
    public void showChartLeaderboard() {
        List<Entry> entries = new ArrayList<Entry>();
        int min = 46;
        int max = 99;

        Random r = new Random();
        for (int i = min; i< max; i++){
            float random = min + r.nextFloat() * (max - min);
            entries.add(new Entry(i, random));
        }


        LineDataSet set1 = new LineDataSet(entries, "BMI"); // add entries to dataset
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setCubicIntensity(0.4f);
        set1.setFillColor(getResources().getColor(R.color.error_red));
        set1.setColor(Color.RED);
        set1.setDrawHorizontalHighlightIndicator(false);
        LineData lineData = new LineData(set1);
        lineData.setDrawValues(false);

        chartLeaderboard.setData(lineData);
        chartLeaderboard.invalidate(); // refres
    }

    @Override
    public void showBarIdealValue(double minVal, double maxVal, double currentVal, List<BarData> barData) {
        barIdealValue.post(()->{
            barIdealValue.setValueRange(0, 38);

            List<BarData> data = new ArrayList<>();
            data.add(new BarData(getResources().getColor(R.color.muhealth_underweight), 18.5, "Underweight"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_normal), 25, "Normal"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_monitor), 28, "OB I"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_bad), 32, "OB II"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_verybad), 38, "OB III"));

            barIdealValue.setData(data, 23.0);
        });

    }
}
