package com.ceshi.android.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistAgreementActivity extends BaseActivity {


    private static final String TAG = "RegistAgreementActivity";
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.wv_content)
    WebView wv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_agreement);
        ButterKnife.bind(this);
        Log.e(TAG, "注册页面的注册协议点击跳转");
        widget_title.setText("注册协议");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wv_content.loadUrl(Const.RegistAgreement);
    }

    @Override
    protected void initView() {
        Toast.makeText(mApplication, "注册协议的initView:", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "注册协议的initView: ");
//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        widget_title.setText("注册协议");
//        WebSettings webSettings = wv_content.getSettings();
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(false);// 用于设置webview放大
//        webSettings.setBuiltInZoomControls(false);
//        wv_content.loadUrl(Const.RegistAgreement);
    }

    @Override
    protected void initEvent() {

    }

}
