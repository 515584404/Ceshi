package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.ui.MainActivity;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/04.
 */
public class AboutusActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.wv)
    WebView abouts_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_official);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        widget_title.setText("关于我们");
        WebSettings webSettings = abouts_wv.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);// 用于设置webview放大
        webSettings.setBuiltInZoomControls(false);
        abouts_wv.loadUrl(Const.Aboutus);
        /*if (abouts_wv!=null){
            abouts_wv.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    dismissLoadingDialog();
                }
            });
            loadUrl(Const.Aboutus);
        }*/
    }

    /*private void loadUrl(String url) {

        if (abouts_wv!=null){
            abouts_wv.loadUrl(url);
            showLoadingDialog("Loading...");
            abouts_wv.reload();
        }
    }*/

    @Override
    protected void initEvent() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("backFindFragment", true);
        intent.putExtra("gestureLoginSuccess", false);
        startActivity(intent);
    }
}
