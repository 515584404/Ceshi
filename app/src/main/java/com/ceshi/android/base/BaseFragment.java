package com.ceshi.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ceshi.android.base.util.NetWorkUtils;
import com.ceshi.android.base.view.LoadingDialog;
import com.ceshi.android.util.ErrorUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by killer on 16/1/10.
 */
public abstract class BaseFragment extends Fragment {
    public NetWorkUtils mNetWorkUtils;
    public int mScreenWidth;
    public int mScreenHeight;
    public float mDensity;
    public DisplayMetrics mDM;
    protected com.ceshi.android.base.BaseApplication mApplication;
    protected Activity mActivity;
    protected Context mContext;
    protected List<AsyncTask<Void, Void, Object>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Object>>();
    protected LoadingDialog mLoadingDialog;
    protected View mView;
    protected DisplayImageOptions mDisplayImageOptions;
    protected ImageLoader mImageLoader;
    public String mLogName;
    public ErrorUtils mErrorUtils;
    public String mFragmentName;
    public final String TAG = this.getClass().getName();

    public BaseFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mApplication = (com.ceshi.android.base.BaseApplication) getActivity().getApplication();
        mActivity = getActivity();
        mContext = getActivity();
        mLoadingDialog = new LoadingDialog(mContext, "请求提交中");
        mNetWorkUtils = new NetWorkUtils(mContext);
        mScreenWidth = ((BaseActivity) mActivity).mScreenWidth;
        mScreenHeight = ((BaseActivity) mActivity).mScreenHeight;
        mDensity = ((BaseActivity) mActivity).mDensity;
        mDM = ((BaseActivity) mActivity).mDM;
        mLogName=((BaseActivity)mActivity).mLogName;
        mFragmentName=this.getClass().getName();
        mErrorUtils=((BaseActivity) mActivity).mErrorUtils;
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ButterKnife.bind(this, mView);
        initViews();
        initEvents();
        return mView;
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    public View findViewById(int id){
        return mView.findViewById(id);
    }

    /************ 任务管理 **************/
    public void putAsyncTask(AsyncTask<Void, Void, Object> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }
    protected void clearAsyncTask() {
        Iterator<AsyncTask<Void, Void, Object>> iterator = mAsyncTasks
                .iterator();
        while (iterator.hasNext()) {
            AsyncTask<Void, Void, Object> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        mAsyncTasks.clear();
    }


    /************ Loading显示 **************/
    public void showLoadingDialog(String text){
        if (text != null) {
            mLoadingDialog.setText(text);
        }
        mLoadingDialog.show();
    }
    public void dismissLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    /************ Toast显示 **************/
    protected void showShortToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

   /* protected void showLongToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }*/


    /****************  跳转 ****************/
    public void startActivity(Class<?> cls){
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls,Bundle bundle){
        Intent intent=new Intent();
        intent.setClass(mContext,cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivity(String action){
        startActivity(action, null);
    }
    public void startActivity(String action,Bundle bundle){
        Intent intent=new Intent();
        intent.setAction(action);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        clearAsyncTask();
        mApplication.getRequestQueue().cancelAll(mFragmentName);
        super.onDestroy();
    }

}
