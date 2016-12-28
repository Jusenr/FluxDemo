package com.myself.fluxdemo.basic;

import com.myself.fluxdemo.retrofit.HttpHelper;

/**
 * Created by riven_chris on 16/7/4.
 */
public class BaseInteractorImpl {

    public void unSubscribe() {
        HttpHelper.getInstance().clearCallsByTag(getClass().getSimpleName());
    }

    public void onDestroy() {
        unSubscribe();
    }
}
