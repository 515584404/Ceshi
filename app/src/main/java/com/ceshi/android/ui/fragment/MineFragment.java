package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.MineEntity;
import com.ceshi.android.entity.SafeEntity;
import com.ceshi.android.ui.DepositActivity;
import com.ceshi.android.ui.LiCaiActivity;
import com.ceshi.android.ui.MessageActivity;
import com.ceshi.android.ui.RedPacketActivity;
import com.ceshi.android.ui.SafeCenterActivity;
import com.ceshi.android.ui.SpreadActivity;
import com.ceshi.android.ui.TradeActivity;
import com.ceshi.android.ui.TransOutActivity;
import com.ceshi.android.ui.WithdrawActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_transout)
    TextView mTransOut;
    @Bind(R.id.rl_setting)
    RelativeLayout rl_setting;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.htv_name)
    HandyTextView tv_name;
    @Bind(R.id.rl_licai)
    RelativeLayout rl_licai;
    @Bind(R.id.rl_jiaoyi)
    RelativeLayout rl_jiaoyi;
    @Bind(R.id.rl_tuiguang)
    RelativeLayout rl_tuiguang;
    @Bind(R.id.phone)
    ImageView phone;
    @Bind(R.id.ka)
    ImageView ka;
    @Bind(R.id.shiming)
    ImageView shiming;
    @Bind(R.id.htv_one)
    HandyTextView htv_one;
    @Bind(R.id.htv_two)
    HandyTextView htv_two;
    @Bind(R.id.htv_three)
    HandyTextView htv_three;
    @Bind(R.id.iv_charge)
    LinearLayout iv_charge;
    @Bind(R.id.iv_cash)
    LinearLayout iv_cash;
    @Bind(R.id.current_holding)
    TextView tv_current_holding;
    @Bind(R.id.current_yesterday_earning)
    TextView tv_current_yesterday_earning;
    @Bind(R.id.current_accumulate_balance)
    TextView tv_current_accumulate_balance;
    @Bind(R.id.period_holdings)
    TextView tv_period_holdings;
    @Bind(R.id.period_prcopective_share)
    TextView tv_period_prcopective_share;
    @Bind(R.id.period_accumulated_earnings)
    TextView tv_period_accumulated_earnings;
    @Bind(R.id.rl_message)
    RelativeLayout rl_message;
    @Bind(R.id.rl_redpacket)
    RelativeLayout rlRedpacket;

    private String availableMoney, mobile, totalMoney, totalInterest, yestodayInterest, currentInterest,
            currentYestodayInterest, currentMoney, periodicalExpactInterest, periodicalInterest, periodicalMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
//        mineCenterData();
//        messageCount();
//        requestUserinfo();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
//        mineCenterData();
//        messageCount();
//        requestUserinfo();
        Log.d(TAG, "MineFragment里面的请求网络数据执行了一次了");
