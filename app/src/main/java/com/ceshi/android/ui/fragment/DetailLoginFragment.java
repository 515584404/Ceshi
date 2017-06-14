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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.entity.ResultVoNoEntity;
import com.ceshi.android.ui.ForgetPasswordPhoneActivity;
import com.ceshi.android.ui.RegisterActivity;
import com.ceshi.android.ui.fragment.DetailoneFragment;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
@SuppressLint("ValidFragment")

public class DetailLoginFragment extends BaseFragment implements View.OnClickListener {
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
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.rl_et)
    RelativeLayout rlEt;
    private FrameLayout fl;
    private String lgname;//定义手机号
    private String mempsw;//定义密码
    private SharedPreferences sharedPreferences;//存储手机号、密码的sharedPreferences
    private MediaStore.Audio.Playlists.Members lr = new MediaStore.Audio.Playlists.Members();
    String cookie;
    private DetailoneFragment detailoneFragment;
    private FragmentTransaction ft;

    public DetailLoginFragment(FrameLayout fl) {
        this.fl = fl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login_detail, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        widget_title.setText("登录");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
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
//        if (!(chickPhone() && chickPassWord())) {
//            return;
//        }
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
                Log.i("xUtilsError","DetailLoginFragment里面获取的数据是："+result);
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                final ResultVoNoEntity mResult = gson.fromJson(result, ResultVoNoEntity.class);
                if (mResult == null) {
                    return;
                }
                if (mResult.isResult()) {
                    //登录成功
                    //将用户号码和密码保存到本地
                    cookie = "JSESSIONID="+mResult.getJSESSIONID();
                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_cookie, cookie);
                    mApplication.mUserCookie = cookie;

                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_phone, lgname);
                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_pwd, mempsw);
                    mApplication.mUserPhone = lgname;
                    mApplication.mUserMima = mempsw;
                    showShortToast("登录成功！");
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    detailoneFragment = new DetailoneFragment();
                    ft.replace(R.id.fl, detailoneFragment);
                    ft.commit();
                } else {
                    showShortToast(mResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","DetailLoginFragment的login异常"+throwable.toString());
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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.showSoftInput(login_chicknum, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(login_chicknum.getWindowToken(), 0); //强制隐藏键盘
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
