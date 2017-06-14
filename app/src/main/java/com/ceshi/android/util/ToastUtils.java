package com.ceshi.android.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mac on 2017/5/29.
 */

public class ToastUtils {
    private static Toast toast = null;

    public static void showToast(Context context, String msg){
        if(toast == null){
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
