package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.BitmapFromURL;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.TimeCountUtil;
import com.ceshi.android.entity.PostVerificationEntity;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.fragment.FragmentRegisterthree;
import com.ceshi.android.ui.fragment.FragmentRegistertwo;
import com.ceshi.android.util.OkHttpClientUtil;
import com.ceshi.android.util.PhoneFormatCheckUtils;
import com.ceshi.android.util.ToastUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/27.
 */
@SuppressLint("ValidFragment")
public class FragmentRegisterone extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.btn_next)
    Button btn_next;
    //    @Bind(R.id.tv_login)
//    TextView tv_login;
    @Bind(R.id.et_chicknum)
    EditText et_chicknum;
    @Bind(R.id.tv_get_verification_code)
    TextView mGetCode;
    @Bind(R.id.et_pic_chicknum)
    EditText et_pic_chicknum;
    @Bind(R.id.iv_yz)
    ImageView iv_yz;

    private FrameLayout fl;
    private ImageView iv_back;
    private FragmentTransaction ft;
    private FragmentRegistertwo fragmentRegistertwo;
    //倒计时信息处理
    public static final int TIMERMESSAGE = -1;
    //倒计时结束信息处理
    public static final int TIMEOVER = -2;
    //获取、提交验证码信息处理
    public static final int AFTEREVENT = -3;

    public static final int SUCCEED = 1;
    public static final int FAIL = 2;
    private String localCookieStr;
    private Bitmap bitmap;
    private TimeCountUtil timeCountUtil;
    String failMessage;
    private String yzma;
    private FragmentRegisterthree fragmentRegisterthree;
    String verificationJsessionId;
    private String picyzma;
    private int time = 0;
    private boolean isOnclick = false;
    //判断图片验证码是否正确
    boolean picyzmaTrue = false;
    //判断短信是否已经发送
    boolean isSend = false;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            设置验证码图片,放大图片
            Matrix matrix = new Matrix();
            matrix.postScale(2f, 2f);
            Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            iv_yz.setImageBitmap(newbmp);

            localCookieStr = BitmapFromURL.getJsessionId();
            Log.d("TAG", "JsessionId:" + localCookieStr);
        }
    };

    Handler okHttpHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    timeCountUtil = new TimeCountUtil(getActivity(), 60000, 1000, mGetCode);
                    timeCountUtil.start();
                    showShortToast("验证码已发送");
                    break;

                case FAIL:
                    if (failMessage != null) {
                        if(failMessage.equals("该手机号已被注册")){

                        }
                        showShortToast(failMessage);
                    } else {
                        showShortToast("验证码发送失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register_one, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public FragmentRegisterone(FrameLayout fl, ImageView iv_back) {
        this.fl = fl;
        this.iv_back = iv_back;
    }

    @Override
    protected void initViews() {
//        tv_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });

        //设置点击获取验证码按钮
        //如果号码为空 已经点击 return
        //没有点击就倒计时
        iv_yz.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        mGetCode.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        initPicture();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        phone = et_phone.getText().toString(); // 电话号码
        picyzma = et_pic_chicknum.getText().toString();//图片验证码
        yzma = et_chicknum.getText().toString();//短信验证码
        //判断手机号码
        boolean isTruePhone = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);

        switch (v.getId()){
            case R.id.iv_yz://点击获取图片验证码
                initPicture();
                break;
            case R.id.tv_get_verification_code://发送短信验证码
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast(mContext,"手机号不能为空!");
                    return;
                }
                if(!isTruePhone){
                    ToastUtils.showToast(mContext,"请输入正确的手机号码!");
                    return;
                }
//                验证验证码是否填写正确
                if (TextUtils.isEmpty(picyzma)) {
                    ToastUtils.showToast(mContext,"请填写验证码!");
                    return;
                }
//                if(!isOnclick && time == 0){
//                    mGetCode.setBackgroundResource(R.drawable.edittext_background_code);
//                    mGetCode.setText(60 - time + "s");
//                    timeHandler.sendEmptyMessageDelayed(0,1000);
//                    isOnclick = true;
//                }else {
//                    timeHandler.removeMessages(0);
//                    time = 0;
//                    isOnclick = false;
//                    return;
//                }

                System.out.println("eee" + picyzma);
                Log.d("TAG", "图片密码为：" + picyzma);
//                et_pic_chicknum.setEnabled(false);
                sendMsgCode(picyzma);
                break;
            case R.id.btn_next://下一步按钮
                if(!isSend){ // 点击发送了才可点击下一步
                    ToastUtils.showToast(mContext,"请先发送短信进行验证!");
                    return;
                }
                if (phone.equals("")) {
                    ToastUtils.showToast(mContext,"手机号不能为空!");
                    return;
                }
                //图片验证码是否为空
                if (TextUtils.isEmpty(picyzma)) {
                    ToastUtils.showToast(mContext,"请填写验证码!");
                    return;
                }
                //图片验证码是否正确
                if (!picyzmaTrue) {
                    ToastUtils.showToast(mContext,"请填写正确的图片验证码!");
                    return;
                }
                if(!isTruePhone){
                    ToastUtils.showToast(mContext,"请输入正确的手机号码!");
                    return;
                }
//                短信验证码是否填写正确
                if (TextUtils.isEmpty(yzma) & yzma.length() != 6) {
                    ToastUtils.showToast(mContext,"短信验证码错误!");
                    return;
                }
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentRegisterthree = new FragmentRegisterthree(iv_back, fl, phone, yzma, localCookieStr);
                ft.replace(R.id.fl, fragmentRegisterthree);
                ft.commit();
                break;
            case R.id.iv_back://返回按钮
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("quitSuccess", true);
                startActivity(intent);
                getActivity().finish();
                break;
        }

    }

    //    使用okHttp框架请求加上cookie
    private void sendMsgCode(String picnum) {
        final OkHttpClient client = OkHttpClientUtil.getInstance();
        RequestBody formBody = new FormEncodingBuilder()
                .add("imageCode", picnum)
                .add("mobile", et_phone.getText().toString())
                .add("type", "REGIST")
                .build();

        Log.d("TAG", "picnum:" + picnum + "\nmobile:" + et_phone.getText().toString());
        Log.d("TAG", "cookie: " + localCookieStr);

        final Request request = new Request.Builder()
                .addHeader("cookie", "JSESSIONID=" + localCookieStr)
                .url(Const.SendMsgCode)
                .post(formBody)
                .build();

        Log.d("TAG", "注册请求的数据 " + request.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    Message message = new Message();
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        Log.d("TAG", "验证码返回信息成功" + res);
                        //解析发送验证码返回的数据
                        Gson gson = new Gson();
                        PostVerificationEntity entity = gson.fromJson(res, PostVerificationEntity.class);

                        Log.d("TAG", "注册界面解析类PostVerificationEntity: " + entity.toString());
                        Log.d("TAG", "注册界面的entity=" + entity.isResult());

                        if (entity.isResult()) {
                            message.what = SUCCEED;
                            okHttpHandler.sendMessage(message);
                            //获取验证码的jsessionId
                            verificationJsessionId = entity.getJsessionId();
                            picyzmaTrue = true;//图片验证码正确
                            isSend = true;//验证码已经发送
                            Log.d("TAG", "验证码发送成功" + verificationJsessionId);
                        } else {
                            Log.d("TAG", "验证码发送失败");
                            initPicture();  //发送失败重新获取图片验证码
                            failMessage = entity.getMessage();
                            message.what = FAIL;
                            okHttpHandler.sendMessage(message);
                        }
                    } else {
                        Log.d("TAG", "验证码返回信息失败1");
                        initPicture();//发送失败重新获取图片验证码
                        message.what = FAIL;
                        okHttpHandler.sendMessage(message);
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //   显示图片验证码
    private void initPicture() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Message msg = new Message();
                //得到可用的图片
                bitmap = BitmapFromURL.getHttpBitmap(Const.RegisterPicture);
                System.out.println("oooo" + bitmap);
                myHandler.sendMessage(msg);
            }
        }.start();

    }

    @Override
    protected void initEvents() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        okHttpHandler.removeMessages(SUCCEED);
        ButterKnife.unbind(this);
    }

}
