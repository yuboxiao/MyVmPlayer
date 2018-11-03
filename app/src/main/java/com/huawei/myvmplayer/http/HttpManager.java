package com.huawei.myvmplayer.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.myvmplayer.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by x00378851 on 2018/8/22.
 * 以后如果要修改网络框架不用okhttp了，可以直接修改此文件
 * 替换掉okhttpclient即可
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    private static HttpManager sHttpManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private HttpManager() {
        mOkHttpClient = new OkHttpClient();

        mOkHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式获取httpManager
     *
     * @return
     */
    public static HttpManager getHttpManager() {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                }
            }
        }

        return sHttpManager;
    }

    /**
     * 发起 get 请求
     *
     * @param url
     */
    public void get(String url, BaseCallBack baseCallBack) {
        // 创建请求参数
        Request request = new Request.Builder().url(url).build();
        // 发起异步的请求
        doRequest(request, baseCallBack);
    }

    private void doRequest(final Request request, final BaseCallBack baseCallBack) {
        //创建一个请求对象
        Call call = mOkHttpClient.newCall(request);
        //发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                LogUtils.e(TAG, "====>onFailure result: " + e.getMessage());
                sendFailResultCallBack(e, baseCallBack);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                LogUtils.e(TAG, "====>onResponse result: " + result);
                sendSuccessfulCallBack(response, result, baseCallBack);
            }
        });
    }

    private void sendSuccessfulCallBack(final Response response, final String result, final BaseCallBack baseCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (baseCallBack.type == String.class) {
                    baseCallBack.onSuccess(result);
                } else {

                    try {
                        //代表请求成功，并且成功请求到数据
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            Object obj = gson.fromJson(result, baseCallBack.type);
                            baseCallBack.onSuccess(obj);
                        } else {
                            baseCallBack.onFailure(response.code(), new RuntimeException("====>server error!!!"));
                        }
                    } catch (JsonSyntaxException e) {
                        baseCallBack.onFailure(response.code(), e);
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void sendFailResultCallBack(final IOException e, final BaseCallBack baseCallBack) {
        if (baseCallBack == null) {
            LogUtils.e(TAG, "baseCallBack is null!!!");
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG, "====>onFailure result: " + e.toString());
                baseCallBack.onFailure(-1, e);
            }
        });
    }
}
