package com.nostratech.mufit.consumer.forgot_password;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.ForgotPasswordRequestModel;

import java.util.concurrent.TimeUnit;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Call;

/**
 * Created by Ahmadan Ditiananda on 4/25/2018.
 */

public class ForgotPasswordPresenter extends MyBasePresenter implements ForgotPasswordContract.Presenter {

    private ForgotPasswordContract.View view;

    public ForgotPasswordPresenter(Context context,
                                   MvpView mvpView,
                                   ForgotPasswordContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void sendEmail(String email) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            if (TextUtils.isEmpty(email)) {
                view.showValidationEmpty();
            } else {
                Call<StandardResponseModel> call = getApiService().sendEmail(new ForgotPasswordRequestModel(email, "customer"));
                call.enqueue(new RetrofitCallback<>(this, response -> view.sendEmailSuccess()));
            }
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void observeEmail(EditText textEmail) {
        Observable<Boolean> emailStream = RxTextView
                .textChanges(textEmail)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence) throws Exception {
                        return !Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
                    }
                });


        Observer<Boolean> emailObserver = new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d("rx", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rx", "Email stream completed");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean emailValid) {
                Log.d("email", String.valueOf(emailValid.booleanValue()));
                view.showEmailAlert(emailValid);
            }
        };

        emailStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(emailObserver);
    }
}
