package com.ceshi.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发现界面跳转到意见反馈界面
 */
public class FindIdeaActivity extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.widget_title)
    TextView widgetTitle;
    @Bind(R.id.ll_zhuce)
    LinearLayout llZhuce;
    @Bind(R.id.findidea_et_phone)
    EditText findideaEtPhone;
    @Bind(R.id.findidea_btn_back)
    Button findideaBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_idea);
        ButterKnife.bind(this);

        llZhuce.setVisibility(View.GONE);
        widgetTitle.setText("意见反馈");
        Toast.makeText(this, "跳转到意见反馈界面", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.findidea_btn_back)
    public void onViewClicked() {
        Toast.makeText(this, "返回以发送，我们会尽快恢复您...", Toast.LENGTH_SHORT).show();
    }
}
