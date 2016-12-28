package com.myself.fluxdemo.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.library.utils.Logger;

import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by riven_chris on 16/7/7.
 */
public class HttpHelper {

    private static HttpHelper instance = null;
    private ConcurrentHashMap<String, Call> callMap;
    private ConcurrentHashMap<String, Call> callMapWithTag;

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    private void putCall(Call call, String tag) {
        if (TextUtils.isEmpty(tag)) {
            if (callMap == null) {
                callMap = new ConcurrentHashMap<>();
            }
            callMap.put(call.toString(), call);
        } else {
            if (callMapWithTag == null) {
                callMapWithTag = new ConcurrentHashMap<>();
            }
            callMapWithTag.put(call.toString() + "/" + tag, call);
        }
    }

    private void removeCall(Call call, String tag) {
        if (TextUtils.isEmpty(tag)) {
            if (callMap != null) {
                callMap.remove(call.toString());
            }
        } else {
            if (callMapWithTag != null) {
                callMapWithTag.remove(call.toString() + "/" + tag);
            }
        }
    }

    public void clearCalls() {
        if (callMap != null)
            for (String key : callMap.keySet()) {
                callMap.get(key).cancel();
                callMap.remove(key);
            }
    }

    public void clearCallsByTag(String tag) {
        if (callMapWithTag != null)
            for (String key : callMapWithTag.keySet()) {
                if (key.contains("/" + tag)) {
                    callMapWithTag.get(key).cancel();
                    callMapWithTag.remove(key);
                }
            }
        clearCalls();
    }

    public <T> void newCall(Call call, @NonNull final ApiCallback<T> callback) {
        newCall(call, callback, null);
    }

    public <T> void newCall(Call call, @NonNull final ApiCallback<T> callback, final String tag) {
        putCall(call, tag);
        call.enqueue(new Callback<RetrofitBean<T>>() {
            @Override
            public void onResponse(Call<RetrofitBean<T>> call, Response<RetrofitBean<T>> response) {
                removeCall(call, tag);
                int code = response.code();
                RetrofitBean<T> bean = response.body();
                boolean success = false;
                if (bean != null)
                    success = bean.getHttp_code() == 200 || bean.getHttp_status_code() == 200
                            || (bean.getHttp_code() == 0 && bean.getHttp_status_code() == 0 && code == 200);
                if (success) {
                    T data = bean.getData();
                    if (data != null) {
                        callback.onSuccess(false, data);
                    } else {
                        callback.onSuccessEmpty();
                    }
                } else {
                    callback.onFailed(code, bean != null ? bean.getMsg() : "");
                }
                callback.onCompleted(success, bean != null ? bean.getMsg() : "");
            }

            @Override
            public void onFailure(Call<RetrofitBean<T>> call, Throwable t) {
                removeCall(call, tag);
                try {
                    callback.onFailed(-1, "");
                    callback.onCompleted(false, t.getMessage());
                    Logger.d(call.request().url().toString() + ", " + t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> void newCallForAwfulList(Call call, @NonNull final ApiCallback<T> callback) {
        newCallForAwfulList(call, callback, null);
    }

    public <T> void newCallForAwfulList(Call call, @NonNull final ApiCallback<T> callback, final String tag) {
        putCall(call, tag);
        call.enqueue(new Callback<RetrofitAwfulList<T>>() {
            @Override
            public void onResponse(Call<RetrofitAwfulList<T>> call, Response<RetrofitAwfulList<T>> response) {
                removeCall(call, tag);
                try {
                    RetrofitAwfulList<T> body = response.body();
                    int error_code = body.getError_code();
                    String msg = body.getMsg();
                    boolean success = error_code == 0;
                    if (success) {
                        T data = body.getList();
                        if (data != null) {
                            callback.onSuccess(false, data);
                        } else {
                            callback.onSuccessEmpty();
                        }
                    } else {
                        callback.onFailed(error_code, msg != null ? msg : "");
                    }
                    callback.onCompleted(success, msg != null ? msg : "");
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1, "");
                    callback.onCompleted(false, e.getMessage());
                    Logger.d(call.request().url().toString() + ", " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RetrofitAwfulList<T>> call, Throwable t) {
                removeCall(call, tag);
                try {
                    callback.onFailed(-1, "");
                    callback.onCompleted(false, t.getMessage());
                    Logger.d(call.request().url().toString() + ", " + t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T extends RetrofitAwfulBean> void newCallForAwfulBean(Call call, @NonNull final ApiCallback<T> callback) {
        newCallForAwfulBean(call, callback, null);
    }

    public <T extends RetrofitAwfulBean> void newCallForAwfulBean(Call call, @NonNull final ApiCallback<T> callback, final String tag) {
        putCall(call, tag);
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                removeCall(call, tag);
                try {
                    T body = response.body();
                    int errorCode = body.getError_code();
                    String msg = body.getMsg();
                    boolean success = errorCode == 0;
                    if (success) {
                        callback.onSuccess(false, body);
                    } else {
                        callback.onFailed(errorCode, msg != null ? msg : "");
                    }
                    callback.onCompleted(success, msg != null ? msg : "");
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(-1, "");
                    callback.onCompleted(false, e.getMessage());
                    Logger.d(call.request().url().toString() + ", " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                removeCall(call, tag);
                try {
                    callback.onFailed(-1, "");
                    callback.onCompleted(false, t.getMessage());
                    Logger.d(call.request().url().toString() + ", " + t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param observable retrofit rx callback
     * @param subscriber callback
     * @param scheduler  observer thread
     * @param <T>
     */
    public <T> void getRxResult(Observable<T> observable, Subscriber<T> subscriber, @Nullable Scheduler scheduler) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler == null ? AndroidSchedulers.mainThread() : scheduler)
                .subscribe(subscriber);
    }

    public <T> void getRxResult(Observable<T> observable, final Action1<T> action, @Nullable Scheduler scheduler) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler == null ? AndroidSchedulers.mainThread() : scheduler)
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = e.getMessage();
                    }

                    @Override
                    public void onNext(T t) {
                        action.call(t);
                    }
                });
    }
}
