package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.BitmapFromURL;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.RegistFinishEntity;
import com.ceshi.android.ui.RegistAgreementActivity;
import com.ceshi.android.ui.fragment.FragmentRegistertwo;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/27.
 */
@SuppressLint("ValidFragment")

public class FragmentRegisterthree extends BaseFragment {
    @Bind(R.id.et_shez_mima)
    EditText et_shez_mima;
    @Bind(R.id.et_qr_mima)
    EditText et_qr_mima;
    @Bind(R.id.et_tuijian)
    EditText et_tuijian;
    @Bind(R.id.ly_zc_xy)
    TextView ly_zc_xy;
    @Bind(R.id.btn_finish)
    Button btn_finish;
    private FrameLayout fl;
    private ImageView iv_back;
    private FragmentTransaction ft;
    private FragmentRegistertwo fragmentRegistertwo;
    private String phone;
    private String yzma;

    private String localCookieStr;
    String returnMessage;

    public FragmentRegisterthree(ImageView iv_back, FrameLayout fl, String phone, String yzma, String jessionId) {
        this.iv_back = iv_back;
        this.fl = fl;
        this.phone = phone;
        this.yzma = yzma;
        this.localCookieStr = jessionId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register_three, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ft = getActivity().getSupportFragmentManager().beginTransaction();
////                fragmentRegistertwo = new FragmentRegistertwo(fl, iv_back, null);
////                ft.replace(R.id.fl, fragmentRegistertwo);
////                ft.commit();
//                FragmentRegisterone fragmentRegisterone = new FragmentRegisterone(fl, iv_back);
//                ft.replace(R.id.fl, fragmentRegisterone);
//                ft.commit();
                getActivity().finish();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                点击注册按钮，完成注册
//                showShortToast("完成！");
                zhuceData();
            }
        });
        ly_zc_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //注册协议

                startActivity(RegistAgreementActivity.class);
            }
        });
    }

    private void zhuceData() {
        String password = et_shez_mima.getText().toString();
        String qrphonw = et_qr_mima.getText().toString();
        String tjphone = et_tuijian.getText().toString();
        if (password.equals("")) {
            showShortToast("密码不能为空！");
            return;
        }
        if (qrphonw.equals("")) {
            showShortToast("确认密码不能为空！");
            return;
        }
        if (!password.equals(qrphonw)) {
            showShortToast("确认密码失败！");
            return;
        }

        localCookieStr = "JSESSIONID=" + BitmapFromURL.getJsessionId();
        RequestParams requestParams = new RequestParams(Const.RegisterFinish);
        requestParams.addHeader("cookie",localCookieStr);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("username", phone);
        requestParams.addBodyParameter("password", password);
        requestParams.addBodyParameter("recommenderMobile", tjphone);
        requestParams.addBodyParameter("mobileCode", yzma);
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                RegistFinishEntity entity = gson.fromJson(result, RegistFinishEntity.class);
                Log.d("TAG", "" + entity.isResult());
                returnMessage = entity.getMessage();
                if (entity.isResult()) {
                    showShortToast(returnMessage);
                    getActivity().finish();
                } else {
                    if (returnMessage != null) {
                        showShortToast(returnMessage);
                    } else {
                        showShortToast("注册失败");
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showShortToast("网络异常，请连接网络！");
                Log.i("xUtilsError","FragmentRegisterthree 的 zhuceData 异常"+throwable.toString());
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
    protected void initEvents() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
