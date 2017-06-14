package com.ceshi.android.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.ceshi.android.base.util.TimeCountUtil;
import com.ceshi.android.entity.PostVerificationEntity;
import com.jungly.gridpasswordview.GridPasswordView;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

import butterknife.Bind;

/**
 * 安全中心的交易密码界面
 * Created by Administrator on 2016/5/10.
 */
public class TranPasswordActivity extends BaseActivity {
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.et_pic_chicknum)
    EditText et_pic_chicknum;
    @Bind(R.id.iv_yz)
    ImageView iv_yz;
    @Bind(R.id.et_chicknum)
    EditText et_chicknum;
    @Bind(R.id.tv_hq)
    TextView tv_hq;
    @Bind(R.id.pswView)
    GridPasswordView pswView;
    @Bind(R.id.btn_next)
    Button btn_finish;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    String failMessage;
    String verificationJsessionId;

    private TimeCountUtil timeCountUtil;
    String phone;
    public static final int SUCCEED = 1;

    Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    timeCountUtil = new TimeCountUtil(TranPasswordActivity.this, 60000, 1000, tv_hq);
                    timeCountUtil.start();
                    showShortToast("验证码已发送");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_updatatranpassword);
        super.onCreate(savedInstanceState);
        initPicture();
    }

    @Override
    protected void initView() throws IOException {
        widget_title.setText("交易密码");
        ll_zhuce.setVisibility(View.GONE);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        tv_phone.setText(phone);
        tv_hq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送短信验证码
                sendMsgCode();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compelete();
            }
        });
        iv_yz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPicture();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //    点击完成，更改交易密码
    public void compelete() {
        if (TextUtils.isEmpty(et_chicknum.getText())) {
            showShortToast("请填写验证码");
            return;
        }
        if (TextUtils.isEmpty(pswView.getPassWord())) {
            showShortToast("请填写交易密码");
            return;
        }

        RequestParams requestParams = new RequestParams(Const.changeTradePassword);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("mobileCode", et_chicknum.getText().toString().trim());
        requestParams.addBodyParameter("newTranPassword", pswView.getPassWord());
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                PostVerificationEntity entity = gson.fromJson(result, PostVerificationEntity.class);
                Log.d("TAG", "" + entity.isResult());
                showShortToast(entity.getMessage());
                if (entity.isResult()) {
                    finish();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","TranPasswordActivity 的 compelete 异常"+throwable.toString());
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

    //发送验证码
    private void sendMsgCode() {
        String picnum = et_pic_chicknum.getText().toString();
        if (picnum.equals("")) {
            showShortToast("请填写图片验证码");
            return;
        }
        Log.d("tag", "TranPasswordActivity里面的picnum=" + picnum);

        RequestParams requestParams = new RequestParams(Const.sendMobileCode);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("imageCode", picnum);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                Gson gson = new Gson();
                PostVerificationEntity entity = gson.fromJson(result, PostVerificationEntity.class);
                Log.d("tag", "TranPasswordActivity里面的entity=" + entity.toString());
                if (entity.isResult()) {
                    verificationJsessionId = entity.getJsessionId();
                    Message message = Message.obtain();
                    message.what = SUCCEED;
                    timeHandler.sendMessage(message);
                    showShortToast("验证码已发送");
                } else {
                    failMessage = entity.getMessage();
                    if (failMessage != null) {
                        showShortToast(failMessage);
                    } else {
                        showShortToast("发送失败");
                    }

                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","TranPasswordActivity 的 sendMsgCode 异常"+throwable.toString());
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

    //    //显示图片验证码
    private void initPicture() {
        RequestParams requestParams = new RequestParams(Const.RegisterPicture);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        Log.i("luobo","获取图片cookie是"+mApplication.mUserCookie);
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<byte[]>() {
            @Override
            public void onSuccess(byte[] result) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(result,0,result.length);
                Matrix matrix = new Matrix();
                matrix.postScale(1.8f, 1.8f);
                Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                iv_yz.setImageBitmap(newbmp);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","TranPasswordActivity 的 initPicture 异常"+throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });



    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeHandler.removeMessages(SUCCEED);
    }
}
