//package com.nostratech.mufit.consumer.booking;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.BaseActivity;
//import com.nostratech.mufit.consumer.base.BaseViewInterface;
//import com.nostratech.mufit.consumer.model.booking.BookingShiftList;
//import com.nostratech.mufit.consumer.model.booking.BookingSpecialityList;
//import com.nostratech.mufit.consumer.model.detail_shift.Shift;
//import com.nostratech.mufit.consumer.model.detail_shift.TrainerSpeciality;
//import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
//import com.nostratech.mufit.consumer.legacy.CustomSpinner;
//import com.nostratech.mufit.consumer.legacy.SessionManager;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class BookingActivity1 extends BaseActivity implements BaseViewInterface, BookingInterface1.View {
//    @BindView(R.id.text_title_date_time)
//    TextView textTitleDateTime;
//    @BindView(R.id.spinner_schedule)
//    Spinner spinnerSchedule;
//    @BindView(R.id.text_select_shift)
//    TextView textSelectShift;
//    @BindView(R.id.text_title_2)
//    TextView textTitle2;
////    @BindView(R.id.text_tax)
////    TextView textTax;
//    @BindView(R.id.button_next)
//    Button buttonNext;
//    @BindView(R.id.checkbox_time_1)
//    CheckBox checkboxTime1;
//    @BindView(R.id.checkbox_time_2)
//    CheckBox checkboxTime2;
//    @BindView(R.id.checkbox_time_3)
//    CheckBox checkboxTime3;
//    @BindView(R.id.checkbox_time_4)
//    CheckBox checkboxTime4;
//    @BindView(R.id.checkbox_time_5)
//    CheckBox checkboxTime5;
//    @BindView(R.id.checkbox_time_6)
//    CheckBox checkboxTime6;
//    @BindView(R.id.checkbox_time_7)
//    CheckBox checkboxTime7;
//    @BindView(R.id.checkbox_time_8)
//    CheckBox checkboxTime8;
//    @BindView(R.id.layout_class_1)
//    RelativeLayout layoutClass1;
//    @BindView(R.id.layout_class_2)
//    RelativeLayout layoutClass2;
//    @BindView(R.id.layout_class_3)
//    RelativeLayout layoutClass3;
//    @BindView(R.id.layout_class_4)
//    RelativeLayout layoutClass4;
//    @BindView(R.id.layout_class_5)
//    RelativeLayout layoutClass5;
//    @BindView(R.id.layout_class_6)
//    RelativeLayout layoutClass6;
//    @BindView(R.id.layout_class_7)
//    RelativeLayout layoutClass7;
//    @BindView(R.id.layout_class_8)
//    RelativeLayout layoutClass8;
//    @BindView(R.id.text_speciality1)
//    TextView textSpeciality1;
//    @BindView(R.id.text_speciality2)
//    TextView textSpeciality2;
//    @BindView(R.id.text_speciality3)
//    TextView textSpeciality3;
//    @BindView(R.id.text_speciality4)
//    TextView textSpeciality4;
//    @BindView(R.id.text_speciality5)
//    TextView textSpeciality5;
//    @BindView(R.id.text_speciality6)
//    TextView textSpeciality6;
//    @BindView(R.id.text_speciality7)
//    TextView textSpeciality7;
//    @BindView(R.id.text_speciality8)
//    TextView textSpeciality8;
//    @BindView(R.id.text_price_1)
//    TextView textPrice1;
//    @BindView(R.id.text_price_2)
//    TextView textPrice2;
//    @BindView(R.id.text_price_3)
//    TextView textPrice3;
//    @BindView(R.id.text_price_4)
//    TextView textPrice4;
//    @BindView(R.id.text_price_5)
//    TextView textPrice5;
//    @BindView(R.id.text_price_6)
//    TextView textPrice6;
//    @BindView(R.id.text_price_7)
//    TextView textPrice7;
//    @BindView(R.id.text_price_8)
//    TextView textPrice8;
//    @BindView(R.id.text_count_1)
//    TextView textCount1;
//    @BindView(R.id.text_count_2)
//    TextView textCount2;
//    @BindView(R.id.text_count_3)
//    TextView textCount3;
//    @BindView(R.id.text_count_4)
//    TextView textCount4;
//    @BindView(R.id.text_count_5)
//    TextView textCount5;
//    @BindView(R.id.text_count_6)
//    TextView textCount6;
//    @BindView(R.id.text_count_7)
//    TextView textCount7;
//    @BindView(R.id.text_count_8)
//    TextView textCount8;
//    @BindView(R.id.button_min_1)
//    Button buttonMin1;
//    @BindView(R.id.button_plus_1)
//    Button buttonPlus1;
//    @BindView(R.id.button_min_2)
//    Button buttonMin2;
//    @BindView(R.id.button_plus_2)
//    Button buttonPlus2;
//    @BindView(R.id.button_min_3)
//    Button buttonMin3;
//    @BindView(R.id.button_plus_3)
//    Button buttonPlus3;
//    @BindView(R.id.button_min_4)
//    Button buttonMin4;
//    @BindView(R.id.button_plus_4)
//    Button buttonPlus4;
//    @BindView(R.id.button_min_5)
//    Button buttonMin5;
//    @BindView(R.id.button_plus_5)
//    Button buttonPlus5;
//    @BindView(R.id.button_min_6)
//    Button buttonMin6;
//    @BindView(R.id.button_plus_6)
//    Button buttonPlus6;
//    @BindView(R.id.button_min_7)
//    Button buttonMin7;
//    @BindView(R.id.button_plus_7)
//    Button buttonPlus7;
//    @BindView(R.id.button_min_8)
//    Button buttonMin8;
//    @BindView(R.id.button_plus_8)
//    Button buttonPlus8;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
//    @BindView(R.id.radio_join)
//    RadioButton radioJoin;
//    @BindView(R.id.radio_separate)
//    RadioButton radioSeparate;
//    @BindView(R.id.radio_schedule)
//    RadioGroup radioSchedule;
//    BookingPresenter1 bPresenter;
//    String tempId, dayName, bookDate;
//    Long timeStamp;
//    String bankName, noRek;
//    List<Shift> shiftList;
//    List<TrainerSpeciality> trainerSpecialityList;
//    List<BookingShiftList> bookingShiftList;
//    List<BookingSpecialityList> bookingSpecialityList;
//    int price = 0;
//    int counter = 0;
//    Boolean validTimes, validClass;
//    int counterHour = 0;
//    int counterSession = 0;
//    int hour;
//    @BindView(R.id.text_total_session)
//    TextView textTotalSession;
//    //todo:fristy - check voucher di booking halamn 1
//    private MyVoucherActivity MVA;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking1);
//        ButterKnife.bind(this);
//        initTypefaces();
//        configureToolbar(true, toolbar);
//        Bundle data = getIntent().getExtras();
//        if (data != null) {
//            tempId = data.getString("id");
//            dayName = data.getString("dayName");
//            bookDate = data.getString("bookDate");
//            timeStamp = data.getLong("date");
//        }
//        Calendar rightNow = Calendar.getInstance();
//        hour = rightNow.get(Calendar.HOUR_OF_DAY);
//        bPresenter = new BookingPresenter1(this, this, getContext());
//        bPresenter.getDetailBooking(getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), tempId, dayName, bookDate, timeStamp);
//
//    }
//
//    @Override
//    public void initTypefaces() {
//        toolbarTitle.setTypeface(getTfMedium());
//        textTitleDateTime.setTypeface(getTfMedium());
//        textSelectShift.setTypeface(getTfMedium());
//        textTitle2.setTypeface(getTfMedium());
//        textTotalSession.setTypeface(getTfRegular());
//        buttonNext.setTypeface(getTfMedium());
////        textTax.setTypeface(getTfMedium());
//        checkboxTime1.setTypeface(getTfRegular());
//        checkboxTime2.setTypeface(getTfRegular());
//        checkboxTime3.setTypeface(getTfRegular());
//        checkboxTime4.setTypeface(getTfRegular());
//        checkboxTime5.setTypeface(getTfRegular());
//        checkboxTime6.setTypeface(getTfRegular());
//        checkboxTime7.setTypeface(getTfRegular());
//        checkboxTime8.setTypeface(getTfRegular());
//        textSpeciality1.setTypeface(getTfRegular());
//        textSpeciality2.setTypeface(getTfRegular());
//        textSpeciality3.setTypeface(getTfRegular());
//        textSpeciality4.setTypeface(getTfRegular());
//        textSpeciality5.setTypeface(getTfRegular());
//        textSpeciality6.setTypeface(getTfRegular());
//        textSpeciality7.setTypeface(getTfRegular());
//        textSpeciality8.setTypeface(getTfRegular());
//        textPrice1.setTypeface(getTfRegular());
//        textPrice2.setTypeface(getTfRegular());
//        textPrice3.setTypeface(getTfRegular());
//        textPrice4.setTypeface(getTfRegular());
//        textPrice5.setTypeface(getTfRegular());
//        textPrice6.setTypeface(getTfRegular());
//        textPrice7.setTypeface(getTfRegular());
//        textPrice8.setTypeface(getTfRegular());
//        textCount1.setTypeface(getTfMedium());
//        textCount2.setTypeface(getTfMedium());
//        textCount3.setTypeface(getTfMedium());
//        textCount4.setTypeface(getTfMedium());
//        textCount5.setTypeface(getTfMedium());
//        textCount6.setTypeface(getTfMedium());
//        textCount7.setTypeface(getTfMedium());
//        textCount8.setTypeface(getTfMedium());
//        buttonMin1.setTypeface(getTfMedium());
//        buttonMin2.setTypeface(getTfMedium());
//        buttonMin3.setTypeface(getTfMedium());
//        buttonMin4.setTypeface(getTfMedium());
//        buttonMin5.setTypeface(getTfMedium());
//        buttonMin6.setTypeface(getTfMedium());
//        buttonMin7.setTypeface(getTfMedium());
//        buttonMin8.setTypeface(getTfMedium());
//        buttonPlus1.setTypeface(getTfMedium());
//        buttonPlus2.setTypeface(getTfMedium());
//        buttonPlus3.setTypeface(getTfMedium());
//        buttonPlus4.setTypeface(getTfMedium());
//        buttonPlus5.setTypeface(getTfMedium());
//        buttonPlus6.setTypeface(getTfMedium());
//        buttonPlus7.setTypeface(getTfMedium());
//        buttonPlus8.setTypeface(getTfMedium());
//        radioJoin.setTypeface(getTfRegular());
//        radioSeparate.setTypeface(getTfRegular());
//    }
//
//    @Override
//    public void showLogDebug(String tag, String message) {
//        Log.i(tag, message);
//    }
//
//    @Override
//    public void noInternetError() {
//        simpleDialog("", getResources().getString(R.string.no_internet), false, false, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finishActivity();
//            }
//        });
//    }
//
//    @Override
//    public void showLoading() {
//        showProgressDialog(getContext());
//    }
//
//    @Override
//    public void dismissLoading() {
//        dismissProgressDialog();
//    }
//
//    @Override
//    public void hideKeyboard() {
//        hideKeyboard(getCurrentFocus());
//    }
//
//    @Override
//    public void showError(String errorMessage) {
//        dismissLoading();
//        simpleDialog("", errorMessage, false, false, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finishActivity();
//            }
//        });
//    }
//
//    @Override
//    public void setSpinnerSchedule(List<String> dayNameList) {
//        CustomSpinner adapter = new CustomSpinner(
//                getContext(),
//                android.R.layout.simple_spinner_dropdown_item,
//                dayNameList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinnerSchedule.setAdapter(adapter);
//        spinnerSchedule.setEnabled(false);
//    }
//
//    @Override
//    public void setCheckboxSchedule(List<Shift> shiftList) {
//        if (shiftList != null && !shiftList.isEmpty()) {
//            this.shiftList = shiftList;
//            for (int i = 0; i < shiftList.size(); i++) {
//                String startTime, endTime, minutesStartTime, minutesEndTime, schedule;
//                switch (i) {
//                    case 0:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime1.setText(schedule);
//                        checkboxTime1.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime1.setEnabled(true);
//                        } else {
//                            checkboxTime1.setEnabled(false);
//                            checkboxTime1.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//
//                        break;
//                    case 1:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime2.setText(schedule);
//                        checkboxTime2.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime2.setEnabled(true);
//                        } else {
//                            checkboxTime2.setEnabled(false);
//                            checkboxTime2.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 2:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime3.setText(schedule);
//                        checkboxTime3.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime3.setEnabled(true);
//                        } else {
//                            checkboxTime3.setEnabled(false);
//                            checkboxTime3.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 3:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime4.setText(schedule);
//                        checkboxTime4.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime4.setEnabled(true);
//                        } else {
//                            checkboxTime4.setEnabled(false);
//                            checkboxTime4.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 4:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime5.setText(schedule);
//                        checkboxTime5.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime5.setEnabled(true);
//                        } else {
//                            checkboxTime5.setEnabled(false);
//                            checkboxTime5.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 5:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime6.setText(schedule);
//                        checkboxTime6.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime6.setEnabled(true);
//                        } else {
//                            checkboxTime6.setEnabled(false);
//                            checkboxTime6.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 6:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime7.setText(schedule);
//                        checkboxTime7.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime7.setEnabled(true);
//                        } else {
//                            checkboxTime7.setEnabled(false);
//                            checkboxTime7.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                    case 7:
//                        minutesStartTime = shiftList.get(i).getStartTime().getMinute().toString();
//                        if (minutesStartTime.length() == 1) {
//                            minutesStartTime = "0" + minutesStartTime;
//                        }
//                        startTime = shiftList.get(i).getStartTime().getHour().toString();
//                        if (startTime.length() == 1) {
//                            startTime = "0" + startTime;
//                        }
//                        startTime += "." + minutesStartTime;
//
//                        minutesEndTime = shiftList.get(i).getEndTime().getMinute().toString();
//                        if (minutesEndTime.length() == 1) {
//                            minutesEndTime = "0" + minutesEndTime;
//                        }
//                        endTime = shiftList.get(i).getEndTime().getHour().toString();
//                        if (endTime.length() == 1) {
//                            endTime = "0" + endTime;
//                        }
//                        endTime += "." + minutesEndTime;
//                        schedule = startTime + " - " + endTime;
//                        checkboxTime8.setText(schedule);
//                        checkboxTime8.setVisibility(View.VISIBLE);
//                        if (new Date(timeStamp).compareTo(new Date(System.currentTimeMillis())) < 0 ) {
//                            if(hour >= shiftList.get(i).getStartTime().getHour()){
//                                shiftList.get(i).setStatus(false);
//                            }
//                        }
//                        if (shiftList.get(i).getStatus()) {
//                            checkboxTime8.setEnabled(true);
//                        } else {
//                            checkboxTime8.setEnabled(false);
//                            checkboxTime8.setTextColor(ContextCompat.getColor(getContext(),R.color.black38));
//                        }
//                        break;
//                }
//            }
//            //spinnerSchedule.setOnItemSelectedListener(this);
//        }
//    }
//
//    @Override
//    public void setCheckboxSpeciality(List<TrainerSpeciality> trainerSpecialityList) {
//        this.trainerSpecialityList = trainerSpecialityList;
//        for (int i = 0; i < trainerSpecialityList.size(); i++) {
//            Locale localeID = new Locale("in", "ID");
//            NumberFormat formatRupiah = NumberFormat.getInstance(localeID);
//            String temp = formatRupiah.format(trainerSpecialityList.get(i).getPrice());
//            switch (i) {
//                case 0:
//                    layoutClass1.setVisibility(View.VISIBLE);
//                    textSpeciality1.setText(trainerSpecialityList.get(i).getName());
//                    textPrice1.setText(temp);
//                    break;
//                case 1:
//                    layoutClass2.setVisibility(View.VISIBLE);
//                    textSpeciality2.setText(trainerSpecialityList.get(i).getName());
//                    textPrice2.setText(temp);
//                    break;
//                case 2:
//                    layoutClass3.setVisibility(View.VISIBLE);
//                    textSpeciality3.setText(trainerSpecialityList.get(i).getName());
//                    textPrice3.setText(temp);
//                    break;
//                case 3:
//                    layoutClass4.setVisibility(View.VISIBLE);
//                    textSpeciality4.setText(trainerSpecialityList.get(i).getName());
//                    textPrice4.setText(temp);
//                    break;
//                case 4:
//                    layoutClass5.setVisibility(View.VISIBLE);
//                    textSpeciality5.setText(trainerSpecialityList.get(i).getName());
//                    textPrice5.setText(temp);
//                    break;
//                case 5:
//                    layoutClass6.setVisibility(View.VISIBLE);
//                    textSpeciality6.setText(trainerSpecialityList.get(i).getName());
//                    textPrice6.setText(temp);
//                    break;
//                case 6:
//                    layoutClass7.setVisibility(View.VISIBLE);
//                    textSpeciality7.setText(trainerSpecialityList.get(i).getName());
//                    textPrice7.setText(temp);
//                    break;
//                case 7:
//                    layoutClass8.setVisibility(View.VISIBLE);
//                    textSpeciality8.setText(trainerSpecialityList.get(i).getName());
//                    textPrice8.setText(temp);
//                    break;
//            }
//        }
//
//    }
//
//    @Override
//    public void checkValidation() {
//        validTimes = !checkboxTime1.isChecked() && !checkboxTime2.isChecked() && !checkboxTime3.isChecked() && !checkboxTime4.isChecked()
//                && !checkboxTime5.isChecked() && !checkboxTime6.isChecked() && !checkboxTime7.isChecked() && !checkboxTime8.isChecked();
//
//        validClass = textCount1.getText().toString().equals("0") && textCount2.getText().toString().equals("0")
//                && textCount3.getText().toString().equals("0") && textCount4.getText().toString().equals("0")
//                && textCount5.getText().toString().equals("0") && textCount6.getText().toString().equals("0");
//    }
//
//    @Override
//    public void getShifts() {
//        bookingShiftList = new ArrayList<>();
//        if (checkboxTime1.getVisibility() == View.VISIBLE && checkboxTime1.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(0).getId()));
//        }
//        if (checkboxTime2.getVisibility() == View.VISIBLE && checkboxTime2.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(1).getId()));
//        }
//        if (checkboxTime3.getVisibility() == View.VISIBLE && checkboxTime3.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(2).getId()));
//        }
//        if (checkboxTime4.getVisibility() == View.VISIBLE && checkboxTime4.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(3).getId()));
//        }
//        if (checkboxTime5.getVisibility() == View.VISIBLE && checkboxTime5.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(4).getId()));
//        }
//        if (checkboxTime6.getVisibility() == View.VISIBLE && checkboxTime6.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(5).getId()));
//        }
//        if (checkboxTime7.getVisibility() == View.VISIBLE && checkboxTime7.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(6).getId()));
//        }
//        if (checkboxTime8.getVisibility() == View.VISIBLE && checkboxTime8.isChecked()) {
//            bookingShiftList.add(new BookingShiftList(shiftList.get(7).getId()));
//        }
//    }
//
//    @Override
//    public void getClasses() {
//        price = 0;
//        bookingSpecialityList = new ArrayList<>();
//        if (layoutClass1.getVisibility() == View.VISIBLE) {
//            if (!textCount1.getText().toString().equals("0")) {
//
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(0).getId(), Integer.parseInt(textCount1.getText().toString())));
//                price += trainerSpecialityList.get(0).getPrice() * Integer.parseInt(textCount1.getText().toString());
//            }
//        }
//        if (layoutClass2.getVisibility() == View.VISIBLE) {
//            if (!textCount2.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(1).getId(), Integer.parseInt(textCount2.getText().toString())));
//                price += trainerSpecialityList.get(1).getPrice() * Integer.parseInt(textCount2.getText().toString());
//            }
//        }
//        if (layoutClass3.getVisibility() == View.VISIBLE) {
//            if (!textCount3.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(2).getId(), Integer.parseInt(textCount3.getText().toString())));
//                price += trainerSpecialityList.get(2).getPrice() * Integer.parseInt(textCount3.getText().toString());
//            }
//        }
//        if (layoutClass4.getVisibility() == View.VISIBLE) {
//            if (!textCount4.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(3).getId(), Integer.parseInt(textCount4.getText().toString())));
//                price += trainerSpecialityList.get(3).getPrice() * Integer.parseInt(textCount4.getText().toString());
//            }
//        }
//        if (layoutClass5.getVisibility() == View.VISIBLE) {
//            if (!textCount5.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(4).getId(), Integer.parseInt(textCount5.getText().toString())));
//                price += trainerSpecialityList.get(4).getPrice() * Integer.parseInt(textCount5.getText().toString());
//            }
//        }
//        if (layoutClass6.getVisibility() == View.VISIBLE) {
//            if (!textCount6.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(5).getId(), Integer.parseInt(textCount6.getText().toString())));
//                price += trainerSpecialityList.get(5).getPrice() * Integer.parseInt(textCount6.getText().toString());
//            }
//        }
//        if (layoutClass7.getVisibility() == View.VISIBLE) {
//            if (!textCount7.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(6).getId(), Integer.parseInt(textCount7.getText().toString())));
//                price += trainerSpecialityList.get(6).getPrice() * Integer.parseInt(textCount7.getText().toString());
//            }
//        }
//        if (layoutClass8.getVisibility() == View.VISIBLE) {
//            if (!textCount8.getText().toString().equals("0")) {
//                bookingSpecialityList.add(new BookingSpecialityList(trainerSpecialityList.get(7).getId(), Integer.parseInt(textCount8.getText().toString())));
//                price += trainerSpecialityList.get(7).getPrice() * Integer.parseInt(textCount8.getText().toString());
//            }
//        }
//    }
//
//    @Override
//    public void setBank(String bankName, String noRek) {
//        this.bankName = bankName;
//        this.noRek = noRek;
//    }
//
//    @Override
//    public void checkSchedule(int counter) {
//        if (counter > 1) {
//            radioSchedule.setVisibility(View.VISIBLE);
//        } else {
//            radioSchedule.setVisibility(View.GONE);
//            radioSeparate.setChecked(true);
//            radioSeparate.setTextColor(getResources().getColor(R.color.black87));
//            radioJoin.setTextColor(getResources().getColor(R.color.grey_2));
//        }
//    }
//
//
//    public void onCheckboxClicked(View view) {
//        boolean checked = ((CheckBox) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.checkbox_time_1:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(0).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(0).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_2:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(1).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(1).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_3:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(2).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(2).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_4:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(3).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(3).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_5:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(4).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(4).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_6:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(5).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(5).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_7:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(6).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(6).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//            case R.id.checkbox_time_8:
//                textTotalSession.setVisibility(View.VISIBLE);
//                if (checked) {
//                    counter += 1;
//                    checkSchedule(counter);
//                    counterHour += shiftList.get(7).getShiftRange();
//                    String temp = getString(R.string.choose_total_booking) + counterHour;
//                    textTotalSession.setText(temp);
//                } else {
//                    if (counter != 0) {
//                        counter -= 1;
//                        checkSchedule(counter);
//                        counterHour -= shiftList.get(7).getShiftRange();
//                        String temp = getString(R.string.choose_total_booking) + counterHour;
//                        textTotalSession.setText(temp);
//                    }
//                }
//                break;
//        }
//    }
//
//    @OnClick({R.id.button_min_1, R.id.button_plus_1, R.id.button_min_2, R.id.button_plus_2, R.id.button_min_3,
//            R.id.button_plus_3, R.id.button_min_4, R.id.button_plus_4, R.id.button_min_5, R.id.button_plus_5,
//            R.id.button_min_6, R.id.button_plus_6, R.id.button_min_7, R.id.button_plus_7,
//            R.id.button_min_8, R.id.button_plus_8, R.id.button_next})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button_min_1:
//                int min1 = Integer.parseInt(textCount1.getText().toString());
//                if (min1 >= 1) {
//                    min1--;
//                    counterSession--;
//                    String tempCount = "" + min1;
//                    textCount1.setText(tempCount);
//                }
//                break;
//            case R.id.button_plus_1:
//                if (counterSession < counterHour) {
//                    int plus1 = Integer.parseInt(textCount1.getText().toString());
//                    plus1++;
//                    counterSession++;
//                    String tempCount = "" + plus1;
//                    textCount1.setText(tempCount);
//                }
//                break;
//            case R.id.button_min_2:
//                int min2 = Integer.parseInt(textCount2.getText().toString());
//                if (min2 >= 1) {
//                    min2--;
//                    counterSession--;
//                    String tempCount2 = "" + min2;
//                    textCount2.setText(tempCount2);
//                }
//                break;
//            case R.id.button_plus_2:
//                if (counterSession < counterHour) {
//                    int plus2 = Integer.parseInt(textCount2.getText().toString());
//                    plus2++;
//                    counterSession++;
//                    String tempCount2 = "" + plus2;
//                    textCount2.setText(tempCount2);
//                }
//                break;
//            case R.id.button_min_3:
//                int min3 = Integer.parseInt(textCount3.getText().toString());
//                if (min3 >= 1) {
//                    min3--;
//                    counterSession--;
//                    String tempCount3 = "" + min3;
//                    textCount3.setText(tempCount3);
//                }
//                break;
//            case R.id.button_plus_3:
//                if (counterSession < counterHour) {
//                    int plus3 = Integer.parseInt(textCount3.getText().toString());
//                    plus3++;
//                    counterSession++;
//                    String tempCount3 = "" + plus3;
//                    textCount3.setText(tempCount3);
//                }
//                break;
//            case R.id.button_min_4:
//                int min4 = Integer.parseInt(textCount4.getText().toString());
//                if (min4 >= 1) {
//                    min4--;
//                    counterSession--;
//                    String tempCount4 = "" + min4;
//                    textCount4.setText(tempCount4);
//                }
//                break;
//            case R.id.button_plus_4:
//                if (counterSession < counterHour) {
//                    int plus4 = Integer.parseInt(textCount4.getText().toString());
//                    plus4++;
//                    counterSession++;
//                    String tempCount4 = "" + plus4;
//                    textCount4.setText(tempCount4);
//                }
//                break;
//            case R.id.button_min_5:
//                int min5 = Integer.parseInt(textCount5.getText().toString());
//                if (min5 >= 1) {
//                    min5--;
//                    counterSession--;
//                    String tempCount5 = "" + min5;
//                    textCount5.setText(tempCount5);
//                }
//                break;
//            case R.id.button_plus_5:
//                if (counterSession < counterHour) {
//                    int plus5 = Integer.parseInt(textCount5.getText().toString());
//                    plus5++;
//                    counterSession++;
//                    String tempCount5 = "" + plus5;
//                    textCount5.setText(tempCount5);
//                }
//                break;
//            case R.id.button_min_6:
//                int min6 = Integer.parseInt(textCount6.getText().toString());
//                if (min6 >= 1) {
//                    min6--;
//                    counterSession--;
//                    String tempCount6 = "" + min6;
//                    textCount6.setText(tempCount6);
//                }
//                break;
//            case R.id.button_plus_6:
//                if (counterSession < counterHour) {
//                    int plus6 = Integer.parseInt(textCount6.getText().toString());
//                    plus6++;
//                    counterSession++;
//                    String tempCount6 = "" + plus6;
//                    textCount6.setText(tempCount6);
//                }
//                break;
//            case R.id.button_min_7:
//                int min7 = Integer.parseInt(textCount7.getText().toString());
//                if (min7 >= 1) {
//                    min7--;
//                    counterSession--;
//                    String tempCount7 = "" + min7;
//                    textCount7.setText(tempCount7);
//                }
//                break;
//            case R.id.button_plus_7:
//                if (counterSession < counterHour) {
//                    int plus7 = Integer.parseInt(textCount7.getText().toString());
//                    plus7++;
//                    counterSession++;
//                    String tempCount7 = "" + plus7;
//                    textCount7.setText(tempCount7);
//                }
//                break;
//            case R.id.button_min_8:
//                int min8 = Integer.parseInt(textCount8.getText().toString());
//                if (min8 >= 1) {
//                    min8--;
//                    counterSession--;
//                    String tempCount8 = "" + min8;
//                    textCount8.setText(tempCount8);
//                }
//                break;
//            case R.id.button_plus_8:
//                if (counterSession < counterHour) {
//                    int plus8 = Integer.parseInt(textCount8.getText().toString());
//                    plus8++;
//                    counterSession++;
//                    String tempCount8 = "" + plus8;
//                    textCount8.setText(tempCount8);
//                }
//                break;
//            case R.id.button_next:
//                Boolean joinShifts = false;
//                if (radioSchedule.getVisibility() == View.VISIBLE) {
//                    int selectedId = radioSchedule.getCheckedRadioButtonId();
//                    joinShifts = selectedId == R.id.radio_join;
//                }
//
//                checkValidation();
//
//                if (!validTimes && !validClass && counterSession == counterHour) {
//                    getShifts();
//                    getClasses();
//
//                    Bundle data = new Bundle();
//                    data.putString("trainerId", tempId);
//                    data.putLong("timeStamp", timeStamp);
//                    data.putInt("totalPrice", price);
//                    data.putString("bankName", bankName);
//                    data.putString("noRek", noRek);
//                    data.putBoolean("joinShifts", joinShifts);
//                    data.putParcelableArrayList("bookingShift", (ArrayList<? extends Parcelable>) bookingShiftList);
//                    data.putParcelableArrayList("bookingSpeciality", (ArrayList<? extends Parcelable>) bookingSpecialityList);
//
//                    //todo : fristy - check speciality id user choose dengan voucher
//                    MVA = new MyVoucherActivity();
//                    if(MVA!=null) {
//                        if(MVA.isVoucherUsed()) {
//                            if(bookingSpecialityList.size() == 1) {
//                                //Log.d("FRISTY LOG", "bookingSpecialityList.get(0).getIdTrainerSpeciality() " +bookingSpecialityList.get(0).getIdTrainerSpeciality());
//                                //Log.d("FRISTY LOG", "MVA.voucherTrainerSpecialityID " +MVA.voucherTrainerSpecialityID());
//                                if(bookingSpecialityList.get(0).getIdTrainerSpeciality().equals(MVA.voucherTrainerSpecialityID()))
//                                    showActivityBundle(getContext(), BookingActivity2.class, data);
//                                else
//                                    simpleDialog("", getResources().getString(R.string.voucher_choosen_not_match), false, false, null);
//                            }
//                            else
//                                simpleDialog("", getResources().getString(R.string.voucher_choosen_not_match), false, false, null);
//                        }
//                        else
//                            showActivityBundle(getContext(), BookingActivity2.class, data);
//                    }
//                    else
//                        showActivityBundle(getContext(), BookingActivity2.class, data);
//                } else if (validTimes && validClass) {
//
//                    simpleDialog("", getResources().getString(R.string.time_class_validation), false, false, null);
//                } else if (counterSession != counterHour) {
//                    simpleDialog("", getResources().getString(R.string.session_validation), false, false, null);
//
//                }
//
//                break;
//        }
//    }
//
//    public void onRadioClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.radio_join:
//                if (checked) {
//                    radioSeparate.setTextColor(getResources().getColor(R.color.grey_2));
//                    radioJoin.setTextColor(getResources().getColor(R.color.black87));
//                }
//                break;
//            case R.id.radio_separate:
//                if (checked) {
//                    radioSeparate.setTextColor(getResources().getColor(R.color.black87));
//                    radioJoin.setTextColor(getResources().getColor(R.color.grey_2));
//                }
//                break;
//        }
//    }
//}