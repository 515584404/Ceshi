package com.ceshi.android.util;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient单例类
 * Created by Mark on 2016/10/29.
 */
public class OkHttpClientUtil {

    private static OkHttpClient  okHttpClient;

    public static synchronized OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
            okHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
            okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        }
        return okHttpClient;
    }
}
