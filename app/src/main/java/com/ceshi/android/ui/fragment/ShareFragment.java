package com.ceshi.android.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.ui.WebViewActivity;
import com.ceshi.android.util.CreateQRCode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by chen on 2017/6/12
 */

public class ShareFragment extends BaseFragment {

    @Bind(R.id.share_iv_qrcode)
    ImageView shareIvQrcode;
    @Bind(R.id.share_tv_sharelink)
    TextView shareTvSharelink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        Create2QR2("http://47.93.127.44/fensantouziweb", shareIvQrcode);
    }

    @Override
    protected void initEvents() {

    }

    private void Create2QR2(String urls, ImageView imageView) {
        String uri = urls;
        int mScreenWidth = 0;
        Bitmap bitmap;
        try {
            /**
             * 获取屏幕信息的区别
             * 只有activity可以使用WindowManager否则应该使用Context.getResources().getDisplayMetrics()来获取。
             * Context.getResources().getDisplayMetrics()依赖于手机系统，获取到的是系统的屏幕信息；
             * WindowManager.getDefaultDisplay().getMetrics(dm)是获取到Activity的实际屏幕信息。
             */
            DisplayMetrics dm = new DisplayMetrics();
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels / 2;

            bitmap = CreateQRCode.createQRImage(uri, mScreenWidth,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo_new));//自己写的方法

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.share_tv_sharelink)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("pageUrl","http://47.93.127.44/fensantouziweb");
        startActivity(intent);
    }
}
