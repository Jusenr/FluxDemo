package com.myself.fluxdemo.api;

/**
 * Created by xiaopeng on 16/7/11.
 * create at 16/7/11 下午1:51
 */

public class BaseApi {
    public static final int HOST_FORMAL = 1;//正式环境
    public static final int HOST_DEV = 2;//开发环境
    public static final int HOST_TEST = 3;//测试环境

    public static int HOST_NOW;//当前环境
    /*所有的BASE URL*/
    public static String WEIDU_BASE_URL = "";

    /**
     * environment: 1，外网 2，开发内网，3，测试内网
     */
    public static void init(int environment) {
        HOST_NOW = environment;
        switch (environment) {
            case 1:
                WEIDU_BASE_URL = "http://api-weidu.putao.com/";

                break;
            case 2:
                WEIDU_BASE_URL = "http://api-weidu.ptdev.cn/";

                break;
            case 3:
                WEIDU_BASE_URL = "http://api-weidu-test.ptdev.cn/";

                break;
        }
    }

    public static boolean isDebug() {
        return HOST_NOW == HOST_DEV || HOST_NOW == HOST_TEST;
    }
}
