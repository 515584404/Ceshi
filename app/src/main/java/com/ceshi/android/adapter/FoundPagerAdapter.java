package com.ceshi.android.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ceshi.android.entity.MainPictureEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mark on 2016/11/4.
 */
public class FoundPagerAdapter extends PagerAdapter {

    public List<MainPictureEntity.LoadImagesBean> mImageUrlList;
    public Activity mActivity;

    public FoundPagerAdapter(List<MainPictureEntity.LoadImagesBean> imageUrlList, Activity activity) {
        mImageUrlList = imageUrlList;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mImageUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mActivity);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        Picasso.with(mActivity).load(mImageUrlList.get(position).getFileUrl()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
