package com.example.library.controller.http.interceptor;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * reponse信息拦截器
 * Created by guchenkai on 2016/1/22.
 */
public class ResponseInfoInterceptor implements Interceptor {
    private static final String TAG = ResponseInfoInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Response response = chain.proceed(request);
        Headers responseHeaders = response.headers();
      return response;
    }
}
