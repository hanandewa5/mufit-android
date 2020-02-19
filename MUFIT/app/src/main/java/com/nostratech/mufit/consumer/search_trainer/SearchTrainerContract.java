package com.nostratech.mufit.consumer.search_trainer;

import android.os.Bundle;

import com.nostratech.mufit.consumer.model.SearchTrainerModel;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 4/26/2018.
 */

public interface SearchTrainerContract {

    interface View {
        void showListTrainer(List<SearchTrainerModel> searchTrainerModels, boolean endOfPage);

        void showFilterSpecialityList(List<HomeSpecialityListModel> homeSpecialityListModels);

        void clearSearchResults();

        void showInfiniteScrollLoading();

        void dismissInfiniteScrollLoading();

        void showDateText(String date);

        void showActiveVoucher(String voucherCode);

        void removeActiveVoucher();

        void toggleButtonWoman(boolean checked);

        void toggleButtonMan(boolean checked);

        void hideFilters();

        void showFilters();

        void setPredeterminedFilter(String speciality);

        void updateFilterSpeciality(String speciality);

        void removeFilterSpeciality();

        void updateFilterGender(String gender);

        void removeFilterGender();

        void removeFilterDate();

        void navigateToDetailTrainer(String trainerId, String voucherCode, String voucherDiscountType, int discountValue);

        void navigateToPackage(String trainerId, String trainerName);

        void navigateToLogin(int flag);
    }

    interface Presenter {
        void initializeData(Bundle data);

        void runSearch();

        void refreshResults();

        void getAllTrainersWithFilter(String name, String gender, String speciality, String day, String bookDate);

        void resetPagination();

        void getAllSpeciality();

        void onFilterGenderReset();

        void onFilterSpecialityReset();

        void onFilterDateReset();

        void onFilterDateChanged(int year, int month, int day);

        void onFilterNameChanged(String trainerName);

        void onFilterGenderSpecialityChanged(boolean male, boolean female, String speciality);

        void onVoucherRemoved();

        void requestNavigationToDetailTrainer(String trainerId);

        void requestNavigationToPackage(String trainerId, String trainerName);

        //TODO: Find an elegant solution. This is a lazy solution
        int[] getLastSelectedDate();
    }
}
