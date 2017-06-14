package com.ceshi.android.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @since 2016-01-10 13:58:14
 * @author killer
 * @version 1.0
 */
public class NetWorkUtils {

    private Context mContext;
    public NetworkInfo.State wifiState = null;
    public NetworkInfo.State mobileState = null;

    public NetWorkUtils(Context context) {
        mContext = context;
    }

    public enum NetWorkState {
        WIFI, MOBILE, NONE;

    }

    /**
     * 获取当前的网络状态
     *
     * @return
     */
    public NetWorkState getConnectState() {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.getActiveNetworkInfo();
        wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            return NetWorkState.MOBILE;
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            return NetWorkState.NONE;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            return NetWorkState.WIFI;
        }
        return NetWorkState.NONE;
    }
}
