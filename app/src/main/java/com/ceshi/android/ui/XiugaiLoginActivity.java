package com.ceshi.android.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.model.ResultVoNoData;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/02.
 */
//app/user/account/updatePassword
public class XiugaiLoginActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.et_ys_mima)
    EditText et_ys_mima;
    @Bind(R.id.et_login_mima)
    EditText et_login_mima;
    @Bind(R.id.et_queren_mima)
    EditText et_queren_mima;
    @Bind(R.id.login_loginbtn)
    Button login_loginbtn;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xiugai_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("修改登录密码");
        ll_zhuce.setVisibility(View.GONE);
        login_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePassword();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updatePassword() {
        String oldPassword = et_ys_mima.getText().toString();
        String newPassword = et_login_mima.getText().toString();
        String qrPassword = et_queren_mima.getText().toString();

        if (oldPassword.equals("")) {
            showShortToast("请输入原始密码！");
            return;
        }
        if (newPassword.equals("")) {
            showShortToast("请设置新密码！");
            return;
        }
        if (qrPassword.equals("")) {
            showShortToast("请确认密码！");
            return;
        }
        if (!newPassword.equals(qrPassword)) {
            showShortToast("确认密码失败，请重新输入！");
            return;
        }
        RequestParams requestParams = new RequestParams(Const.UpdataePassword);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("oldPassword", oldPassword);
        requestParams.addBodyParameter("newPassword", newPassword);
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                if(mResult==null)
                {
                    return;
                }
                if (mResult.isResult()) {
                    showShortToast(mResult.getMessage());
                    finish();
                } else {
                    showShortToast(mResult.getMessage());
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","XiugaiLoginActivity 的 updatePassword 异常"+throwable.toString());
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
    protected void initEvent() {

    }

}
