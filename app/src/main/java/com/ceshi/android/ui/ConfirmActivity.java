package com.ceshi.android.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.InvestEntity;
import com.ceshi.android.model.ResultVoNoData;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.ProductDetailActivity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/04/29. 确认订单
 */
public class ConfirmActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.tv_chanpin)
    HandyTextView tv_chanpin;
    @Bind(R.id.tv_buy)
    HandyTextView tv_buy;
    @Bind(R.id.tv_yue)
    HandyTextView tv_yue;
    @Bind(R.id.tv_lilv)
    HandyTextView tv_lilv;
    @Bind(R.id.tv_shouyi)
    HandyTextView tv_shouyi;
    @Bind(R.id.tv_qix)
    HandyTextView tv_qix;
    @Bind(R.id.tv_time)
    HandyTextView tv_time;
    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    private String money;
    private int id;
    private String message;
    private ProductDetailActivity productDetailActivity = new ProductDetailActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm);
        super.onCreate(savedInstanceState);
        widget_title.setText("确认订单");
        Log.i("luobo", "进入投资初始页");
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("productId", -1);
        System.out.println(id);
        money = intent.getStringExtra("money");
        System.out.println(money);
        confirmData();
        //投资
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmFinish();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //进行投资
    private void confirmFinish() {
        RequestParams requestParams = new RequestParams(Const.invest);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("productId", id + "");
        requestParams.addBodyParameter("money", money);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("luobo", "投资初始页" + result);
                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                if (mResult == null) {
                    return;
                }
                if (mResult.isResult()) {
                    Dialog(mResult.getMessage());
                } else {
                    DialogFaile(mResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ConfirmActivity 的 confirmFinish 异常"+throwable.toString());
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

    //显示确认订单详情
    private void confirmData() {
        RequestParams requestParams = new RequestParams(Const.investInit);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("productId", id + "");
        requestParams.addBodyParameter("money", money);
        showLoadingDialog(" Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                InvestEntity mResult = gson.fromJson(result, InvestEntity.class);
                if(mResult==null)
                {
                    return;
                }
                tv_chanpin.setText(mResult.getProductTitle());
                tv_buy.setText(mResult.getMoney());
                tv_yue.setText(mResult.getAvailableMoney());
                tv_lilv.setText(mResult.getRate());
                tv_qix.setText(mResult.getDuration());
                tv_shouyi.setText(mResult.getInterest());
                tv_time.setText(mResult.getRepayDate());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ConfirmActivity 的 confirmData 异常"+throwable.toString());
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

    }

    @Override
    protected void initEvent() {

    }

    //成功
    public void Dialog(String message) {
        LayoutInflater m = LayoutInflater.from(ConfirmActivity.this);
        LinearLayout layout = (LinearLayout) m.inflate(R.layout.item_kuang, null);
        Button button = (Button) layout.findViewById(R.id.btn_finish);
        TextView textView = (TextView) layout.findViewById(R.id.tv_message);
        textView.setText(message);
        final Dialog dialog = new AlertDialog.Builder(ConfirmActivity.this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
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
              /* setResult(0);
               ConfirmActivity.this.finish();*/
                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                intent.putExtra("depositSuccess", true);
                startActivity(intent);
                finish();

            }
        });
    }

    //失败
    public void DialogFaile(String message) {
        LayoutInflater m = LayoutInflater.from(ConfirmActivity.this);
        LinearLayout layout = (LinearLayout) m.inflate(R.layout.item_kuang_yes, null);
        TextView textView = (TextView) layout.findViewById(R.id.tv_message);
        textView.setText(message);
        final Dialog dialog = new AlertDialog.Builder(ConfirmActivity.this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        ImageView diss = (ImageView) layout.findViewById(R.id.iv_diss);
        diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
