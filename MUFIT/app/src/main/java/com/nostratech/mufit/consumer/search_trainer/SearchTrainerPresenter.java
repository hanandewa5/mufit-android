package com.nostratech.mufit.consumer.search_trainer;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.model.SearchTrainerModel;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;
import com.nostratech.mufit.consumer.utils.Constants;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.PaginatedResponseModel;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

/**
 * Created by Ahmadan Ditiananda on 4/26/2018.
 */

public class SearchTrainerPresenter extends MyBasePresenter implements SearchTrainerContract.Presenter {
    private SearchTrainerContract.View view;
    private List<SearchTrainerModel> searchTrainerModels = new ArrayList<>();
    private List<HomeSpecialityListModel> modelsSpeciality = new ArrayList<>();

    private static final String TIME_END = "23:59:00";
    private static final String TIME_START = "00:00:00";
    private final int limit = 25;
    private int counterPage = 0;
    private SearchQuery lastQuery;

    //Filters
    private Calendar calendarForSearch;
    private int day, month, year;
    private String specialityFilter;
    private String genderFilter;
    private String filterTrainerName = "";

    //Vouchers
    private String voucherDiscountType;
    private String voucherCode;
    private int discountValue;

    private Timer timer = new Timer();
    private final long DELAY = 500; // milliseconds

