package com.nostratech.mufit.consumer.muhealth.checklist;

import java.io.Serializable;

public class DailyQuest implements Serializable {

    private final int imageResId;

    private final boolean done;

    public DailyQuest(int imageResId, boolean done) {
        this.imageResId = imageResId;
        this.done = done;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isDone() {
        return done;
    }
}
