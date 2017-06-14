package com.ceshi.android.util;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static void showDialogForLoading (Activity context, String msg, boolean cancelable) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) return;
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
            if (!TextUtils.isEmpty(msg)) {
                HandyTextView loadingText = (HandyTextView) view.findViewById(R.id.loadingdialog_htv_text);
                loadingText.setText(msg);
            }
//            mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
            mLoadingDialog = new Dialog(context, R.style.Theme_Light_FullScreenDialogAct);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭加载对话框
     */
    public static void hideDialogForLoading () {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }

}
