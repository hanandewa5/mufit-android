package com.nostratech.mufit.consumer.detailtrainer;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.booking_package.BookingPackageActivity;
import com.nostratech.mufit.consumer.booking.BookingSessionActivity;
import com.nostratech.mufit.consumer.detailtrainer.profile.FragmentProfile;
import com.nostratech.mufit.consumer.detailtrainer.schedule.FragmentSchedule;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.model.DetailTrainerModel;
import com.nostratech.mufit.consumer.model.MyVoucherModel;
import com.nostratech.mufit.consumer.model.booking.BookingShiftList;
import com.nostratech.mufit.consumer.model.booking.BookingSpecialityList;
import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
import com.nostratech.mufit.consumer.utils.glidehelper.PositionedCropTransformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.mufit.core.data.AppCache;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

//TODO: Move logic into presenter
public class DetailTrainerActivity extends MyToolbarBackActivity implements DetailTrainerContract.View,
        FragmentSchedule.ToggleButtonCallback,
        FragmentSchedule.OnTutorialFinishListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout mTab;
    @BindView(R.id.tab_indicator)
    View indicator;
    @BindView(R.id.viewpaager)
    ViewPager mPager;

    //Header part
    @BindView(R.id.profile_cover)
    ImageView profileCover;
    @BindView(R.id.trainer_name)
    TextView textTrainerName;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.trainer_description)
    TextView trainerDescription;
    @BindView(R.id.trainer_rating)
    TextView trainerRating;
    @BindView(R.id.trainer_num_review)
    TextView trainerNumOfReviews;
    @BindView(R.id.progress_bar_detail)
    ProgressBar progressBarDetail;
    @BindView(R.id.view_detail_trainer)
    NestedScrollView viewDetail;
    @BindView(R.id.btn_layout)
    LinearLayout layoutButtom;
    @BindView(R.id.button_book_package)
    Button buttonBookPackage;
    @BindView(R.id.btn_booking)
    Button btnBooking;

    private DetailTrainerPresenter dtPresenter;
    private String trainerId;
    private String trainerName;

    private String discountType;

    FragmentProfile mProfile;
    FragmentSchedule mSchedule;
    TutorialManager tutorialManager;
    private int indicatorWidth;
    private ViewGroup tabLayout;

    private MyVoucherActivity.VoucherType voucherType;
    private AppCache appCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trainer);
        ButterKnife.bind(this);

        appCache = new AppCache(this);

        initFragmentsAndViewPager(savedInstanceState);
        initToolbar(toolbar);

        tutorialManager = new TutorialManager(this);

        mSchedule.setListener(this);

        dtPresenter = new DetailTrainerPresenter(this, this, this);
        dtPresenter.getTrainerProfile(trainerId);
    }

    @Override
    public void setProfile(DetailTrainerModel model) {
        //Set trainer Cover picture
        String trainerCover = model.getCoverPicTrainer(); //TODO: add cover pic dari API baru

        if (isValidContextForGlide(this)) {
            GlideApp.with(this)
                    .load(trainerCover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new PositionedCropTransformation(1, 0))
                    .error(R.color.preload_image_gray)
                    .into(profileCover);
        }

        //Set trainer profile picture
        String trainerPhoto = model.getUrl_photo_trainer();

        ImageView imageView = findViewById(R.id.profile_image);
        if (isValidContextForGlide(this)) {
            GlideApp.with(this).load(trainerPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new PositionedCropTransformation(1, 0))
                    .into(imageView);
        }

        //Set name
        String name = model.getName();
        textTrainerName.setText(name);
        this.trainerName = name;

        //Set rating
        double rating = model.getRating();
        trainerRating.setText(String.valueOf(rating));

        //Set number of reviews
        //TODO: load from model once API supports showing num of reviews
        int numOfReviews = 0;
        if (numOfReviews != 0) {
            trainerNumOfReviews.setText(String.valueOf(numOfReviews));
        } else {
            trainerNumOfReviews.setText(String.valueOf(model.getReviews()));
        }

        //Set description
        String description = model.getDescription();
        if(description == null) description = "No description";
        trainerDescription.setText(description);

        //Pass the data to child fragments
        mProfile.displayImagesAndCertificates(model);
//        mSchedule.setListOfTrainerSpecialityPackage(model.getTrainerSpecialityPackage());
    }

    @Override
    public void navigateToBookingPackage() {
        Intent intent = new Intent(this, BookingPackageActivity.class);
        intent.putExtra(BookingPackageActivity.EXTRA_TRAINER_ID, trainerId);
        intent.putExtra(Constants.IntentExtras.TRAINER_NAME, trainerName);
        showActivity(intent);
    }

    @OnClick({R.id.btn_booking, R.id.button_book_package})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_booking:
                if (appCache.isUserLoggedIn()) {

                    Date selectedDate = mSchedule.getSelectedDate();
                    long selectedTime = mSchedule.getSelectedTime();
                    String selectedScheduleId = mSchedule.getSelectedScheduleId() ;
                    String dayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(selectedDate);
                    String shiftString = mSchedule.getShiftString();
                    String priceSelectedSpeciality =  mSchedule.getPriceSelectedSpeciality();
                    String specilaityClass = mSchedule.getSpecialityClass();
                    String selectedSpecialityId = mSchedule.getSelectedSpecialityId();
                    String voucherCode = mSchedule.getVoucherCode();
                    int discountValue = mSchedule.getDiscountValue();

                    if (selectedScheduleId == null) {
                        showGenericError(getResources().getString(R.string.error_detail_trainer_schedule));
                        break;
                    }

                    Bundle data = new Bundle();

                    //todo: fristy new booking step
                    data.putString("type", "normal");
                    data.putString("trainerId", trainerId);
                    data.putLong("timeStamp", selectedTime);
                    DateFormat dateFormat1 = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
                    String dateFormatString = dateFormat1.format(selectedDate);
                    data.putString("date", dateFormatString);
                    data.putString("shift", shiftString);
                    data.putString("class", specilaityClass);
                    if (priceSelectedSpeciality != null)
                        data.putInt("totalPrice", Integer.parseInt(priceSelectedSpeciality.replace(".", "")));
                    else {
                        showGenericError(getResources().getString(R.string.error_detail_trainer_speciality));
                        break;
                    }
                    data.putBoolean("joinShifts", false); //todo: fristy berhubung cuma di batein 1 class ini di false dulu aja
                    List<BookingShiftList> bookingShiftList = new ArrayList<>();
                    bookingShiftList.add(new BookingShiftList(selectedScheduleId));
                    data.putParcelableArrayList("bookingShift", (ArrayList<? extends Parcelable>) bookingShiftList);
                    List<BookingSpecialityList> bookingSpecialityList = new ArrayList<>();
                    bookingSpecialityList.add(new BookingSpecialityList(selectedSpecialityId, 1)); //todo: harcode to 1 because for now just only can select 1 class
                    data.putParcelableArrayList("bookingSpeciality", (ArrayList<? extends Parcelable>) bookingSpecialityList);

                    //Pass voucher code and discount value to BookingActivity2
                    data.putSerializable(Constants.IntentExtras.VOUCHER_TYPE, voucherType);
                    data.putString(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
                    data.putInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, discountValue);
                    data.putString(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, discountType);
                    data.putString(Constants.IntentExtras.TRAINER_NAME, this.trainerName);

                    Intent intent = new Intent(this, BookingSessionActivity.class);
                    intent.putExtras(data);
                    startActivity(intent, ActivityOptions.makeCustomAnimation(this, R.anim.pull_in_right, R.anim.push_out_left).toBundle());
                } else {
                    Intent i = new Intent(this, LoginActivity.class);
                    i.putExtra(LoginActivity.FLAG, LoginActivity.BEHAVIOR_UNSPECIFIED);
                    showActivity(i);
                }
                break;
            case R.id.button_book_package:
                dtPresenter.onBookingPackageClick();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        getSupportFragmentManager()
                .putFragment(outState, FragmentProfile.class.getName(), mProfile);
        getSupportFragmentManager()
                .putFragment(outState, FragmentSchedule.class.getName(), mSchedule);
    }

    private void initFragmentsAndViewPager(Bundle savedInstanceState) {
        //Initialize new fragments if there does not exist a savedInstanceSate
        if(savedInstanceState == null){

            //Retrieve data needed to initialize fragments
            Bundle data = getIntent().getExtras();

            //Get trainerId from previous activity, must not be null
            trainerId = data.getString(Constants.IntentExtras.TRAINER_ID, null);
            Objects.requireNonNull(trainerId, "trainerId from IntentExtras is null. trainerId is needed to fetch trainer's schedule");

            //By default there is no voucher code used
            String voucherCode = null;
            int discountValue = 0;
            String specialityId = null;

            this.voucherType =
                    (MyVoucherActivity.VoucherType)data.getSerializable(Constants.IntentExtras.VOUCHER_TYPE);

            //If non-null, then DetailTrainerActivity is opened from MyVoucherActivity
            //then Obtain voucher code
            if(voucherType != null) {
                switch (voucherType) {
                    case VOUCHER_CODE:
                        voucherCode = data.getString(Constants.IntentExtras.VOUCHER_CODE, null);
                        discountValue = data.getInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);
                        discountType = data.getString(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, null);
                        break;
                    case VOUCHER_PACKAGE:
                        MyVoucherModel model = data.getParcelable(Constants.IntentExtras.VOUCHER_MODEL);
                        voucherCode = model.getCode();
                        discountValue = model.getValue();
                        specialityId = model.getTrainerSpecialityId();
                        break;
                }
            }

            //Create new FragmentSchedule and pass trainerId, voucherCode and discountValue
            mSchedule = FragmentSchedule.newInstance(trainerId, voucherCode, discountValue, specialityId);

            //Create new FragmentProfile
            mProfile = FragmentProfile.newInstance();
        }

        //If there exists a savedInstanceState, retrieve previous fragment instances
        else {
            mSchedule = (FragmentSchedule) getSupportFragmentManager().getFragment(
                    savedInstanceState, FragmentSchedule.class.getName());
            mProfile = (FragmentProfile) getSupportFragmentManager().getFragment(
                    savedInstanceState, FragmentProfile.class.getName());
        }

        mSchedule.setCallback(this);

        mProfile.setTempId(trainerId);

        //Populate ViewPager with fragments
        DetailTrainerFragmentAdapter adapter = new DetailTrainerFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(mSchedule, getString(R.string.schedule));
        adapter.addFragment(mProfile, getString(R.string.profile));
        mPager.setAdapter(adapter);

        //Assign TabLayout to use ViewPager's ScrollListener
        mTab.setupWithViewPager(mPager);

        //Once views are inflated, get indicator width which is equal to each tab in TabLayout's width
        mTab.post(() -> {
            indicatorWidth = mTab.getWidth() / mTab.getTabCount();
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            indicator.setLayoutParams(indicatorParams);
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)indicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+i) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                indicator.setLayoutParams(params);
            }


            //Kalo mau lakukan sesuatu ketika pagenya berubah (di scroll / dipencet)
            @Override
            public void onPageSelected(int i) {
                //Forces viewpager to recalculate its height when view changes
                mPager.requestLayout();

                toggleButtonVisibility(i);


                //When a view calls requestLayout, it will also get focused
                //By calling request focus on the root view, this prevents the whole activity from scrolling to bottom whenever
                //tab is changed.
                findViewById(android.R.id.content).requestFocus();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void toggleButtonVisibility(int i) {
        if (i == 0){
            layoutButtom.setVisibility(View.VISIBLE);
        }else {
            layoutButtom.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        progressBarDetail.setVisibility(View.VISIBLE);
        viewDetail.setVisibility(View.INVISIBLE);
        layoutButtom.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        progressBarDetail.setVisibility(View.INVISIBLE);
        viewDetail.setVisibility(View.VISIBLE);
        layoutButtom.setVisibility(View.VISIBLE);
        showTutorialForTabs();

    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        showGenericError(getString(R.string.no_internet));
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }
    public void showTutorialForTabs() {
        if (tutorialManager.isTutorialDetailTrainerScheduleFinished()) return;

        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));

        View tabSchedule = ((ViewGroup) mTab.getChildAt(0)).getChildAt(0);
        tb.addSequenceItem(tabSchedule,
                getString(R.string.next),
                getString(R.string.schedule_tabs_trainer_showcase),
                null);

        View profile = ((ViewGroup) mTab.getChildAt(0)).getChildAt(1);
        tb.addSequenceItem(profile,
                getString(R.string.next),
                getString(R.string.profile_tabs_trainer_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        mSchedule.showTutorialCalendar();
                    }
                }
        );
        tb.start();
    }


    @Override
    public void toggleButtonBooking(boolean enable) {
        btnBooking.setEnabled(enable);
    }

    @Override
    public void toggleButtonPackage(boolean enable) {
        buttonBookPackage.setEnabled(enable);

    }

    @Override
    public void onTutorialButton() {
        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));

        tb.addSequenceItem(btnBooking,
                getString(R.string.next),
                getString(R.string.button_booking_showcase),
                null);

        tb.addSequenceItem(buttonBookPackage,
                getString(R.string.finish_showcase),
                getString(R.string.button_booking_package_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        tutorialManager.finishTutorialDetailTrinerScedule();
                    }
                });
        tb.start();
    }
}
