package com.ceshi.android.ui;

import android.content.Intent;
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
import com.ceshi.android.entity.DepositEntity;
import com.ceshi.android.model.ResultVoNoData;
import com.ceshi.android.ui.VertifyCodeActivity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/05. 充值界面
 */
public class DepositActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.tv_keyong)
    EditText tv_keyong;
    @Bind(R.id.btn_chongzhi)
    Button btn_chongzhi;
    @Bind(R.id.et_jine)
    EditText et_jine;
    @Bind(R.id.tv_card)
    TextView tv_card;
    @Bind(R.id.tv_yinhang)
    TextView tv_yinhang;
    @Bind(R.id.tv_tishi)
    TextView tishi;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_deposit);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("充值");
        ll_zhuce.setVisibility(View.GONE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        depositData();
        //点击充值按钮 进行充值
        btn_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongzhiData();
            }
        });
    }

    private void chongzhiData() {
        String money = et_jine.getText().toString();
        if (money.equals("")) {
            showShortToast("充值金额不能为空!");
            return;
        }
        if (Integer.valueOf(money) <= 2) {
            showShortToast("最低充值金额为3元!");
            return;
        }
        RequestParams requestParams = new RequestParams(Const.Deposit);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("money", money);
        showLoadingDialog("Loading...");  // 显示数据正在提交中

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("luobo", "xUtils获取的数据是：" + result);
                if (result == null || "".equals(result)) {
                    showShortToast("充值请求失败！");
                    return;
                }
                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                if (mResult == null) {
                    showShortToast("充值请求失败！");
                    return;
                }
               // showShortToast(mResult.getMessage());
                if (mResult.isResult()) {
                    Log.i("luobo", "跳转提交界面 " + mResult.toString());
                    Intent intent = new Intent(DepositActivity.this, VertifyCodeActivity.class);
                    intent.putExtra("message", mResult.getMessage());
                    startActivity(intent);
                }else{
                    showShortToast("充值请求失败！");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "DepositActivity 的 chongzhiData 异常" + throwable.toString());
                showShortToast("提交充值信息时网络请求失败！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("提交充值信息时网络请求取消！");
            }

            @Override
            public void onFinished() {
                dismissLoadingDialog();     // 隐藏正在加载对话框
            }
        });
    }

    private void depositData() {
        RequestParams requestParams = new RequestParams(Const.Deposit_main);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("luobo", "获取金额信息是的数据是：" + result);
                Gson gson = new Gson();
                DepositEntity mResult = gson.fromJson(result, DepositEntity.class);
                tv_keyong.setText(mResult.getAvailableMoney() + "元");
                tishi.setText(mResult.getMoneyLimitDescription());
                if (mResult.isIsBindBankCard()) {    //是否绑定银行卡
                    String s = mResult.getBankCard().toString();
                    System.out.println("cccc" + s);
                    String spStr[] = s.split("\\s{1,}");
                    System.out.println("sssss" + spStr[0]);
                    tv_yinhang.setText(spStr[0]);
                    tv_card.setText(spStr[1]);
                    //显示提示信息
                } else {
                    tv_card.setText("添加银行卡");
                    tv_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //隐藏提示信息
                            startActivity(com.ceshi.android.ui.BindCardActivity.class);
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showShortToast("网络异常，请连接网络！");
                Log.i("xUtilsError", "DepositActivity 的 depositData 异常" + throwable.toString());
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
