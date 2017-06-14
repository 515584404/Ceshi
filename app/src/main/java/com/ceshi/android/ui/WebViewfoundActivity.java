package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/16.
 */
public class WebViewfoundActivity extends BaseActivity {
    @Bind(R.id.wv)
    WebView wv;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String pageUrl = intent.getStringExtra("pageUrl");
        System.out.println("pppppuuuuu" + pageUrl);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);// 用于设置webview放大
        webSettings.setBuiltInZoomControls(false);
        wv.loadUrl(pageUrl);
    }

    @Override
    protected void initEvent() {

    }
}
