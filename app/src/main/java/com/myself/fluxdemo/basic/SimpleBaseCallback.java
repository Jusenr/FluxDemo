package com.myself.fluxdemo.basic;

/**
 * Created by riven_chris on 16/7/7.
 */
public class SimpleBaseCallback<T> implements BaseCallback<T> {

    @Override
    public void refreshData(T data) {

    }

    @Override
    public void loadMoreData(T data) {

    }

    @Override
    public void showSuccessToast(String msg) {

    }

    @Override
    public void showErrorToast(String msg) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void dataNoMore() {

    }
}
