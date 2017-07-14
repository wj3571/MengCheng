package com.mengcheng.application;

import android.app.Application;

/**
 * Created by wangjia on 2017/7/13.
 */

public class MengChengApp extends Application{
    private static MengChengApp instanse;
    @Override
    public void onCreate() {
        super.onCreate();
        instanse=this;
    }

    public static MengChengApp getInstanse() {
        return instanse;
    }

}

