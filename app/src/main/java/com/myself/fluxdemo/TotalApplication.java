package com.myself.fluxdemo;

import com.antfortune.freeline.FreelineCore;
import com.example.library.BasicApplication;
import com.example.library.controller.ActivityManager;
import com.example.library.utils.Logger;
import com.example.library.utils.SDCardUtils;
import com.myself.fluxdemo.api.BaseApi;

import java.io.File;

import im.fir.sdk.FIR;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/11/18 10:58.
 */
public class TotalApplication extends BasicApplication {
    public static final String TAG = TotalApplication.class.getSimpleName();
    public static final String FIR_API_TOKEN = "1b91eb3eaaea5f64ed127882014995dd";
    public static final String FIR_APP_ID = "582ee751a1de524f75000003";
    public static boolean isDebug;
    public static String sdCardPath;//SdCard路径
    private static Throwable ex;//异常信息

    @Override
    public void onCreate() {
        super.onCreate();
        isDebug = configEnvironment();
        //sdCard缓存路径
        sdCardPath = getSdCardPath();
        //Fir-SDk配置
        FIR.init(this);
        //Freeline配置
        FreelineCore.init(this);

    }

    public static String showCrash() {
        if (null != ex)
            return ex.toString();
        else
            return "无异常信息";
    }

    /**
     * debug模式
     *
     * @return 是否开启
     */
    @Override
    protected boolean configEnvironment() {
        //初始化Service Api
        BaseApi.init(BaseApi.HOST_FORMAL);
        return BaseApi.isDebug();
    }

    /**
     * 设置腾讯bugly的AppKey
     *
     * @return 腾讯bugly的AppKey
     */
    @Override
    protected String getBuglyKey() {
        return "";
    }

    /**
     * 填写工程包名
     *
     * @return 工程包名
     */
    @Override
    public String getPackageName() {
        return "com.myself.fluxdemo";
    }

    /**
     * 设置调试日志标签名
     *
     * @return 调试日志标签名
     */
    @Override
    protected String getLogTag() {
        return "Jusenr-Log";
    }

    /**
     * 设置sdCard路径
     *
     * @return sdCard路径
     */
    @Override
    protected String getSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    /**
     * 网络缓存文件夹路径
     *
     * @return 缓存文件夹路径
     */
    @Override
    protected String getNetworkCacheDirectoryPath() {
        return sdCardPath + File.separator + "http_cache";
    }

    /**
     * 网络缓存文件大小
     *
     * @return 缓存文件大小
     */
    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;
    }

    /**
     * 网络缓存最大时间
     *
     * @return 缓存最大时间
     */
    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 1000 * 60 * 60;
    }

    /**
     * 捕捉到异常就退出App
     *
     * @param ex 异常信息
     */
    @Override
    protected void onCrash(Throwable ex) {
        Logger.e("APP崩溃了,错误信息是:" + ex.getMessage());
        ex.printStackTrace();
        ActivityManager.getInstance().killProcess(getApplicationContext());
    }

    @Override
    public void singleSign(String msg) {

    }

    @Override
    public String appDeviceId() {
        return null;
    }

}
