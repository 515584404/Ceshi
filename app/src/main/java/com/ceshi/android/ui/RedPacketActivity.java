package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.adapter.RedPacketViewPagerAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.ui.fragment.ExperienceFragment;
import com.ceshi.android.ui.fragment.JiaInterestFragment;
import com.ceshi.android.ui.fragment.RedPacketFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedPacketActivity extends BaseActivity {

    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Bind(R.id.head_share)
    ImageView head_share;
    @Bind(R.id.redpacket_tl_tabs)
    TabLayout mTabs;
    @Bind(R.id.redpacket_vp_viewpager)
    ViewPager mViewpager;
    //红包 体验金 加息券
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private RedPacketViewPagerAdapter adapter;
    private static final String TAG = "RedPacketActivity";
    public String mobile; // 电话号码
    private boolean isRedpacket; //红包
    private boolean isExperience;//体验金
    private boolean isJiaInterest;//加息券


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_award);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
//        isRedpacket = intent.getBooleanExtra("redpacket", false);
//        isExperience = intent.getBooleanExtra("experience", false);
//        isJiaInterest = intent.getBooleanExtra("jiaInterest", false);

        widget_title.setText("我的奖励");
        ll_zhuce.setVisibility(View.GONE);
        Log.d(TAG, "红包 体验金 加息券的onCreate:运行了 " + "从我的界面传递过来的电话号码" + mobile);
//        Toast.makeText(this, "我的奖励", Toast.LENGTH_SHORT).show();
        initView();
        initFragment();
        initViewPager();
//        switchFragment();
    }

    private void switchFragment() {
//        if(isRedpacket){//红包
//            mViewpager.setCurrentItem(0);
//        }else if(isExperience){ // 体验金
//            mViewpager.setCurrentItem(1);
//        }else if(isJiaInterest){// 加息券
//            mViewpager.setCurrentItem(2);
//        }
    }

    private void initFragment() {
        fragments.add(new ExperienceFragment());//体验金
        fragments.add(new RedPacketFragment()); //红包
        fragments.add(new JiaInterestFragment());//加息券
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    private void initViewPager() {
        mViewpager.setOffscreenPageLimit(3);
        if (adapter == null) {
            adapter = new RedPacketViewPagerAdapter(getSupportFragmentManager(), fragments);
            mViewpager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        mTabs.setupWithViewPager(mViewpager);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
