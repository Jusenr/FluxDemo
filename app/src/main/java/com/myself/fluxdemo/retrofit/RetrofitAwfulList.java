package com.myself.fluxdemo.retrofit;

/**
 * Created by riven_chris on 16/7/14.
 */
public class RetrofitAwfulList<T> {
    private int error_code;
    private T list;
    private String msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
