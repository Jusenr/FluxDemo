package com.myself.fluxdemo.retrofit;

/**
 * Created by riven_chris on 16/7/4.
 */
public class RetrofitBean<T> {
    private T data;
    private int http_code;//纬度后台接口状态码
    private int http_status_code;//日历后台接口状态码
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getHttp_status_code() {
        return http_status_code;
    }

    public void setHttp_status_code(int http_status_code) {
        this.http_status_code = http_status_code;
    }
}
