package com.myself.fluxdemo.mvp.base;

/**
 * Created by riven_chris on 16/6/30.
 */
public interface IPresenter {

    void subscribe();

    void unSubscribe();

    void onDestroy();
}
