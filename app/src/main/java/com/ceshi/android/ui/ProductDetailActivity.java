package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.ui.fragment.DetailLoginFragment;
import com.ceshi.android.ui.fragment.DetailoneFragment;
import com.ceshi.android.util.FragmentInstanceManager;

import butterknife.Bind;

/**
 * 产品详情页
 * Created by ztr on 2016/04/26.
 */
public class ProductDetailActivity extends BaseActivity {
    @Bind(R.id.fl)
    FrameLayout fl;
    private DetailoneFragment detailoneFragment;
    private DetailLoginFragment detailLoginFragment;
    private FragmentTransaction ft;
    String status;
    int countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.frame_layout);
        status = getIntent().getStringExtra("status");
        countdown = getIntent().getIntExtra("countdown",0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        String sp_phone = SharePrefUtil.getString(this, SharePrefUtil.sp_phone, null);
        if(sp_phone == null){
            Log.i("sp_phone","sp_phone");
            ft = getSupportFragmentManager().beginTransaction();
            detailLoginFragment = new DetailLoginFragment(fl);
            ft.replace(R.id.fl, detailLoginFragment);
            ft.commit();
            return;
        }
//        if(mApplication.mUserPhone==null) {
//
//           ft = getSupportFragmentManager().beginTransaction();
//           detailLoginFragment = new DetailLoginFragment(fl);
//           ft.replace(R.id.fl, detailLoginFragment);
//           ft.commit();
//           return;
//       }
        switchFragment(FragmentInstanceManager.getInstance().getFragment(DetailoneFragment.class));
    }
    public void switchFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void initEvent() {
    }


    //点击spinner下拉列表选中后的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                // 取出Intent里的数据
                String mobile0 = data.getStringExtra("mobile");
                String redPacketId = data.getStringExtra("redPacketId");
                Toast.makeText(mApplication, "红包返回的电话和红包id...mobile0="+mobile0+"redPacketId="+redPacketId, Toast.LENGTH_SHORT).show();
                break;
//            case 1:
//                String mobile1 = data.getStringExtra("mobile");
//                String redPacketId = data.getStringExtra("redPacketId");
//
//                break;
//            case 2:
//                // 取出Intent里的数据
//                String mobile2 = data.getStringExtra("mobile");
//                String redPacketId = data.getStringExtra("redPacketId");
//
//                break;
        }

    }
}
