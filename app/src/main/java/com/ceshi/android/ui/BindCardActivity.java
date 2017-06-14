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
import com.ceshi.android.model.ResultVoNoData;
import com.ceshi.android.ui.CityActivity;
import com.ceshi.android.ui.ProvincesActivity;
import com.ceshi.android.ui.SafeCenterActivity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/10.
 */
public class BindCardActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.et_card)
    EditText et_card;
    @Bind(R.id.tv_select_yh)
    TextView tv_select_yh;
    @Bind(R.id.tv_shengfen)
    TextView tv_shengfen;
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Bind(R.id.btn_bind)
    Button btn_bind;
    @Bind(R.id.et_bank_name)
    EditText et_bank_name;
    private Intent intent;
    private String provinceCode, bankCode, cityCode;
    private String message;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bindcard);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ll_zhuce.setVisibility(View.GONE);
        widget_title.setText("绑定银行卡");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initEvent() {
        tv_select_yh.setOnClickListener(this);
        tv_shengfen.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选择银行卡
            case R.id.tv_select_yh:
                intent = new Intent(BindCardActivity.this, com.ceshi.android.ui.BankActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
                break;
            //省份
            case R.id.tv_shengfen:
                intent = new Intent(BindCardActivity.this, ProvincesActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);
                break;
            //城市
            case R.id.tv_city:
                if (provinceCode == null) {
                    showShortToast("请选择省份！");
                    return;
                }
                intent = new Intent(BindCardActivity.this, CityActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("provinceCode", provinceCode);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
                break;
            //进行绑定
            case R.id.btn_bind:
                bindCard();
                break;
        }
    }

    //进行绑定
    private void bindCard() {
        String candNum = et_card.getText().toString();
        String bankName = et_bank_name.getText().toString();
        if (candNum.equals("")) {
            showShortToast("请输入银行卡号!");
            return;
        }
        if (bankName.equals("")) {
            showShortToast("请输入支行名称!");
            return;
        }
        RequestParams requestParams = new RequestParams(Const.BindBankCard);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("candNum", candNum);  //卡号
        requestParams.addBodyParameter("bankCode", bankCode);// 银行
        requestParams.addBodyParameter("subBranch", bankName);//支行
        requestParams.addBodyParameter("provinceCode", provinceCode);//省份
        requestParams.addBodyParameter("cityCode", cityCode);//城市


        Log.i("bangka","cookie"+mApplication.mUserCookie+",candNum"+candNum+",bankCode"+bankCode+",bankName"+bankName+",provinceCode"+provinceCode+",cityCode"+cityCode);
        showLoadingDialog("Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Log.i("bangka","绑卡的返回数据是"+result);
                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                Log.d(TAG, "绑定定银行卡mResult: " + mResult.toString());
                if (mResult == null) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                showShortToast(mResult.getMessage());
                Log.d(TAG, "绑定定银行卡mResult.isResult=" + mResult.isResult());
                Log.i("yan","绑卡结果是"+mResult.isResult()+"++"+mResult.getMessage());
                if("请先进行实名认证".equals(mResult.getMessage())){
                    startActivity(com.ceshi.android.ui.AutonymActivity.class);
                    finish();
                }
                if (mResult.isResult()) {
                    //跳转到安全中心界面
                    Intent intent = new Intent(BindCardActivity.this, SafeCenterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                dismissLoadingDialog();
                Log.i("xUtilsError","BindCardActivity 的 bindCard 异常"+throwable.toString());
                showShortToast("网络请求失败!");
            }

            @Override
            public void onCancelled(CancelledException e) {
                dismissLoadingDialog();
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
                dismissLoadingDialog();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                // 取出Intent里的数据
                String provinceName = data.getStringExtra("provinceName");
                provinceCode = data.getStringExtra("provinceCode");
                tv_shengfen.setText(provinceName);
                break;
            case 1:
                String bankName = data.getStringExtra("bankName");
                bankCode = data.getStringExtra("bankCode");
                tv_select_yh.setText(bankName);
                break;
            case 2:
                // 取出Intent里的数据
                String cityName = data.getStringExtra("cityName");
                cityCode = data.getStringExtra("cityCode");
                tv_city.setText(cityName);
                break;
        }
    }
}
