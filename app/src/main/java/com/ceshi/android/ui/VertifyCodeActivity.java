package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.TimeCountUtil;
import com.ceshi.android.model.ResultVoNoData;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * 验证码界面
 */
public class VertifyCodeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.txt_reCard)
    TextView txt_reCard;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.et_verify_code)
    EditText et_verify_code;
    @Bind(R.id.widget_title)
    TextView widget_title;
    private String message;
    private TimeCountUtil timeCountUtil;
    private final static int FAILCODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vertify_code);
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void initView() {
        widget_title.setText("获取验证码");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initEvent() {
        timeCountUtil = new TimeCountUtil(VertifyCodeActivity.this, 60000, 1000, txt_reCard);
        timeCountUtil.start();
        txt_reCard.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        Intent intent = getIntent();
        message = intent.getStringExtra("message");//获取hyTokenId的值
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_reCard://重新获取
                recallVeriyCode();
                break;
            case R.id.btn_submit://提交
                submit();
                break;
        }
    }

    /**
     * 重新获取验证码
     */
    private void recallVeriyCode(){

        RequestParams requestParams = new RequestParams(Const.Recall_verify_code);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("hyTokenId", message);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                if (mResult.isResult()){
                    timeCountUtil = new TimeCountUtil(VertifyCodeActivity.this,60000,1000,txt_reCard);
                    timeCountUtil.start();
                    Toast.makeText(VertifyCodeActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(VertifyCodeActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","VertifyCodeActivity 的 recallVeriyCode 异常"+throwable.toString());
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


    /**
     * 提交
     */
    private void submit(){
        showLoadingDialog("Loading...");
        String veriyCode = et_verify_code.getText().toString().trim();
        if (TextUtils.isEmpty(veriyCode)){
            showShortToast("验证码不能为空");
            return;
        }

        RequestParams requestParams = new RequestParams(Const.Ensure_pay);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("hyTokenId", message);
        requestParams.addBodyParameter("verifyCode", veriyCode);
//        showShortToast("Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                Log.d(TAG,"VerifyCodeActivity里面的验证码返回数据result="+result);
                ResultVoNoData mResult = gson.fromJson(result, ResultVoNoData.class);
                if(mResult==null)
                {
                    return;
                }
                Log.d("tag","VertifyCodeActivity里面的mResult.isResult="+mResult.isResult());
                Log.d(TAG, "c: "+mResult.toString());
                showShortToast(mResult.getMessage());
                if (mResult.isResult()){
                    Log.d("TAG", "结果： "+mResult.toString());
                    Log.d("tag","充值成功"+mResult.getMessage());
                    Intent intent = new Intent(VertifyCodeActivity.this, com.ceshi.android.ui.MainActivity.class);
                    intent.putExtra("depositSuccess",true);
                    startActivity(intent);
                    finish();
                }else {
                    Log.d("tag","充值失败"+mResult.getMessage());
//                                showShortToast("充值失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","VertifyCodeActivity 的 submit 异常"+throwable.toString());
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
}
