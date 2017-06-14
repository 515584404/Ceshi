package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.entity.ResultVoNoEntity;
import com.ceshi.android.ui.ForgetPasswordPhoneActivity;
import com.ceshi.android.ui.RegisterActivity;
import com.ceshi.android.util.PhoneFormatCheckUtils;
import com.google.gson.Gson;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2016/5/17.
 */
@SuppressLint("ValidFragment")
public class FragmentmineLogin extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.login_phone)
    EditText login_phone;
    @Bind(R.id.login_chicknum)
    EditText login_chicknum;
    @Bind(R.id.login_loginbtn)
    Button login_loginbtn;
    @Bind(R.id.ll_mima)
    LinearLayout ll_mima;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Bind(R.id.iv_shezhi)
    ImageView iv_shezhi;
    @Bind(R.id.widget_title)
    TextView widgetTitle;
    private FrameLayout fl;
    private String lgname;//定义手机号
    private String mempsw;//定义密码
    private SharedPreferences sharedPreferences;//存储手机号、密码的sharedPreferences
    private MediaStore.Audio.Playlists.Members lr = new MediaStore.Audio.Playlists.Members();
    String cookie;
    private FragmentmineGestureLogin gestureFragment;
    private FragmentTransaction ft;

    public FragmentmineLogin(FrameLayout fl) {
        this.fl = fl;
    }

    public FragmentmineLogin() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        widgetTitle.setText("爱生息");
        iv_shezhi.setVisibility(View.GONE);
        Log.d(TAG, "FragmentmineLogin里面的initViews()执行了");
    }

    @Override
    public void onStart() {
        login_phone.setText("");
        login_chicknum.setText("");
        super.onStart();
    }

    @Override
    protected void initEvents() {
        login_chicknum.setOnClickListener(this);
        login_phone.setOnClickListener(this);
        login_loginbtn.setOnClickListener(this);
        ll_zhuce.setOnClickListener(this);
        ll_mima.setOnClickListener(this);
    }

    private void login() {
        lgname = login_phone.getText().toString().trim();
        mempsw = login_chicknum.getText().toString().trim();
        boolean isTruePhone = PhoneFormatCheckUtils.isChinaPhoneLegal(lgname);
        if (!isTruePhone) {
            Toast.makeText(mApplication, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mempsw.length() < 6) {
            Toast.makeText(mApplication, "请输入(6 - 20)位字母数字组合！", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams requestParams = new RequestParams(Const.Login);
        //  requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("username", lgname);
        requestParams.addBodyParameter("password", mempsw);
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {

            private FragmentmineGestureLogin fragmentmineGestureLogin;

            @Override
            public void onSuccess(String result) {
                Log.i("luobo", "在Fragmentmineogin里面获取的数据是：" + result);
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
                    Log.d("TAG", "登陆成功口1");
                    cookie = "JSESSIONID=" + mResult.getJSESSIONID();
                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_cookie, cookie);
                    mApplication.mUserCookie = cookie;
                    Log.i("luobo", "登录之后的cookie是" + mApplication.mUserCookie);

                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fl, new FragmentmineGestureLogin(lgname,mempsw));
                    ft.commit();
//                    List<Activity> activityList = mApplication.getActivityList();
//                    for (Activity activity : activityList) {
//                        if (activity instanceof MainActivity) {
//                            fragmentmineGestureLogin = (FragmentmineGestureLogin) ((MainActivity) activity).mBaseFragment.get(4);
////                            fragmentmineGestureLogin.setNamePwd(lgname, mempsw);
//                            break;
//                        }
//                    }
//                    gestureFragment = new FragmentmineGestureLogin(lgname, mempsw);
                    Log.e(TAG, "登陆成功 跳转到手势界面  ");
//                    ft.replace(R.id.fl, fragmentmineGestureLogin);
//                    ft.commit();
                } else {
                    showShortToast(mResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "FragmentmineLogin 的 login 异常" + throwable.toString());
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_loginbtn:
                login();
                break;
            case R.id.ll_zhuce:
                startActivity(RegisterActivity.class);
                break;
            case R.id.ll_mima:
                startActivity(ForgetPasswordPhoneActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
