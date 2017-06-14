package com.ceshi.android.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainSafeDetailActivity extends BaseActivity {

    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Bind(R.id.mainsafedetail_wv)
    WebView mainsafedetail_wv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_safe_detail);
        ButterKnife.bind(this);

        ll_zhuce.setVisibility(View.GONE);
        widget_title.setText("多重保障");
        WebSettings webSettings = mainsafedetail_wv.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);// 用于设置webview放大
        webSettings.setBuiltInZoomControls(false);
        mainsafedetail_wv.loadUrl(Const.HelpCenter);
    }

    @Override
    protected void initView() throws IOException {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
