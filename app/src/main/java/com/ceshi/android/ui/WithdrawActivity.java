package com.ceshi.android.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.WithdrawEntity;
import com.ceshi.android.model.ResultVoNoData;
import com.jungly.gridpasswordview.GridPasswordView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/05. 提现金额
 */
public class WithdrawActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.et_jine)
    EditText et_jine;
    @Bind(R.id.tv_keyong)
    HandyTextView tv_keyong;
    @Bind(R.id.btn_chongzhi)
    Button btn_tx;
    @Bind(R.id.rl_top)
    RelativeLayout rl_top;
    @Bind(R.id.ll_qixian)
    LinearLayout ll_qixian;
    @Bind(R.id.tv_card)
    TextView tv_card;
    @Bind(R.id.tv_yinhang)
    TextView tv_yinhang;
    @Bind(R.id.ll_tishi)
    LinearLayout ll_tishi;
    private PopupWindow popupWindow;
    private GridPasswordView gpv;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        widget_title.setText("提现");
        ll_zhuce.setVisibility(View.GONE);
        withdrawData();
    }

    private void clickinitView() {

        btn_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = et_jine.getText().toString();
                if (money.equals("")) {
                    showShortToast("请输入金额！");
                    return;
                }
                if (Integer.valueOf(money) <= 2) {
                    showShortToast("最低提现金额为3元！");
                    return;
                }

                if (Integer.valueOf(money) > tv_keyong.getId()) {
                    showShortToast("账户余额不足!");
                    return;
                }
                LayoutInflater m = LayoutInflater.from(WithdrawActivity.this);
                LinearLayout layout = (LinearLayout) m.inflate(R.layout.item_withdraw, null);

                //弹出软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                //获取软键盘的显示状态
//                boolean isOpen = imm.isActive();
                //如果软键盘已经显示，则隐藏，反之则显示
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


                gpv = (GridPasswordView) layout.findViewById(R.id.pswView);
                Button button = (Button) layout.findViewById(R.id.btn_add);

                final Dialog dialog = new AlertDialog.Builder(WithdrawActivity.this).create();
                dialog.show();
                dialog.getWindow().setContentView(layout);
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                ImageView diss = (ImageView) layout.findViewById(R.id.iv_diss);
                diss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        withdrawFinish();
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    //提现
    private void withdrawFinish() {
        String tranPassword = gpv.getPassWord();
        String money = et_jine.getText().toString();

        RequestParams requestParams = new RequestParams(Const.Withdraw);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("money", money);
        requestParams.addBodyParameter("tranPassword", tranPassword);
        showLoadingDialog("Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                Log.d(TAG, "WithdrawActivity里面的体现ResultVoNoData=" + mResult);
                if (mResult == null) {
                    return;
                }
                showShortToast(mResult.getMessage());
                if (mResult.isResult()) {
                    Intent intent = new Intent(WithdrawActivity.this, com.ceshi.android.ui.MainActivity.class);
                    intent.putExtra("depositSuccess", true);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "WithdrawActivity 的 withdrawFinish 异常" + throwable.toString());
                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
                dismissLoadingDialog();
            }
        });

//        showLoadingDialog("数据提交中");

    }


    private void withdrawData() {
        RequestParams requestParams = new RequestParams(Const.Withdraw_main);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                WithdrawEntity mResult = gson.fromJson(result, WithdrawEntity.class);
                if (mResult == null) {
                    showShortToast("数据请求失败");
                }
                tv_keyong.setText(mResult.getAvailableMoney() + "元");
                //是否绑定银行卡
                if (mResult.isIsBindBankCard()) {
                    tv_yinhang.setText(mResult.getBankCardName());
                    tv_card.setText(mResult.getBankCardNum());
                    ll_tishi.setVisibility(View.VISIBLE);
                } else {
                    tv_card.setText("添加银行卡");
                    tv_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(com.ceshi.android.ui.BindCardActivity.class);
                            ll_tishi.setVisibility(View.GONE);
                        }
                    });
                }
                //是否设置了交易密码
                if (mResult.isIsSetTranPassword()) {
                    clickinitView();
                } else {
                    showShortToast("请设置交易密码！");
                    //跳转到交易密码界面
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "WithdrawActivity 的 withdrawData 异常" + throwable.toString());
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
