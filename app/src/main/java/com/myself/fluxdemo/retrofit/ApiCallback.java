package com.myself.fluxdemo.retrofit;

/**
 * Created by riven_chris on 16/7/4.
 */
public interface ApiCallback<T> {

    void onSuccess(boolean intermediate, T response);

    void onSuccessEmpty();

    void onFailed(int code, String msg);

    void onCompleted(boolean success, String msg);
}
