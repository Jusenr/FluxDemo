package com.example.library.controller.http;

import com.example.library.utils.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp管理
 * Created by guchenkai on 2015/10/26.
 */
public final class OkHttpManager {
    private static final int CONNECT_TIMEOUT_MILLIS = 10 * 1000;//连接时间超时
    private static final int WRITE_TIMEOUT_MILLIS = 10 * 1000;//写入时间超时
    private static final int READ_TIMEOUT_MILLIS = 10 * 1000;//读取时间超时

    private static OkHttpManager instance;
    private static List<Interceptor> mInterceptors;

    private int mCacheSize;
    private String mCacheDirectoryPath;

    public OkHttpManager(String cacheDirectoryPath, int cacheSize) {
        mCacheDirectoryPath = cacheDirectoryPath;
        mCacheSize = cacheSize;
        mInterceptors = new LinkedList<>();
    }

    /**
     * 单例实例
     *
     * @return OkHttpHelper实例
     */
    public static OkHttpManager getInstance(String cacheDirectoryPath, int cacheSize) {
        if (instance == null)
            instance = new OkHttpManager(cacheDirectoryPath, cacheSize);
        return instance;
    }

    /**
     * 构建OkHttpClient
     *
     * @return OkHttpClient
     */
    public OkHttpClient build() {
        return generateOkHttpClient(mInterceptors);
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     * @return OkHttpHelper
     */
    public OkHttpManager addInterceptor(Interceptor interceptor) {
        mInterceptors.add(interceptor);
        return this;
    }

    /**
     * 获得OkHttp客户端
     *
     * @return OkHttp客户端
     */
    public OkHttpClient generateOkHttpClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        Cache cache = new Cache(new File(mCacheDirectoryPath, "HttpCache"),
                mCacheSize);
        builder.cache(cache);

        return builder.build();
    }
}
