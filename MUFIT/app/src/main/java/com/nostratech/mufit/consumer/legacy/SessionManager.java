package com.nostratech.mufit.consumer.legacy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.login.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    private OnLogoutListener listener;

    // Shared Preferences reference
    private SharedPreferences pref;

    // Editor reference for Shared preferences
    private Editor editor;

    // Context
    private Context _context;

    // Sharedpref file name
    private static final String PREFER_NAME = "SessionPref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // token (make variable public to access from outside)
    public static final String KEY_ACCESS_TOKEN = "accessToken";

    // fullname (make variable public to access from outside)
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_EMAIL = "email";

    // role (make variable public to access from outside)
    public static final String KEY_CLIENT_ID = "clientId";
    public static final String KEY_PHONE = "phone";

    // Constructor
    public SessionManager(Context context, OnLogoutListener listener) {
        this._context = context;
        this.listener = listener;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String accessToken, String email,
                                       String fullname, String clientId, String phone) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Store email address
        editor.putString(KEY_EMAIL, email);

        // Storing access toke  in pref
        editor.putString(KEY_ACCESS_TOKEN, accessToken);

        // Storing fullname in pref
        editor.putString(KEY_FULLNAME, fullname);

        // Storing clientId in pref
        editor.putString(KEY_CLIENT_ID, clientId);

        editor.putString(KEY_PHONE, phone);

        // commit changes
        editor.commit();
    }

//    /**
//     * Check login method will check user login status
//     * If false it will redirect user to login page
//     * Else do anything
//     * */
//    public boolean checkLogin(){
//        // Check login status
//        if(!this.isUserLoggedIn()){
//
//            // user is not logged in redirect him to splash Activity
//            Intent i = new Intent(_context, SplashActivity.class);
//
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//
//            return true;
//        }
//        return false;
//    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin2(int flag) {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to splash Activity
            Intent i = new Intent(_context, LoginActivity.class);
            i.putExtra(LoginActivity.FLAG, flag);

            // Staring Login Activity
            _context.startActivity(i);
            ((Activity) _context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            return true;
        }
        return false;
    }

//    /**
//     * Check login method will check user login status
//     * If false it will redirect user to login page
//     * Else do anything
//     * */
//    public boolean checkLoginFromDetailTrainer(int flag, String trainerId){
//        // Check login status
//        if(!this.isUserLoggedIn()){
//
//            // user is not logged in redirect him to splash Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            i.putExtra("flagActivity", flag);
//            i.putExtra(Constants.INTENT_EXTRA_TRAINER_ID, trainerId);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//            ((Activity) _context).finish();
//            ((Activity) _context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//
//            return true;
//        }
//        return false;
//    }

//    public boolean checkLoginFromDetailTrainer(int flag, String eventId, String eventImgUrl){
//        // Check login status
//        if(!this.isUserLoggedIn()){
//
//            // user is not logged in redirect him to splash Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            i.putExtra("flagActivity", flag);
//            i.putExtra("event_id", eventId);
//            i.putExtra("event_image", eventImgUrl);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//            ((Activity) _context).finish();
//            ((Activity) _context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//
//            return true;
//        }
//        return false;
//    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user token
        user.put(KEY_ACCESS_TOKEN, pref.getString(KEY_ACCESS_TOKEN, null));

        // user fullname id
        user.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        // user fullname id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user role id
        user.put(KEY_CLIENT_ID, pref.getString(KEY_CLIENT_ID, null));

        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        listener.onLogout();

    }


    // Check for login
    private boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    // Check for login
    public boolean isUserLogged() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


    //update user email after login
    public void updateEmailUser(String email) {
        // Storing login value as TRUE
        // Storing fullname in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void updateUserPhone(String phone) {
        editor.putString(KEY_PHONE, phone);

        editor.commit();
    }

    public void updateUserFullName(String fullName) {
        editor.putString(KEY_FULLNAME, fullName);

        editor.commit();
    }

    public String getAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getFullName() {
        return pref.getString(KEY_FULLNAME, null);
    }
    
    public interface OnLogoutListener {
        void onLogout();
    }
}
