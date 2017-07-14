package com.core.base;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.Disposable;

/**
 * Created by JieGuo on 2017/3/16.
 */

public interface BasePresenterView {

    AppCompatActivity getCurrentActivity();

    void addDisposable(Disposable disposable);
}
