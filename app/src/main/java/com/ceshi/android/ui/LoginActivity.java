package com.ceshi.android.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.entity.ResultVoNoEntity;
import com.ceshi.android.ui.RegisterActivity;
import com.ceshi.android.ui.fragment.FragmentmineGestureLogin;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * 登录界面
 * Created by ztr on 2016/04/25.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.login_phone)
    EditText login_phone;
    @Bind(R.id.login_chicknum)
    EditText login_chicknum;
    @Bind(R.id.login_loginbtn)
    Button login_loginbtn;
    @Bind(R.id.tv_new)
    TextView tv_new;
    @Bind(R.id.ll_mima)
    LinearLayout ll_mima;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Bind(R.id.iv_forget)
    ImageView ivForget;
    @Bind(R.id.tv_forget)
    TextView tvForget;

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    //    xml中的整体View
    public ViewGroup holeView;

    private String lgname;//定义手机号
    private String mempsw;//定义密码
    private SharedPreferences sharedPreferences;//存储手机号、密码的sharedPreferences
    private MediaStore.Audio.Playlists.Members lr = new MediaStore.Audio.Playlists.Members();
    String cookie;
    private FragmentTransaction ft;
    private FragmentmineGestureLogin gestureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        widget_title.setText("登录");
        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
        holeView = (ViewGroup) contentView.getChildAt(0);
        holeView.getChildAt(0).setVisibility(View.GONE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(MainActivity.class);
                finish();*/
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });
        ll_zhuce.setOnClickListener(this);
        ll_mima.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {
        login_chicknum.setOnClickListener(this);
        login_phone.setOnClickListener(this);
//        登录按钮
        login_loginbtn.setOnClickListener(this);

    }

    //数据回显
    private void initDate() {
        sharedPreferences = this.getSharedPreferences("denglu", MODE_APPEND);
        String phone = sharedPreferences.getString("lgname", login_phone
                .getText().toString());
        login_phone.setText(phone);
        String pass = sharedPreferences.getString("mempsw", login_chicknum
                .getText().toString());
        login_chicknum.setText(pass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_loginbtn:
//                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
//                // 获取软键盘的显示状态
//                boolean isOpen = imm.isActive();
//                // 如果软键盘已经显示，则隐藏，反之则显示
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(login_chicknum, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(login_chicknum.getWindowToken(), 0); //强制隐藏键盘

//                login();
                break;
            case R.id.ll_zhuce:
                startActivity(RegisterActivity.class);
                break;
            case R.id.ll_mima:
                startActivity(com.ceshi.android.ui.ForgetPasswordPhoneActivity.class);
                break;
            default:
                break;


        }
    }

    //    点击登录
    private void login() {
        lgname = login_phone.getText().toString().trim();
        mempsw = login_chicknum.getText().toString().trim();

        RequestParams requestParams = new RequestParams(Const.Login);
        // requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("username", lgname);
        requestParams.addBodyParameter("password", mempsw);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("luobo", "登录返回数据是" + result);
                Log.i("luobo", "在LoginActivity里面获取的数据是：" + result);
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                final ResultVoNoEntity mResult = gson.fromJson(result, ResultVoNoEntity.class);
                if (mResult == null) {
                    return;
                }
                if (mResult.isResult()) {
                    cookie = "JSESSIONID=" + mResult.getJSESSIONID();
                    SharePrefUtil.saveString(LoginActivity.this, SharePrefUtil.sp_cookie, cookie);
                    mApplication.mUserCookie = cookie;

                    //登录成功,进入设置手势密码界面
                    ft = getSupportFragmentManager().beginTransaction();
                    gestureFragment = new FragmentmineGestureLogin(lgname, mempsw);
                    ft.replace(R.id.login_fragment, gestureFragment);
                    ft.commit();
                    Log.e(TAG, "initFragment: "+gestureFragment+"1111111111111111111111111");


                    //将用户号码和密码保存到本地
//                    SharePrefUtil.saveString(LoginActivity.this, SharePrefUtil.sp_phone, lgname);
//                    SharePrefUtil.saveString(LoginActivity.this, SharePrefUtil.sp_pwd, mempsw);
//                    mApplication.mUserPhone = lgname;
//                    mApplication.mUserMima = mempsw;
//                      这里加入创建手势密码，创建完成登录成功

//                    Log.d("TAG", "登录成功");
//                    showShortToast("登录成功！");
//                    startActivity(MainActivity.class);
                } else {
                    showShortToast(mResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "LoginActivity 的 login 异常" + throwable.toString());
                showShortToast("网络错误，登录失败！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络错误，登录取消！");
            }

            @Override
            public void onFinished() {

            }
        });


    }


}