    public SearchTrainerPresenter(Context context, MvpView mvpView, SearchTrainerContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void initializeData(Bundle data) {
        calendarForSearch = Calendar.getInstance();
        day = 0;
        month = 0;
        year = 0;

        if (data != null) {
            specialityFilter = data.getString(ListTrainerConstants.EXTRA_SPECIALITY_FILTER, null);
            genderFilter = data.getString(ListTrainerConstants.EXTRA_GENDER_FILTER, null);

            if (specialityFilter != null) view.setPredeterminedFilter(specialityFilter);

            long dateFromSubCategory = data.getLong(ListTrainerConstants.EXTRA_SELECTED_DATE, 0);
            if (dateFromSubCategory != 0) {
                Date selectedDate = new Date(dateFromSubCategory);
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
                String selectedDateFormatted = dateFormat.format(selectedDate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);

                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                view.showDateText(selectedDateFormatted);
            }

            voucherDiscountType = data.getString(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, null);
            voucherCode = data.getString(Constants.IntentExtras.VOUCHER_CODE, null);
            discountValue = data.getInt(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, 0);

            //If no voucher code was passed it will be null
            //else, show the code
            if (voucherCode != null) view.showActiveVoucher(voucherCode);

//            if ("F".equalsIgnoreCase(genderFilter)) {
//                view.toggleButtonWoman(true);
//            } else if ("M".equalsIgnoreCase(genderFilter)) {
//                view.toggleButtonMan(true);
//            }

            updateFilterSpecialityGender();

        }

        runSearch();
        //Initialize list of speciality for filter
        getAllSpeciality();
    }

    @Override
    public void runSearch() {
        getAllTrainersWithFilter(filterTrainerName, genderFilter, specialityFilter, getSelectedDateDayName(), getSelectedDateEpochStr());
    }

    @Override
    public void refreshResults() {
        counterPage = 0;
        view.clearSearchResults();
        runSearch();
    }

    private void updateFilterSpecialityGender() {
        if (specialityFilter == null && genderFilter == null) {
            view.hideFilters();
            view.removeFilterGender();
            view.removeFilterSpeciality();
            return;
        }

        view.showFilters();

        if (specialityFilter != null) {
            view.updateFilterSpeciality(specialityFilter);
        } else {
            view.removeFilterSpeciality();
        }

        if (genderFilter != null) {
            view.updateFilterGender(genderFilter);
        } else {
            view.removeFilterGender();
        }
    }

    /**
     * Used to query for trainers with different search parameters
     *
     * @param name       - trainer name parameter
     * @param gender     - trainer gender parameter (M or F)
     * @param speciality - trainer speciality parameter
     * @param day        - name of the day to be queried (i.e. Monday)
     * @param bookDate   - epoch of the selected date
     */
    @Override
    public void getAllTrainersWithFilter(String name, String gender, String speciality, String day, String bookDate) {
        if (isConnectedToInternet()) {

            compareCurrentAndLastQuery(name, gender, speciality, day, bookDate);

            if (counterPage == 0) getMvpView().showLoading();
            else view.showInfiniteScrollLoading();

            Call<PaginatedResponseModel> call =
                    getApiService().getListTrainerWithFilter(getAccessToken(), name, speciality, gender, day, TIME_START, TIME_END, bookDate, counterPage, limit);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<SearchTrainerModel>>() {
                }.getType();
                searchTrainerModels = getGson().fromJson(jsonArray, dataType);

                if (counterPage == 0) getMvpView().dismissLoading();
                else view.dismissInfiniteScrollLoading();

                boolean endOfPage = response.body().getPages() == ++counterPage;

                view.showListTrainer(searchTrainerModels, endOfPage);
            }));
        }
    }

    /**
     * Comparison is made to determine when to reset page counter.
     * If the query has changed, boolean sameQuery will return false,
     * which indicates we need to reset the page counter before making a HTTP request to our API
     */
    private void compareCurrentAndLastQuery(String name, String gender, String speciality, String day, String bookDate) {
        SearchQuery query = new SearchQuery(name, gender, speciality, day, bookDate);

        if (lastQuery != null) {
            boolean sameQuery = query.equals(lastQuery);
            //If user changes search parameters, clear the trainer list and reset pagination
            if (!sameQuery) {
                resetPagination();
                view.clearSearchResults();
            }
        }

        lastQuery = query;
    }

    @Override
    public void resetPagination() {
        counterPage = 0;
    }

    /**
     * Get all specialities offered by MUFIT; used for search advanced filters
     */
    @Override
    public void getAllSpeciality() {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getListSpecialityForHome(getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<HomeSpecialityListModel>>() {
                }.getType();
                modelsSpeciality = getGson().fromJson(jsonArray, dataType);
                view.showFilterSpecialityList(modelsSpeciality);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void onFilterNameChanged(String trainerName) {

        if (trainerName == null || trainerName.equalsIgnoreCase(filterTrainerName)) return;

        filterTrainerName = trainerName.isEmpty() ? null : trainerName;

        //Run search after DELAY seconds
        //If user changed his query within DELAY seconds, reset timer
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runSearch();
                    }
                },
                DELAY
        );
    }

    @Override
    public void onFilterGenderReset() {
        genderFilter = null;
        updateFilterSpecialityGender();
        runSearch();
    }

    @Override
    public void onFilterSpecialityReset() {
        specialityFilter = null;
        //Dismiss filter
        updateFilterSpecialityGender();
        runSearch();
    }

    @Override
    public void onFilterDateReset() {
        day = 0;
        month = 0;
        year = 0;
        view.removeFilterDate();
        runSearch();
    }

    @Override
    public void onFilterDateChanged(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        Date date = new Date(getSelectedDateEpoch());
        //e.g. Monday, 19 January 2019
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());

        view.showDateText(dateFormat.format(date));

        runSearch();
    }

    @Override
    public void onFilterGenderSpecialityChanged(boolean male, boolean female, String speciality) {
        if (male && female) genderFilter = null;
        else if (male) genderFilter = "M";
        else if (female) genderFilter = "F";
        else genderFilter = null;

        specialityFilter = speciality;
        updateFilterSpecialityGender();
        runSearch();
    }

    @Override
    public void onVoucherRemoved() {
        voucherCode = null;
        view.removeActiveVoucher();
    }

    @Override
    public void requestNavigationToDetailTrainer(String trainerId) {
        if (getmAppCache().isUserLoggedIn()) {
            view.navigateToDetailTrainer(trainerId, voucherCode, voucherDiscountType, discountValue);
        } else {
            view.navigateToLogin(LoginActivity.OPEN_DETAIL_TRAINER);
        }
    }

    @Override
    public void requestNavigationToPackage(String trainerId, String trainerName) {
        if (getmAppCache().isUserLoggedIn()) {
            view.navigateToPackage(trainerId, trainerName);
        } else {
            //TODO: add case for package
            view.navigateToLogin(LoginActivity.OPEN_LIST_TRAINER);
        }
    }

    /**
     * Temporary solution to get last selected date
     *
     * @return an array with length of 3, containing year, month and day last selected by user
     */
    @Override
    public int[] getLastSelectedDate() {
        return new int[]{day, month, year};
    }


    private String getSelectedDateDayName() {
        if (day == 0 && month == 0 && year == 0) return null;

        String dateString = String.format(Locale.ENGLISH, "%d-%d-%d", year, month + 1, day);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Then get the day of week from the Date based on specific locale.


        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);


    }


    private String getSelectedDateEpochStr() {
        long epoch = getSelectedDateEpoch();
        return epoch == 0 ? null : String.valueOf(epoch);
    }

    private long getSelectedDateEpoch() {

        if (day == 0 && month == 0 && year == 0) {
            //Do nothing
            return 0;
        } else {
            calendarForSearch.set(Calendar.DAY_OF_MONTH, day);
            calendarForSearch.set(Calendar.MONTH, month);
            calendarForSearch.set(Calendar.YEAR, year);

            return calendarForSearch.getTimeInMillis();
        }
    }

//    /**
//     * Get all packages offered by trainers; used to enable/disable button "Book Package"
//     */
//    @Override
//    public void getAllPackage() {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()){
//            Call<StandardResponseModel> call = getApiService().getAllPackage(getAccessToken());
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
//                Type dataType = new TypeToken<Collection<ListPackageModelOld>>(){}.getType();
//                listPackageModelOld = getGson().fromJson(jsonArray, dataType);
//                view.doShowListAllPackage(listPackageModelOld);
//            }));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
}
