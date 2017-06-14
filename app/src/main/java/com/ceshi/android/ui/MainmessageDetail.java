package com.ceshi.android.ui;

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
 * Created by Administrator on 2016/5/12.  首页公告详情
 */
public class MainmessageDetail extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
   /* @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_time)
    TextView tv_time;*/
    @Bind(R.id.wv_content)
    WebView wv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_message_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        /*Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        String time=intent.getStringExtra("time");
        tv_title.setText(title);
       // tv_content.setText(content);
        tv_content.loadData(content, "text/html; charset=UTF-8", "UTF-8");
        tv_time.setText(time);*/
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        widget_title.setText("公告消息");
        WebSettings webSettings = wv_content.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);// 用于设置webview放大
        webSettings.setBuiltInZoomControls(false);
        wv_content.loadUrl(Const.MainMessageDetail);
        /*if (wv_content!=null){
            wv_content.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    dismissLoadingDialog();
                }
            });
            loadUrl(Const.MainMessageDetail);
        }*/
//        wv_content.loadUrl(Const.MainMessageDetail);
    }

    /*private void loadUrl(String url) {
        if (wv_content!=null){
            showLoadingDialog("Loading...");
            wv_content.loadUrl(url);
            wv_content.reload();
        }

    }*/

    @Override
    protected void initEvent() {

    }
}
