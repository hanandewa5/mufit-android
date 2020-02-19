package com.nostratech.mufit.consumer.base;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public abstract class BaseMufitPresenter {

    private List<Call> retrofitCalls;

    public BaseMufitPresenter(){
        this.retrofitCalls = new ArrayList<>();
    }

    protected void addCall(Call c){
        retrofitCalls.add(c);
    }

    protected void removeCall(Call c){
        retrofitCalls.remove(c);
    }

    public void cancelRequests(){
        for(Call c : retrofitCalls){
            c.cancel();
        }
    }
}
