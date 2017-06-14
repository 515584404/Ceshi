package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/04/28.
 */
public class IntroduceActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.chanpin_wv)
    WebView wv;
    String productType;
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jieshao);
        Intent intent = getIntent();
        productType = intent.getStringExtra("productType");
        productId = intent.getIntExtra("productId", -1);
        Log.d("TAG", "IntroduceActivity里面的productId：" + productId);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("产品介绍");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);// 用于设置webview放大
        webSettings.setBuiltInZoomControls(false);
    }

    @Override
    protected void initEvent() {
        RequestParams requestParams = new RequestParams(Const.productIntroduce);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("productId", productId + "");

        showLoadingDialog("数据提交中...");  // 显示数据正在提交中
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                wv.loadData(result,"text/html;charset=UTF-8",null);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","IntroduceActivity 的 initEvent 异常"+throwable.toString());
                showShortToast("充值网络请求失败！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("充值网络请求取消！");
            }

            @Override
            public void onFinished() {
                dismissLoadingDialog();
            }
        });

    }
}
