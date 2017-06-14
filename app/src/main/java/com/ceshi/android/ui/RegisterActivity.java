package com.ceshi.android.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.ui.fragment.FragmentRegisterone;

import butterknife.Bind;

/** 注册界面
 * Created by ztr on 2016/04/27.
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.fl)
    FrameLayout fl;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    public boolean backFromThree = false;

    private FragmentTransaction ft;
    private FragmentRegisterone fragmentRegisterone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        widget_title.setText("注册");
        ll_zhuce.setVisibility(View.GONE);
        ft = getSupportFragmentManager().beginTransaction();
        fragmentRegisterone = new FragmentRegisterone(fl,iv_back);
        ft.replace(R.id.fl, fragmentRegisterone);
        ft.commit();
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initEvent() {

    }

}
