package com.nostratech.mufit.consumer.register;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.register.RegisterRequestModel;

import java.util.concurrent.TimeUnit;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function5;
import retrofit2.Call;

class RegisterPresenter extends MyBasePresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private Observable<Boolean> emptyFieldStream;
    private Observable<Boolean> emailStream;
    private Observable<Boolean> phoneStream;
    private Observable<Boolean> passwordStream;
    private Observable<Boolean> passwordConfirmationStream;

    public RegisterPresenter(Context context, MvpView mvpView, RegisterContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void doRegister(String email, String phone, String password, String confirmationPassword, String fullname) {
        if (isConnectedToInternet()) {
            if (TextUtils.isEmpty(fullname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmationPassword)) {
                view.showValidationEmpty();
            } else if(!password.equals(confirmationPassword)){
                view.showPasswordNotMatch();
            } else {
                getMvpView().showLoading();
                Call<StandardResponseModel> call = getApiService().register(new RegisterRequestModel(email, phone, password, confirmationPassword, fullname));
                call.enqueue(new RetrofitCallback<>(this, response -> {
                    getMvpView().dismissLoading();
                    view.showSuccessRegistration();
                }));
            }
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void observeFieldEmpty(EditText textFullname, EditText textEmail, EditText textPhone, EditText textPassword, EditText textConfirmPassword) {
        emptyFieldStream = Observable.combineLatest(
                RxTextView.textChanges(textFullname)
                        .map(new Function<CharSequence, Object>() {
                            @Override
                            public Object apply(CharSequence charSequence) throws Exception {
                                return TextUtils.isEmpty(charSequence);
                            }
                        }),
                RxTextView.textChanges(textEmail)
                        .map(new Function<CharSequence, Object>() {
                            @Override
                            public Object apply(CharSequence charSequence) throws Exception {
                                return TextUtils.isEmpty(charSequence);
                            }
                        }),
                RxTextView.textChanges(textPhone)
                        .map(new Function<CharSequence, Object>() {
                            @Override
                            public Object apply(CharSequence charSequence) throws Exception {
                                return TextUtils.isEmpty(charSequence);
                            }
                        }),
                RxTextView.textChanges(textPassword)
                        .map(new Function<CharSequence, Object>() {
                            @Override
                            public Object apply(CharSequence charSequence) throws Exception {
                                return TextUtils.isEmpty(charSequence);
                            }
                        }),
                RxTextView.textChanges(textConfirmPassword)
                        .map(new Function<CharSequence, Object>() {
                            @Override
                            public Object apply(CharSequence charSequence) throws Exception {
                                return TextUtils.isEmpty(charSequence);
                            }
                        }),
                new Function5<Object, Object, Object, Object, Object, Boolean>() {

                    @Override
                    public Boolean apply(Object o, Object o2, Object o3, Object o4, Object o5) throws Exception {
                        return (Boolean) o || (Boolean) o2 || (Boolean) o3 || (Boolean) o4 || (Boolean) o5 ;
                    }
                }
        );

    }

    @Override
    public void observeEmail(EditText textEmail) {

        emailStream = RxTextView
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

    @Override
    public void observePhone(EditText textPhone) {
        phoneStream = RxTextView.textChanges(textPhone)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence) throws Exception {
                        return !(charSequence.toString().startsWith("08") && (charSequence.toString().length() >= 10));
                    }
                });


        Observer<Boolean> phoneObserver = new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d("rx", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rx", "Phone stream completed");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean phoneValid) {
                Log.d("phone", String.valueOf(phoneValid.booleanValue()));
                view.showPhoneAlert(phoneValid);
            }
        };

        phoneStream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(phoneObserver);
    }

    @Override
    public void observePassword(EditText textPassword) {
        passwordStream = RxTextView.textChanges(textPassword)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence) throws Exception {
                        String temp = charSequence.toString();
                        return !(temp.matches(".*[A-Za-z].*") && temp.matches(".*[0-9].*")
                                && (charSequence.toString().length() >= 8));
                    }
                });



        Observer<Boolean> passwordObserver = new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d("rx", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rx", "Password stream completed");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean passwordValid) {
                Log.d("Password", String.valueOf(passwordValid.booleanValue()));
                view.showPasswordAlert(passwordValid);
            }
        };

        passwordStream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(passwordObserver);
    }

    @Override
    public void observeConfirmationPassword(final EditText textPassword, final EditText textConfirmationPassword) {
        passwordConfirmationStream =
                /*RxTextView.textChanges(textConfirmationPassword)
                        .map(new Function<CharSequence, Boolean>() {
                            @Override
                            public Boolean apply(CharSequence charSequence) throws Exception {
                                return !textConfirmationPassword.toString().isEmpty() && !charSequence.toString().trim().equals(textPassword.getText().toString());
                            }
                        });*/Observable.merge(
                RxTextView.textChanges(textPassword)
                        .map(new Function<CharSequence, Boolean>() {
                            @Override
                            public Boolean apply(CharSequence charSequence) throws Exception {
                                return !charSequence.toString().trim().equals(textConfirmationPassword.getText().toString());
                            }
                        }),
                RxTextView.textChanges(textConfirmationPassword)
                        .map(new Function<CharSequence, Boolean>() {
                            @Override
                            public Boolean apply(CharSequence charSequence) throws Exception {
                                return !textConfirmationPassword.toString().isEmpty() && !charSequence.toString().trim().equals(textPassword.getText().toString());
                            }
                        })
        );



        Observer<Boolean> passwordConfirmationObserver = new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d("rx", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rx", "Password confirmation stream completed");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean passwordConfirmationDontMatch) {
                Log.d("passwordConfirmation", String.valueOf(passwordConfirmationDontMatch.booleanValue()));
                view.showPasswordConfirmationAlert(passwordConfirmationDontMatch);
            }
        };

        passwordConfirmationStream.subscribe(passwordConfirmationObserver);
    }

    @Override
    public void observeInvalidStream(EditText textFullname, EditText textEmail, EditText textPhone, EditText textPassword, EditText textConfirmPassword) {
        observeEmail(textEmail);
        observePhone(textPhone);
        observePassword(textPassword);
        observeConfirmationPassword(textPassword, textConfirmPassword);
        observeFieldEmpty(textFullname, textEmail, textPhone, textPassword, textConfirmPassword);

        Observable<Boolean> invalidFieldsStream = Observable.combineLatest(
                emailStream,
                phoneStream,
                passwordStream,
                passwordConfirmationStream,
                emptyFieldStream,
                new Function5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean emailInvalid, Boolean phoneInvalid, Boolean passwordInvalid, Boolean passwordConfirmation, Boolean emptyFieldExist) throws Exception {
                        //return emailInvalid;
                        return !emailInvalid && !phoneInvalid && !passwordInvalid && !passwordConfirmation && !emptyFieldExist;

                    }
                });

        Observer<Boolean> invalidFieldsObserver = new Observer<Boolean>() {

            @Override
            public void onError(Throwable e) {
                Log.d("rx",e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("rx","All field valid stream completed");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean invalidFieldExist) {
                Log.d("invalidFieldsStream",String.valueOf(invalidFieldExist.booleanValue()));
                view.showButtonEnabled(invalidFieldExist);
            }
        };

        //emptyFieldStream.subscribe(invalidFieldsObserver);
        invalidFieldsStream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(invalidFieldsObserver);
    }

}
