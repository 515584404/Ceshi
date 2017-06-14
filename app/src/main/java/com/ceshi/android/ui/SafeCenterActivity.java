package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.SafeEntity;
import com.ceshi.android.model.ResultVoNoData;
import com.ceshi.android.ui.TranPasswordActivity;
import com.ceshi.android.ui.XiugaiLoginActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/01.
 */
public class SafeCenterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.tv_renz)
    HandyTextView tv_renz;
    @Bind(R.id.rl_renz)
    RelativeLayout rl_renz;
    @Bind(R.id.tv_bd_two)
    HandyTextView tv_bd_two;
    @Bind(R.id.rl_bd_ka)
    RelativeLayout rl_bd_ka;
    @Bind(R.id.rl_mima)
    RelativeLayout rl_mima;
    @Bind(R.id.rl_jyimia)
    RelativeLayout rl_jyimia;
    @Bind(R.id.rl_shoushi)
    RelativeLayout rl_shoushi;
    @Bind(R.id.btn_tuichu)
    Button btn_tuichu;
    @Bind(R.id.rl_phone)
    RelativeLayout rl_phone;
    @Bind(R.id.tv_phone_ban)
    TextView phoneNumber;
    private String phone;
    private boolean hasBind;
    private boolean hasIdenty;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_safe);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        ll_zhuce.setVisibility(View.GONE);
        phone = intent.getStringExtra("phone");
        widget_title.setText("安全中心");
        btn_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoff();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        safeData();
    }

    //退出登录
    private void signoff() {
        RequestParams requestParams = new RequestParams(Const.signoff);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        Log.i("xUtilsError","退出登录");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("xUtilsError","退出登录结果是"+result);
                if (result == null) {
                    showShortToast("数据为空");
                    return;
                }
                Gson gson = new Gson();
                ResultVoNoData mResutlt = gson.fromJson(result, ResultVoNoData.class);
                if (mResutlt.isResult()) {
                    showShortToast(mResutlt.getMessage());
                    SharePrefUtil.saveString(SafeCenterActivity.this, SharePrefUtil.sp_phone, null);
                    SharePrefUtil.saveString(SafeCenterActivity.this, SharePrefUtil.sp_pwd, null);
                    mApplication.mUserPhone = null;
                  //  mApplication.mUserCookie = null;
                    Intent intent = new Intent(SafeCenterActivity.this, com.ceshi.android.ui.MainActivity.class);
                    intent.putExtra("quitSuccess", true);
                    startActivity(intent);
                    finish();
                } else {
                    showShortToast(mResutlt.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","SafeCenterActivity 的 signoff 异常"+throwable.toString());
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

    private void safeData() {
        RequestParams requestParams = new RequestParams(Const.SafeCenter);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null) {
                    showShortToast("数据为空");
                    return;
                }
                Gson gson = new Gson();
                final SafeEntity mResult = gson.fromJson(result, SafeEntity.class);

                if (mResult == null) {
                    return;
                }
                //实名认证
                phoneNumber.post(new Runnable() {
                    @Override
                    public void run() {
                        phoneNumber.setText(mResult.getMobile());
                        if (mResult.isIsSetIdentification() == true) {
                            tv_renz.setText(mResult.getIdentification());
                            hasIdenty = true;
                        } else {
                            tv_renz.setText("进行认证");
                        }

                        if (mResult.isIsBindBankCard() == true) {
                            tv_bd_two.setText(mResult.getBankCard());
                            hasBind = true;
                        } else {
                            tv_bd_two.setText("进行绑定");
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","SafeCenterActivity 的 safeData 异常"+throwable.toString());
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
        iv_back.setOnClickListener(this);
//        rl_zhanghu.setOnClickListener(this);
        rl_renz.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
        rl_bd_ka.setOnClickListener(this);
        rl_mima.setOnClickListener(this);
        rl_jyimia.setOnClickListener(this);
        rl_shoushi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
//            case R.id.rl_zhanghu:
//                showShortToast("已绑定");
//                break;
            case R.id.rl_renz:  //身份认证
                if (hasIdenty) {
                    showShortToast("已认证");
                    return;
                }
                startActivity(com.ceshi.android.ui.AutonymActivity.class);
                break;
            case R.id.rl_bd_ka: //绑定银行卡
                if (hasBind) {
                    showShortToast("已绑定");
                    return;
                }
                Intent intent = new Intent(SafeCenterActivity.this, com.ceshi.android.ui.BindCardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.rl_jyimia:  // 交易密码
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                startActivity(TranPasswordActivity.class, bundle);
                break;
//              手势密码界面
            case R.id.rl_shoushi:
                Intent intent1 = new Intent(this, com.ceshi.android.ui.GestureModifyActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_mima:  //修改密码
                startActivity(XiugaiLoginActivity.class);
                break;
            case R.id.rl_phone:
                showShortToast("已绑定");
                break;
        }
    }
}
