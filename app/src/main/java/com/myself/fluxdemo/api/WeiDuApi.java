package com.myself.fluxdemo.api;


import com.myself.fluxdemo.retrofit.RetrofitBean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by luowentao on 2016/8/1.
 */
public interface WeiDuApi {

    /**
     * 解除绑定
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("service/bindDel")
    Call<RetrofitBean<List<JSONObject>>> removeBinding(@FieldMap Map<String, String> map);
}
