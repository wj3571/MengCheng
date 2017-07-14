package com.core.utils.persistence;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.mengcheng.application.MengChengApp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ToastUtil {

    public static void show(Context context, String msg) {
        show(context, msg, true);
    }

    public static void show(Context context, int resid) {
        show(context, resid, true);
    }

    public static void show(Context context, String msg, boolean isShort) {
        try {
            if (isShort) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void show(Context context, int resid, boolean isShort) {
        try {
            if (isShort) {
                Toast.makeText(context, resid, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, resid, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void show(String message) {
        Observable.just(message).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            Toast.makeText(MengChengApp.getInstanse(), s, Toast.LENGTH_SHORT).show();
        },throwable -> {
            Log.d("TAG","",throwable);
        });
    }
}
