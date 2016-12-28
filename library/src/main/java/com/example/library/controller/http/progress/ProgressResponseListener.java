package com.example.library.controller.http.progress;

/**
 * 响应体进度回调接口，比如用于文件下载中
 * Created by guchenkai on 2015/10/26.
 */
public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
