package com.ceshi.android.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.ceshi.android.entity.RegistFinishEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 转出页面类
 * Created by Mark on 2016/9/18.
 */
public class TransOutActivity extends BaseActivity implements View.OnClickListener {

    private float mMaxMoney;

    String transOut;

    @Bind(R.id.widget_title)
    TextView mTitle;

    @Bind(R.id.iv_back)
    ImageView mBack;

    @Bind(R.id.tv_max_transout)
    TextView mMaxTransout;

    @Bind(R.id.btn_make_sure)
    Button mMakeSure;
@Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Bind(R.id.btn_cancle)
    Button mCancle;
    @Bind(R.id.tv_all_out)
    TextView tv_all_out;
    @Bind(R.id.et_transout)
    EditText mTransout;
    public static final int SUCCEED = 1;
    public static final int FAIL = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_out);
        ButterKnife.bind(this);
        mTitle.setText("转出");
        ll_zhuce.setVisibility(View.GONE);
//        String money = getIntent().getStringExtra("current_holding");
        String money = getIntent().getStringExtra("currentMoney");
        Log.d(TAG, "money: "+money);
        mMaxMoney = Float.valueOf(money).floatValue();
        Log.d(TAG, "mMaxMoney: "+mMaxMoney);
        mMaxTransout.setText("本次最多可转出余额为" + mMaxMoney + "元");
        mCancle.setOnClickListener(this);
        mMakeSure.setOnClickListener(this);
        mBack.setOnClickListener(this);
        tv_all_out.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() throws IOException {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.btn_cancle:
                finish();
                break;

            case R.id.btn_make_sure:
                transOut = mTransout.getText().toString();
                Log.d("TAG", "转出金额：" + transOut + "类：" + transOut.getClass());
                if (TextUtils.isEmpty(transOut)) {
                    showShortToast("转出金额不能为空");
                    return;
                }
                Float mMoney = Float.valueOf(transOut);
                if (mMoney == 0) {
                    showShortToast("转出金额不能为零");
                    return;
                }
                if (mMoney > mMaxMoney) {
                    showShortToast("活期余额不足!");
                    break;
                }
//                转出操作
                transOutMoney(mMoney);
                break;
            case R.id.tv_all_out:
                mTransout.setText(mMaxMoney+"");
                break;
            default:
                break;
        }
    }

//转出操作
    private void transOutMoney(Float money) {
        RequestParams requestParams = new RequestParams(Const.transfer);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("money", String.valueOf(money));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                RegistFinishEntity entity = gson.fromJson(result, RegistFinishEntity.class);
                Log.d("TAG", "" + entity.isResult());
                String returnMessage = entity.getMessage();
                if (entity.isResult()){
                    finish();
                }
                showShortToast(returnMessage);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","TransOutActivity 的 transOutMoney 异常"+throwable.toString());
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
