package com.ceshi.android.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ztr on 2016/05/16.
 */
public class HttpNet {
    private Context context;
    private boolean connected=false;//网络没有连接
    public boolean DetectionNetworkState(){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        return connected;

    }



}
