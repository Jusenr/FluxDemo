package com.myself.fluxdemo.mvp.base;

/**
 * Created by riven_chris on 16/6/30.
 */
public interface IInteractor {
    void unSubscribe();

    void onDestroy();
}
