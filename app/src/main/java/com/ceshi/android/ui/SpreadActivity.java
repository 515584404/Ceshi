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
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.adapter.SpreadViewPagerAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.ui.fragment.ShareFragment;
import com.ceshi.android.ui.fragment.SpreadFragment;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ztr on 2016/05/01.  我的推广
 */
public class SpreadActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
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
    //邀请好友 我的推广
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private SpreadViewPagerAdapter adapter;
    private static final String TAG = "RedPacketActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_award);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Log.d(TAG, "我的推广的onCreate:运行了 ");
        initFragment();
        initViewPager();
    }

    private void initFragment() {
        fragments.add(new ShareFragment()); //分享
        fragments.add(new SpreadFragment());//我的推广
    }

    private void initViewPager() {
        if (adapter == null) {
            adapter = new SpreadViewPagerAdapter(getSupportFragmentManager(), fragments);
            mViewpager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        mTabs.setupWithViewPager(mViewpager);
    }


    @Override
    protected void initView() {
        widget_title.setText("我的推广");
        ll_zhuce.setVisibility(View.GONE);
        head_share.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_back, R.id.head_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.head_share:
                share();
                break;
        }
    }

    //有盟分享应用
    private void share() {
        new ShareAction(SpreadActivity.this).withText("http://47.93.127.44/fensantouziweb")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }
    //有盟
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //有盟的回调
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //有盟分享的监听
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(SpreadActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(SpreadActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SpreadActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
