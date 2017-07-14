package com.core.utils.persistence;


import android.text.TextUtils;

import com.mengcheng.application.MengChengApp;


/**
 * Created by JieGuo on 2017/3/16.
 */

public class FastData extends Remember {

    protected static Remember remember = null;

    private static final String HEADER_TOKEN = "login::header::token";
    private static final String HEADER_IS_LOGIN = "login::check::is:login";
    private static final String SHARED_PREFS_NAME = "tticar.persistence";
    private static final String SHARED_USER_NAME = "login::user::name";
    private static final String SHARED_USER_PHONE = "login::user::phone";
    private static final String SHARED_USER_NAME_PHONE = "login::user::namephone";
    private static final String SHARED_USER_TOKEN_TYPE = "login::user::token::type";
    private static final String SHARED_USER_TOKEN_EXPIRES_TIME = "login::user::token::expires::time";
    private static final String SHARED_USER_IS_AUDIT = "login::user::is::audit";
    private static final String USER_IS_SUB_ACCOUNT = "login::user::is::sub::account";
    private static final String MACHINE_STATE = "machineState";
    private static final String MACHINE_SEX = "machineSex";
    private static final String MACHINE_NAME = "machineName";
    private static final String MACHINE_PHOTO_PATH = "machinePhotoPath";
    private static final String STOREID = "storeId";

    public static synchronized Remember getInstance() {
        if (remember == null) {
            remember = init(MengChengApp.getInstanse(), SHARED_PREFS_NAME);
        }
        return remember;
    }

    public static void setToken(String token) {
        putString(HEADER_TOKEN, token);
    }

    public static String getToken() {
        return getString(HEADER_TOKEN, "");
    }

    public static void putLogin(boolean token) {
        setIsLogin(token);
    }

    public static boolean getLogin() {
        return isLogin();
    }

    public static void setPhone(String phone) {
        putString(SHARED_USER_PHONE, phone);
    }

    public static String getPhone() {
        return getString(SHARED_USER_PHONE, "");
    }

    //存储子账号登陆后的电话号码，与主账号进行区分
    public static void setUserNamePhone(String userNamephone) {
        putString(SHARED_USER_NAME_PHONE, userNamephone);
    }

    public static String getUserNamePhone() {
        return getString(SHARED_USER_NAME_PHONE, "");
    }

    public static void setIsLogin(boolean isLogin) {
        putBoolean(HEADER_IS_LOGIN, isLogin);
    }

    public static boolean isLogin() {
        return getBoolean(HEADER_IS_LOGIN, false);
    }


    public static void setUsername(String username) {
        putString(SHARED_USER_NAME, username);
    }

    public static String getUsername() {
        return getString(SHARED_USER_NAME, "");
    }

    public static void setTokenType(String type) {
        putString(SHARED_USER_TOKEN_TYPE, type);
    }

    public static String getTokenType() {
        return getString(SHARED_USER_TOKEN_TYPE, "");
    }

    public static void setExpires(String expires) {
        putString(SHARED_USER_TOKEN_EXPIRES_TIME, expires);
    }

    public static String getExpires() {
        return getString(SHARED_USER_TOKEN_EXPIRES_TIME, "");
    }

    public static void setIsAudit(boolean isAudit) {
        putBoolean(SHARED_USER_IS_AUDIT, isAudit);
    }

    public static boolean getIsAudit() {
        return getBoolean(SHARED_USER_IS_AUDIT, false);
    }

    public static void setUserIsSubAccount(boolean isSubAccount) {
        putBoolean(USER_IS_SUB_ACCOUNT, isSubAccount);
    }

    public static boolean isDisplayManageSubAccountIcon() {
        return getBoolean(USER_IS_SUB_ACCOUNT, false);
    }

    public static void setMachineState(String state) {
        putString(MACHINE_STATE, state);
    }
    public static String getMachineState() {
        return getString(MACHINE_STATE, "2");
    }

    public static void setMachineSex(String sex) {
        putString(MACHINE_SEX, sex);
    }
    public static String getMachineSex() {
        return getString(MACHINE_SEX, "0");
    }

    public static void setMachineName(String name) {
        putString(MACHINE_NAME, name);
    }
    public static String getMachineName() {
        return getString(MACHINE_NAME, "");
    }
    public static void setMachinePhotoPath(String path) {
        putString(MACHINE_PHOTO_PATH, path);
    }
    public static String getMachinePhotoPath() {
        return getString(MACHINE_PHOTO_PATH, "");
    }

    public static void setStoreId(String id) {
        putString(STOREID, id);
    }
    public static String getStoreId() {
        return getString(STOREID, "");
    }

}
