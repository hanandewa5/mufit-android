package com.nostratech.mufit.consumer.search_trainer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

class TrainerNameWatcher implements TextWatcher {


    private Timer timer=new Timer();
    private final long DELAY = 500; // milliseconds
    private View btnRemoveFilter;
    private OnTimerExpiredListener listener;

    private String previousString;

    TrainerNameWatcher(View btnRemoveFilter, OnTimerExpiredListener listener){
        this.btnRemoveFilter = btnRemoveFilter;
        this.listener = listener;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            btnRemoveFilter.setVisibility(View.VISIBLE);
        } else {
            btnRemoveFilter.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //afterTextChanged is called when user presses the actionIME button on their keyboard,
        //To prevent re-running the same search, current String is checked if it really changed

        if(previousString != null && previousString.equals(s.toString())) return;

        previousString = s.toString();
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        listener.onTimerExpired();
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                    }
                },
                DELAY
        );

    }

    interface OnTimerExpiredListener{
        void onTimerExpired();
    }
}
