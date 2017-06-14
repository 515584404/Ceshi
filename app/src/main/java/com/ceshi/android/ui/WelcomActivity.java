package com.ceshi.android.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.ResultVoNoEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/17.
 */
public class WelcomActivity extends BaseActivity {
    @Bind(R.id.iv_img)
    ImageView iv_img;
    String mima;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initEvent() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (mApplication.mUserPhone == null) {
                    startActivity(com.ceshi.android.ui.MainActivity.class);
                } else {
                    System.out.println("loginlogin");
                    login();
                    startActivity(com.ceshi.android.ui.MainActivity.class);
                }
                finish();
            }
        }, 1200);

    }

    private void login() {
        phone = mApplication.mUserPhone;
        mima = mApplication.mUserMima;
        RequestParams requestParams = new RequestParams(Const.Login);
//        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("username", phone);
        requestParams.addBodyParameter("password", mima);
        requestParams.setUseCookie(false);
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                final ResultVoNoEntity mResult = gson.fromJson(result, ResultVoNoEntity.class);
                if (mResult == null) {
                    return;
                }
                Log.d(TAG, "登录成功...");
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","WelcomActivity 的 login 异常"+throwable.toString());
                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
