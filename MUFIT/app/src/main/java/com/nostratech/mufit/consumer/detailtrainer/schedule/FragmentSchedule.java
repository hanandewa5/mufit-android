package com.nostratech.mufit.consumer.detailtrainer.schedule;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.model.detail_shift.Shift;
import com.nostratech.mufit.consumer.model.detail_shift.TrainerSpeciality;
import com.nostratech.mufit.consumer.model.trainer_package.PackageModel;
import com.nostratech.mufit.consumer.search_trainer.AdapterListTrainer;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogTwoButtonsWithText;
import id.mufit.singleweekcalendar.CalendarAdapter;
import id.mufit.singleweekcalendar.CalendarDay;
import id.mufit.singleweekcalendar.CustomCalendar;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

//TODO: Create a Presenter to contain logic
public class FragmentSchedule extends MyBaseFragment implements FragmentScheduleContract.View,
        CalendarAdapter.OnDateSelectedListener,
        ScheduleSpecialityAdapter.OnStateChangeListener,
        TimeAdapter.OnStateChangeListener{

    public static final String TAG = FragmentSchedule.class.getSimpleName();
    private static final int MONTHS_RANGE = 3;

    @BindView(R.id.calendar_view)
    CustomCalendar calendarView;

    @BindView(R.id.scroll_view_schedule)
    ScrollView mScrollView;

    @BindView(R.id.empty_content)
    LinearLayout emptyContent;
    @BindView(R.id.rv_speciality)
    RecyclerView rvSpeciality;
    @BindView(R.id.rv_schedule)
    RecyclerView rvSchedule;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
//    @BindView(R.id.btn_booking)
//    Button btnBooking;
    @BindView(R.id.progress_bar_content)
    ProgressBar progressBarContent;
    @BindView(R.id.button_my_voucher)
    Button buttonMyVoucher;
    @BindView(R.id.button_cancel_voucher_use)
    Button buttonCancelVoucherUse;
    @BindView(R.id.layout_myVoucher)
    RelativeLayout layoutMyVoucher;
    @BindView(R.id.text_myVoucherName)
    TextView textMyVoucherPackageName;
//    @BindView(R.id.button_book_package)
//    Button buttonBookPackage;

    private FragmentSchedulePresenter fsPresenter;
    private String trainerId;
    private Date date;
    List<PackageModel> listPackage = new ArrayList<>();

    private OnTutorialFinishListener listener;
    private ScheduleSpecialityAdapter specialityAdapter;
    private TimeAdapter mAdapter;
    TutorialManager tutorialManager;
    private AdapterListTrainer mTrainerAdapter;

    @Nullable
    private String voucherCode;
    private String trainerName;
    private int discountValue;

    private ToggleButtonCallback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);

        tutorialManager = new TutorialManager(getActivity());

        retrieveIntentExtras();
        configureRvSpeciality();

        fsPresenter = new FragmentSchedulePresenter(getActivity(), this, this);
        rvSchedule.setNestedScrollingEnabled(false);
        initCalendarView();

        getTrainerScheduleForAMonth();
        //Get packages for selected trainer
        fsPresenter.getAllPackage(trainerId);

        return view;
    }

    private void getTrainerScheduleForAMonth(){
        Calendar calendar = Calendar.getInstance();
        long startDate = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, 1);
        long endDate = calendar.getTimeInMillis();

        fsPresenter.getTrainerSchedule(trainerId, startDate, endDate);
    }

    private void retrieveIntentExtras() {
        Bundle args = getArguments();
        Objects.requireNonNull(args, "FragmentSchedule arguments is null");
        trainerId = args.getString(Constants.IntentExtras.TRAINER_ID);
        voucherCode = args.getString(Constants.IntentExtras.VOUCHER_CODE);
        discountValue = args.getInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE);
        trainerName = args.getString(Constants.IntentExtras.TRAINER_NAME);

        if (voucherCode != null) {
            layoutMyVoucher.setVisibility(View.VISIBLE);
            textMyVoucherPackageName.setText(voucherCode);
        }
    }

    public void setListener(OnTutorialFinishListener listener) {
        this.listener = listener;
    }

    public static FragmentSchedule newInstance(String trainerId, String voucherCode,
                                               int discountValue, String specialityId) {
        FragmentSchedule fragment = new FragmentSchedule();

        //Pass necessary values
        Bundle args = new Bundle();
        args.putString(Constants.IntentExtras.TRAINER_ID, trainerId);
        args.putString(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
        args.putInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, discountValue);
        args.putString(Constants.IntentExtras.TRAINER_SPECIALITY_ID, specialityId);

        fragment.setArguments(args);
        return fragment;
    }

    public void setTrainerName(String name){
        this.trainerName = name;
    }

    @Override
    public void showEmptySchedule() {
        emptyContent.setVisibility(View.VISIBLE);
        layoutContent.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        emptyContent.setVisibility(View.GONE);
        layoutContent.setVisibility(View.VISIBLE);
    }

    public void initCalendarView() {
        calendarView.init(this, null, MONTHS_RANGE);
    }


    @Override
    public void showTrainerShiftsForSelectedDate(List<Shift> shiftList) {


        mAdapter = new TimeAdapter(shiftList, this);
        RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvSchedule.setLayoutManager(mLayoutManager);
        rvSchedule.setHasFixedSize(true);
        rvSchedule.setAdapter(mAdapter);

        //Play tutorial only if there arny shifts
        for (int i = 0; i < shiftList.size(); i++) {
            if (shiftList.get(i).getStatus()) {
                checkTutorial();
                break;
            }
        }

    }

    private void checkTutorial() {
        if (!tutorialManager.isTutorialBookingSchedule()) {
            showTutorialChooseSchedule();
        }
    }

    @Override
    public void setSpecialityList(List<TrainerSpeciality> specialityList, boolean noShiftAvailable) {

        if (voucherCode != null) {
            String specialityId = getArguments().getString(Constants.IntentExtras.TRAINER_SPECIALITY_ID, null);

            if(specialityId != null) {
                specialityList = getSpecialityUsedInVoucherPackage(specialityList, specialityId);
            }
        }

        specialityAdapter = new ScheduleSpecialityAdapter(specialityList, this, noShiftAvailable);
        rvSpeciality.setAdapter(specialityAdapter);
    }

    /**
     * Returns the TrainerSpeciality that corresponds to the Package Voucher being used.
     * In the case of Normal Vouchers, the list will not be filtered
     *
     * @param specialityList - List of all TrainerSpecialitys
     * @param desiredTrainerSpecialityId - ID of the TrainerSpeciality described in Voucher Package
     * @return the desired TrainerSpeciality, or returns the original list if the speciality cannot be found
     */
    private List<TrainerSpeciality> getSpecialityUsedInVoucherPackage(List<TrainerSpeciality> specialityList, String desiredTrainerSpecialityId){

        int index = -1;

        //Get index of the Trainer Speciality described in the voucher
        for(int i = 0; i < specialityList.size(); i++){
            TrainerSpeciality ts = specialityList.get(i);

            if(ts.getId().equalsIgnoreCase(desiredTrainerSpecialityId)){
                index = i;
                break;
            }
        }

        return index != -1
                ? Collections.singletonList(specialityList.get(index))
                : specialityList;
    }

    @Override
    public void setTrainerAvailabilityIndicators(List<CalendarDay> redDays, List<CalendarDay> greenDays) {
        calendarView.addTrainerBusyDays(redDays);
        calendarView.addTrainerFreeDays(greenDays);
    }

    @Override
    public void setListOfPackages(List<PackageModel> listPackage) {
        this.listPackage = listPackage;
    }

    @Override
    public void showLoading() {
        progressBarContent.setVisibility(View.VISIBLE);
        emptyContent.setVisibility(View.GONE);
        layoutContent.setVisibility(View.GONE);
//        btnBooking.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        progressBarContent.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        showGenericError(getString(R.string.no_internet));
    }

    private void configureRvSpeciality(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSpeciality.setLayoutManager(layoutManager);
        rvSpeciality.setNestedScrollingEnabled(false);

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = rvSpeciality.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }


    @OnClick({R.id.button_cancel_voucher_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_cancel_voucher_use:
                showCancelVoucherDialog();
                break;
        }
    }
    private void showCancelVoucherDialog() {
        //Create a dialog which asks user whether to cancel their voucher usage or not
        //If user chose "YES", then cancel voucher usage and bring them back to MyVoucherActivity
        //Else if "NO", close dialog and do nothing
        MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(getContext(),
                getString(R.string.confirmation),
                getString(R.string.cancel_voucher_then_return));

        dialog.getBtnPositive().setOnClickListener(l -> {
            getActivity().setResult(Constants.RESULT_VOUCHER_CANCELLED);
            getActivity().finish();
        });

        dialog.show();
    }

    @Override
    public void onDateSelected(CalendarDay calendarDay) {
//        buttonBookPackage.setVisibility(View.GONE);
        callback.toggleButtonBooking(false);
        callback.toggleButtonPackage(false);
        Calendar selectedDay = Calendar.getInstance();
        selectedDay.setTimeInMillis(calendarDay.getDateInMillis());

        String dayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendarDay.getDate());

        String bookDate = "" + calendarDay.getDate().getTime();
        fsPresenter.getDetailShift(trainerId, dayName, bookDate, calendarDay.getDate());
    }

    public void showTutorialCalendar() {
        TutorialBuilder tb = new TutorialBuilder(getActivity(), FontUtils.getTruenoMedium(getContext()));
        tb.addSequenceItemRectangleShape(calendarView,
                getString(R.string.next),
                getString(R.string.calender_view_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        listener.onTutorialButton();
                    }
                },
                true);

        tb.start();
    }

    public void showTutorialChooseSchedule() {
        TutorialBuilder tb = new TutorialBuilder(getActivity(), FontUtils.getTruenoMedium(getContext()));
        tb.addSequenceItemRectangleShape(rvSchedule,
                getString(R.string.next),
                getString(R.string.schedule_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        mScrollView.postDelayed(() -> {
                            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                            Handler handler = new Handler();

                            handler.postDelayed(() -> showTutorialSpeciality(), 100);
                        }, 300);
                    }
                },
                true);
        tb.start();
    }

    public void showTutorialSpeciality() {
        TutorialBuilder tb = new TutorialBuilder(getActivity(), FontUtils.getTruenoMedium(getContext()));

        RecyclerView.ViewHolder holder = rvSpeciality.findViewHolderForAdapterPosition(0);

        tb.addSequenceItemRectangleShape(holder.itemView.findViewById(R.id.layout_speciality_trainer),
                getString(R.string.next),
                getString(R.string.speciality_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        tutorialManager.finishTutorialBookingSchedule();
                    }
                },
                true);

        tb.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fsPresenter.cancelAllRequests();
    }

    @Override
    public void onStateChanged() {
        String selectedSpesiality = specialityAdapter.getSelectedTrainerSpecialityId();
        String selectedSchedule = mAdapter.getIdSelectedSchedule();

        if (selectedSchedule !=null && selectedSpesiality != null){
            //panggil interface
            callback.toggleButtonBooking(true);
            onPackageSelected(specialityAdapter.getSelectedTrainerSpecialityId());
        }else {
            callback.toggleButtonBooking(false);
            callback.toggleButtonPackage(false);
        }
    }

    @Override
    public void onPackageSelected(String specialityIDSelected) {

        boolean packageAvailable = false;

        if(listPackage.isEmpty()) {
            callback.toggleButtonPackage(packageAvailable);
        } else {
            for (PackageModel pkg: listPackage){
                String trainerSpecialityId = pkg.getPackageTrainerSpeciality().getTrainerSpecialityId();
                if(trainerSpecialityId.equalsIgnoreCase(specialityIDSelected)){
                    packageAvailable = true;
                    break;
                }
            }
        }
        callback.toggleButtonPackage(packageAvailable);
    }

    public interface OnTutorialFinishListener{
        void onTutorialButton();
    }

    public interface ToggleButtonCallback {
        void toggleButtonBooking(boolean enable);
        void toggleButtonPackage(boolean enable);
    }

    public void setCallback(ToggleButtonCallback callback){
        this.callback = callback;
    }

    public Date getSelectedDate(){
        return calendarView.getSelectedDate().getDate();
    }

    public long getSelectedTime(){
        return calendarView.getSelectedDate().getDate().getTime();
    }

    public String getSelectedScheduleId(){
        return mAdapter.getIdSelectedSchedule();
    }

    public String getShiftString(){
        return mAdapter.getTextSelectedSchedule();
    }

    public String getPriceSelectedSpeciality(){
        return specialityAdapter.getPriceSelectedSpeciality();
    }

    public String getSelectedSpecialityId(){
        return specialityAdapter.getSelectedTrainerSpecialityId();
    }

    public String getSpecialityClass(){
        return specialityAdapter.getClassNameSelectedSpeciality();
    }

    public String getVoucherCode(){
        return voucherCode;
    }

    public int getDiscountValue(){
        return discountValue;
    }
}