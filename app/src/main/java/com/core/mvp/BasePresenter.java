package com.core.mvp;

import com.core.base.BasePresenterView;

/**
 * Created by JieGuo on 2017/3/16.
 */

public abstract class BasePresenter {

    protected static final int PAGE_SIZE = 10;

    protected String TAG = this.getClass().getSimpleName();

    protected BasePresenterView view;

    public BasePresenter(BasePresenterView view) {
        this.view = view;
    }
}
