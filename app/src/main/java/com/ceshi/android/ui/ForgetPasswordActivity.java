package com.ceshi.android.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.BitmapFromURL;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.TimeCountUtil;
import com.ceshi.android.entity.PostVerificationEntity;
import com.ceshi.android.entity.RegistFinishEntity;
import com.ceshi.android.ui.LoginActivity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/12.
 *///忘记密码
public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.et_pic_chicknum)
    EditText et_pic_chicknum;
    @Bind(R.id.iv_yz)
    ImageView iv_yz;
    @Bind(R.id.et_chicknum)
    EditText et_chicknum;
    @Bind(R.id.txt_reCard)
    TextView txt_reCard;
    @Bind(R.id.et_login_mima)
    EditText et_login_mima;
    @Bind(R.id.et_queren_mima)
    EditText et_queren_mima;
    @Bind(R.id.btn_finish)
    Button btn_finish;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    private Bitmap bitmap;

    String failMessage;
    String verificationJsessionId;
    String returnMessage;

    private TimeCountUtil timeCountUtil;
    private String localCookieStr;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Matrix matrix = new Matrix();
            matrix.postScale(2f, 2f);
            Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            iv_yz.setImageBitmap(newbmp);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forgetpassword);
        super.onCreate(savedInstanceState);
        initPicture();
    }

    @Override
    protected void initView() {
        widget_title.setText("找回密码");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        tv_phone.setText(phone);
        iv_yz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPicture();
            }
        });
//        点击获取验证码
        txt_reCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainVerification();
            }
        });

//点击完成
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mimaFinish();
            }
        });
//        et_login_mima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (et_login_mima.isClickable()){
//
//                }
//            }
//        });
    }

    private void mimaFinish() {
        String newPassword = et_login_mima.getText().toString();
        String qrPassword = et_queren_mima.getText().toString();
        if (!newPassword.equals(qrPassword)) {
            showShortToast("确认密码失败，请重新输入！");
            return;
        }
        String mobileCode = et_chicknum.getText().toString();
        String mobile = tv_phone.getText().toString();
        if (mobileCode.equals("")) {
            showShortToast("短信验证码不能为空！");
            return;
        }
        if (newPassword.equals("")) {
            showShortToast("密码不能为空！");
            return;
        }

        localCookieStr = "JSESSIONID=" + BitmapFromURL.getJsessionId();
        RequestParams requestParams = new RequestParams(Const.forgetPassword);
        requestParams.addHeader("cookie", localCookieStr);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("mobile", mobile);
        requestParams.addBodyParameter("mobileCode", mobileCode);
        requestParams.addBodyParameter("password", newPassword);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("修改密码失败！");
                    return;
                }
                Gson gson = new Gson();
                RegistFinishEntity entity = gson.fromJson(result, RegistFinishEntity.class);
                returnMessage = entity.getMessage();
                showShortToast(returnMessage);
                if(entity.isResult()){
                    startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ForgetPasswordActivity 的 mimaFinish 异常"+throwable.toString());
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

    //获取短信验证码
    private void gainVerification() {
        String picnum = et_pic_chicknum.getText().toString();
        String phone = tv_phone.getText().toString();
        if (picnum.equals("")) {
            showShortToast("请填写图片验证码");
            return;
        }

        localCookieStr = "JSESSIONID=" + BitmapFromURL.getJsessionId();
        RequestParams requestParams = new RequestParams(Const.SendMsgCode);
        requestParams.addHeader("cookie", localCookieStr);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("imageCode",picnum);
        requestParams.addBodyParameter("mobile",phone);
        requestParams.addBodyParameter("type","FORGET");

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("验证码发送失败！");
                    return;
                }
                Log.d(TAG, "忘记密码界面: "+result);
                Gson gson = new Gson();
                PostVerificationEntity entity = gson.fromJson(result, PostVerificationEntity.class);
                Log.d("TAG", "验证码界面entity.isResult：" + entity.isResult());
                if (entity.isResult()) {
                    timeCountUtil = new TimeCountUtil(ForgetPasswordActivity.this,60000,1000,txt_reCard);
                    timeCountUtil.start();
                    showShortToast("验证码已发送");
                    verificationJsessionId = entity.getJsessionId();
                    Log.d("TAG", "verificationJsessionId:==== "+verificationJsessionId);
                } else {
                    Log.d("TAG", "验证码发送失败");
                    failMessage = entity.getMessage();
                    showShortToast(failMessage);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ForgetPasswordActivity 的 gainVerification 异常"+throwable.toString());
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

    //    //显示图片验证码
    private void initPicture() {
        Log.i("TAG","下面开始获取验证码图片");
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                //得到可用的图片
                Log.i("TAG","下面开始获取验证码图片");
                bitmap = BitmapFromURL.getHttpBitmap(Const.RegisterPicture);
                System.out.println("oooo" + bitmap);
                if(bitmap!=null){
                    myHandler.sendMessage(msg);
                }
            }
        }.start();

    }
}
