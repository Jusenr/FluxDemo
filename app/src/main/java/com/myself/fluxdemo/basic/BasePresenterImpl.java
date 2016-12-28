package com.myself.fluxdemo.basic;

import com.myself.fluxdemo.mvp.base.IInteractor;
import com.myself.fluxdemo.mvp.base.IView;

/**
 * Created by riven_chris on 16/7/6.
 */
public abstract class BasePresenterImpl<V extends IView, I extends IInteractor> {

    protected V mView;
    protected I mInteractor;

    public BasePresenterImpl(V view) {
        mView = view;
        mInteractor = createInteractor();
    }

    protected abstract I createInteractor();

    public void unSubscribe() {
        mInteractor.unSubscribe();
    }

    public void onDestroy() {
        if (mInteractor != null) {
            mInteractor.onDestroy();
            mInteractor = null;
        }

        mView = null;
    }
}
