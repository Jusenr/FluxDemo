package com.myself.fluxdemo.basic;

/**
 * Created by riven_chris on 16/7/7.
 */
public interface BaseCallback<T> {

    void refreshData(T data);

    void loadMoreData(T data);

    void dataNoMore();

    void showSuccessToast(String msg);

    void showErrorToast(String msg);

    void showEmptyView();

    void showErrorView();

    void complete();
}
