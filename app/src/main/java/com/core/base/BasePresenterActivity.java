package com.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import com.core.base.eventbus.EventBusDelegate;
import com.core.base.eventbus.IEventBus;


import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.disposables.Disposable;

/**
 * base presenter activity
 *
 * Created by JieGuo on 2017/3/17.
 */

public abstract class BasePresenterActivity extends AppCompatActivity implements BasePresenterView {

    protected final String TAG = this.getClass().getSimpleName();

    private Queue<Disposable> disposableQueue;
    public TextView right;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposableQueue = new LinkedList<>();
        if (this instanceof IEventBus) {
            EventBusDelegate.register(this);
        }
    }



    @Override
    public AppCompatActivity getCurrentActivity() {
        return this;
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposableQueue.add(disposable);
    }


}
