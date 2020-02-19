package com.nostratech.mufit.consumer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TutorialManager {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context mcontext;

    private static final String PREFER_NAME = "TutorialPref";

    public static final String TUTORIAL_HOME = "TUTORIAL_HOME";

    public static final String TUTORIAL_BOOKING = "TUTORIAL_BOOKING";

    public static final String TUTORIAL_DETAIL_TRAINER_SCHEDULE = "TUTORIAL_DETAIL_TRAINER_SCHEDULE";

    public static final String TUTORIAL_BOOKING_SCHEDULE = "TUTORIAL_BOOKING_SCHEDULE";

    public static final String TUTORIAL_CALENDER_VIEW = "TUTORIAL_CALENDER_VIEW";

    public static final String TUTORIAL_DETAIL_TRAINER_PROFILE_PHOTOS = "TUTORIAL_DETAIL_TRAINER_PROFILE_PHOTOS";

    public static final String TUTORIAL_SEARCH_TRAINER = "TUTORIAL_SEARCH_TRAINER";


    public TutorialManager(Context context){
        this.mcontext = context;
        int PRIVATE_MODE = 0;
        pref = mcontext.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isTutorialHomeFinished(){
        return pref.getBoolean(TUTORIAL_HOME, false);
    }

    public void finishTutorialHome(){
        editor.putBoolean(TUTORIAL_HOME, true);
        editor.commit();
    }

    public boolean isTutorialBookingFinished(){
        return pref.getBoolean(TUTORIAL_BOOKING, false);
    }

    public void finishTutorialBooking(){
        editor.putBoolean(TUTORIAL_BOOKING, true);
        editor.commit();
    }

    public boolean isTutorialDetailTrainerScheduleFinished(){
        return pref.getBoolean(TUTORIAL_DETAIL_TRAINER_SCHEDULE,false);
    }

    public void finishTutorialDetailTrinerScedule(){
        editor.putBoolean(TUTORIAL_DETAIL_TRAINER_SCHEDULE, true);
        editor.commit();
    }

    public boolean isTutorialBookingSchedule(){
        return pref.getBoolean(TUTORIAL_BOOKING_SCHEDULE,false);
    }

    public void finishTutorialBookingSchedule(){
        editor.putBoolean(TUTORIAL_BOOKING_SCHEDULE, true);
        editor.commit();
    }

    public boolean isTutorialDetailTrainersProfilePhotosFinished(){
        return pref.getBoolean(TUTORIAL_DETAIL_TRAINER_PROFILE_PHOTOS,false);
    }

    public void finishTutorialDetailTrinerProfilePhotos(){
        editor.putBoolean(TUTORIAL_DETAIL_TRAINER_PROFILE_PHOTOS, true);
        editor.commit();
    }

    public boolean isTutorialScheduleTrainerFinished(){
        return pref.getBoolean(TUTORIAL_CALENDER_VIEW,false);
    }

    public void finishTutorialScheduleTrainer(){
        editor.putBoolean(TUTORIAL_CALENDER_VIEW, true);
        editor.commit();
    }

    public boolean isTutorialSearchTrainerFinished(){
        return pref.getBoolean(TUTORIAL_SEARCH_TRAINER,false);
    }

    public void finishTutorialSearchTrainer(){
        editor.putBoolean(TUTORIAL_SEARCH_TRAINER, true);
        editor.commit();
    }

    public void repeatTutorial(){
        editor.putBoolean(TUTORIAL_BOOKING,false);
        editor.putBoolean(TUTORIAL_HOME,false);
        editor.putBoolean(TUTORIAL_DETAIL_TRAINER_SCHEDULE, false);
        editor.putBoolean(TUTORIAL_BOOKING_SCHEDULE,false);
        editor.putBoolean(TUTORIAL_DETAIL_TRAINER_PROFILE_PHOTOS,false);
        editor.putBoolean(TUTORIAL_CALENDER_VIEW, false);
        editor.putBoolean(TUTORIAL_SEARCH_TRAINER,false);
        editor.commit();
    }
}