//        widget_title.setText("我的(爱生息)");
    }

    @Override
    public void onStart() {
    Log.e(TAG, "MineFragment的onStart:方法调用了 ");
        super.onStart();
//        mineCenterData();
//        messageCount();
//        requestUserinfo();

    }

    @Override
    public void onResume() {
        Log.e(TAG, "MineFragment的onResume:方法调用了 ");
        mineCenterData();
        messageCount();
        requestUserinfo();
        super.onResume();
    }

    //实名认证  手机号  银行卡绑定  图片的显示
    private void requestUserinfo() {
        RequestParams requestParams = new RequestParams(Const.SafeCenter);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                SafeEntity safeEntity = gson.fromJson(result, SafeEntity.class);
                if (safeEntity.isIsBindBankCard()) {
                    ka.setImageResource(R.mipmap.identify_card);
                } else {
                    ka.setImageResource(R.mipmap.unidentify_card);
                }
                if (safeEntity.isIsSetIdentification()) {
                    shiming.setImageResource(R.mipmap.identify_user);
                } else {
                    shiming.setImageResource(R.mipmap.unidentify_user);
                }
                if (TextUtils.isEmpty(safeEntity.getMobile())) {
                    phone.setImageResource(R.mipmap.unidentify_phone);
                } else {
                    phone.setImageResource(R.mipmap.identify_phone);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "MineFragment的requestUserinfo异常" + throwable.toString());
//                showShortToast("网络异常，请连接网络！");
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


    //阅读消息数量
    private void messageCount() {
        RequestParams requestParams = new RequestParams(Const.UnreadMessageCount);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
//                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                //  Log.i("xUtilsError","在MaineFragment里面获取的数据是："+result);
                int s = Integer.parseInt(result);
                // TODO

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "MineFragment的阅读消息数量异常" + throwable.toString());
//                showShortToast("网络异常，请连接网络！");
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

    private void mineCenterData() {
        RequestParams requestParams = new RequestParams(Const.MineCenter);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
//        showLoadingDialog("Loading...");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
//                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                Log.d("tag", "MineFragment里面的response.body().string()=" + result);
                final MineEntity mResult = gson.fromJson(result, MineEntity.class);
                if (mResult == null) {
                    return;
                }
                Log.d("tag", "MineFragment里面的mResult=" + mResult);
                //手机号1
                mobile = mResult.getMobile();
                //账户总资产2
                totalMoney = mResult.getTotalMoney();
                //累计收益3
                totalInterest = mResult.getTotalInterest();
                //可用余额4
                availableMoney = mResult.getAvailableMoney();
                //昨日收益5
                yestodayInterest = mResult.getYestodayInterest();
                //定期持有金额6
                periodicalMoney = mResult.getPeriodicalMoney();
                //定期累计收益7
                periodicalInterest = mResult.getPeriodicalInterest();
                //定期预期收益8
                periodicalExpactInterest = mResult.getPeriodicalExpactInterest();
                //活期持有金额9
                currentMoney = mResult.getCurrentMoney();
                //活期累计收益10
                currentInterest = mResult.getCurrentInterest();
                //活期昨日收益11
                currentYestodayInterest = mResult.getCurrentYestodayInterest();
                tv_name.setText("" + mobile);
                htv_one.setText("" + totalMoney + "元");
                htv_two.setText("" + totalInterest + "元");
                htv_three.setText("" + availableMoney + "元");
                tv_current_yesterday_earning.setText(currentYestodayInterest + "元");
                tv_period_holdings.setText(periodicalMoney + "元");
                tv_period_accumulated_earnings.setText(periodicalInterest + "元");
                tv_period_prcopective_share.setText(periodicalExpactInterest + "元");
                tv_current_accumulate_balance.setText(currentInterest + "元");
                tv_current_holding.setText(currentMoney + "元");
                Log.d(TAG, "MineFragment里面的mobile" + mobile + ",totalMoney" + totalMoney + ",totalInterest" + totalInterest + ",availableMoney" + availableMoney + ",currentYestodayInterest" + currentYestodayInterest
                        + ",periodicalMoney" + periodicalMoney + ",periodicalInterest" + periodicalInterest + ",periodicalExpactInterest" + periodicalExpactInterest + ",currentInterest" + currentInterest + ",currentMoney=" + currentMoney);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "MineFragment的mineCenterData异常" + throwable.toString());
//                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
//                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
//                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void initEvents() {
        rl_tuiguang.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_licai.setOnClickListener(this);
        rl_jiaoyi.setOnClickListener(this);
        iv_charge.setOnClickListener(this);
        iv_cash.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        mTransOut.setOnClickListener(this);
        rlRedpacket.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //转出
            case R.id.tv_transout:
                Intent intent = new Intent(getActivity(), TransOutActivity.class);
                intent.putExtra("currentMoney", currentMoney);
                startActivity(intent);
                break;
            //我的推广
            case R.id.rl_tuiguang:
                startActivity(SpreadActivity.class);
                break;
            //投资记录
            case R.id.rl_licai:
                startActivity(LiCaiActivity.class);
                break;
            //交易流水
            case R.id.rl_jiaoyi:
                startActivity(TradeActivity.class);
                break;
            //安全中心
            case R.id.rl_setting:
                Bundle bundle = new Bundle();
                bundle.putString("phone", mobile);
                startActivity(SafeCenterActivity.class, bundle);
                break;
            //充值界面
            case R.id.iv_charge:
                startActivity(DepositActivity.class);
                break;
            //提现界面
            case R.id.iv_cash:
                startActivity(WithdrawActivity.class);
                break;
            //我的消息界面
            case R.id.rl_message:
                startActivity(MessageActivity.class);
                break;
            //我的奖励界面  红包 加息券 体验金
            case R.id.rl_redpacket:
                Intent intentRed = new Intent(mActivity, RedPacketActivity.class);
                intentRed.putExtra("mobile",mobile);
                startActivity(intentRed);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
