package com.nostratech.mufit.consumer.muhealth.onboarding;

class OnboardingContract {

    interface View {
        void showErrorDataEmpty();

        void showOnboardingSuccess();
    }

    interface Presenter {
        void submitData(String height, String weight);
    }

}
