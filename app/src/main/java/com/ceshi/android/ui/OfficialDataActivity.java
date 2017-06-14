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

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/02.官方动态 item_official
 */
public class OfficialDataActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.wv)
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_official);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        String pageUrl=intent.getStringExtra("pageUrl");
        widget_title.setText("官方动态");
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
        wv.loadUrl(Const.Announcements);
       /* if (wv!=null){
            wv.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    dismissLoadingDialog();
                }
            });
            loadUrl(Const.Announcements);
        }*/
    }

    /*private void loadUrl(String url) {
        if (wv!=null){
            wv.loadUrl(url);
            showLoadingDialog("Loading...");
            wv.reload();
        }
    }
*/

    @Override
    protected void initEvent() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, com.ceshi.android.ui.MainActivity.class);
        intent.putExtra("backFindFragment", true);
        intent.putExtra("gestureLoginSuccess", false);
        startActivity(intent);
    }
}
