package com.ceshi.android.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ceshi.android.base.util.NetWorkUtils;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.LoadingDialog;
import com.ceshi.android.base.view.LockPatternView;
import com.ceshi.android.ui.GestureBackActivity;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.util.ErrorUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

/**
 */
public abstract class BaseActivity extends AppCompatActivity {
    //屏幕的宽度,高度,密度
    public int mScreenWidth, mScreenHeight;
    public float mDensity;
    public DisplayMetrics mDM;
    protected BaseApplication mApplication;
    protected LoadingDialog mLoadingDialog;
    public String mLogName;//Logger打印，
    public String mActivityName;//volley管理;
    protected List<AsyncTask<Void, Void, Object>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Object>>();
    public NetWorkUtils mNetWorkUtils;
    protected ImageLoader mImageLoader;
    protected DisplayImageOptions mDisplayImageOptions;
    public ErrorUtils mErrorUtils;
    private int backCount = 0;
    private final int SUCCESS_CODE = 101;
    private final int FAIL_CODE = 202;



    //    判断是否是finish结束本活动
    private boolean isFinished = false;

    //    存储是否是软件后台过
    public final String RUN_BACK = "run background";

    public PopupWindow mPopupWindow;

    public LockPatternView mLock;
    long exitTime;
    public final String TAG = this.getClass().getName();

    private Handler getmHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            showShortToast((String) msg.obj);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "执行onCreate" + this.getClass().toString());

        mApplication = (BaseApplication) getApplication();
        mApplication.addActivity(this);
        mLoadingDialog = new LoadingDialog(this, "提交请求中");
        mLoadingDialog.setDialogCancelable(false, true);
        mNetWorkUtils = new NetWorkUtils(this);
        mErrorUtils = new ErrorUtils(this);
        mDM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDM);
        mScreenWidth = mDM.widthPixels;
        mScreenHeight = mDM.heightPixels;
        mDensity = mDM.density;
        mLogName = this.getClass().getSimpleName();
        Log.d("TAG", "mLogName:" + mLogName);
        mActivityName = mLogName;
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();

        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initEvent();
    }


    @Override
    public void finish() {
        super.finish();
        isFinished = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "执行onStop" + this.getClass().toString());
        mApplication.getRequestQueue().cancelAll(mActivityName);
        if (!isFinished) {
            String packageName = "com.ceshi.android";//我们自己的应用的包名
            ActivityManager activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
            String currentTopActivity = taskInfo.get(0).topActivity.getClassName();
            if (mApplication.mUserPhone != null && currentTopActivity != null && !currentTopActivity.startsWith(packageName)) {
                //app已经后台
                SharePrefUtil.saveBoolean(this, RUN_BACK, true);
                Log.d("TAG", "已经后台了onStop" + this.getClass().toString());
            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "执行onRestart" + this.getClass().toString());

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "执行onPause" + this.getClass().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "执行onResume" + this.getClass().toString());
        String classA = getClass().toString();
        Log.d("TAG", "ClassA:" + classA);
        /*if (mApplication.mUserPhone != null && !this.getClass().toString().equals("class com.example.ztr.ui.WelcomActivity")) {
            boolean isReOepn = SharePrefUtil.getBoolean(this, RUN_BACK, false);
            if (isReOepn) {              //已经断开，这里添加手势界面判断后，进入原界面
                Log.d("TAG", "到前台来了" + this.getClass().toString());
                SharePrefUtil.saveBoolean(this, RUN_BACK, false);
                Intent intent = new Intent(this, GestureBackActivity.class);
                startActivity(intent);
                Log.d("TAG", "我已经在onResum中startActivity了");
            }
        }*/
        String lgname = SharePrefUtil.getString(this, SharePrefUtil.sp_phone, null);
        String mempsw = SharePrefUtil.getString(this, SharePrefUtil.sp_pwd, null);
        Log.i("yan","lgname="+lgname+",mempsw="+mempsw+",isReOepn="+ SharePrefUtil.getBoolean(this, RUN_BACK, false));
        if (lgname != null && mempsw != null && !this.getClass().toString().equals("class com.ceshi.android.ui.WelcomActivity")){
            boolean isReOepn = SharePrefUtil.getBoolean(this, RUN_BACK, false);
            if (isReOepn) {              //已经断开，这里添加手势界面判断后，进入原界面
                Log.d("TAG", "到前台来了" + this.getClass().toString());
                SharePrefUtil.saveBoolean(this, RUN_BACK, false);
                Intent intent = new Intent(this, GestureBackActivity.class);
                startActivity(intent);
                Log.d("TAG", "我已经在onResum中startActivity了");
            }
        }


    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected abstract void initView() throws IOException;

    protected abstract void initEvent();

    //-------------------- 任务管理 --------------------
    public void putAsyncTask(AsyncTask<Void, Void, Object> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    public void cleanAsyncTask() {
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

    //--------------------  Dialog  --------------------
    public void showLoadingDialog(String text) {
        Log.d(mLogName, "点击了loading");
        if (text != null) {
            mLoadingDialog.setText(text);
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    //--------------------  Toast --------------------
    public void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /*public void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }*/

    //-------------------- Intent跳转 --------------------
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivity(String action) {
        startActivity(action, null);
    }

    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void defaultFinish() {
        super.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "执行onDestroy" + this.getClass().toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("TAG", "keyCode:" + keyCode + "KeyEvent:" + event.toString());
        if (this instanceof MainActivity || this instanceof GestureBackActivity) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    mApplication.getRequestQueue().cancelAll(mActivityName);
                    String packageName = "com.ceshi.android";//我们自己的应用的包名
                    ActivityManager activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
                    String currentTopActivity = taskInfo.get(0).topActivity.getClassName();
                    Log.d("TAG", "后台" + this.getClass().toString());
                    if (mApplication.mUserPhone != null && currentTopActivity != null) {
                        //app已经后台
                        SharePrefUtil.saveBoolean(this, RUN_BACK, true);
                        Log.d("TAG", "已经后台了onKeyDown" + this.getClass().toString());
                    }
                    mApplication.AppExit();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
