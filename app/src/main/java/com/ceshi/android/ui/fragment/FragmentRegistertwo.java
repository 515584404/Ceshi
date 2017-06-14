package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
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

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/27.
 */
@SuppressLint("ValidFragment")
public class FragmentRegistertwo extends BaseFragment {

    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.et_chicknum)
    EditText et_chicknum;
    @Bind(R.id.tv_get_verification_code)
    TextView mGetCode;
    @Bind(R.id.et_pic_chicknum)
    EditText et_pic_chicknum;
    @Bind(R.id.iv_yz)
    ImageView iv_yz;
    @Bind(R.id.btn_next)
    Button btn_next;

    private FrameLayout fl;
    private FragmentTransaction ft;
    private ImageView iv_back;
    private com.ceshi.android.ui.fragment.FragmentRegisterthree fragmentRegisterthree;
    private String phone;
    private String yzma;
    private String picnum;
    private String localCookieStr;
    String failMessage;
    private TimeCountUtil timeCountUtil;


    //设置验证码等待时间
   // public static int waitTime = 60;
    private Bitmap bitmap;
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



    public FragmentRegistertwo(FrameLayout fl, ImageView iv_back, String phone) {
        this.fl = fl;
        this.iv_back = iv_back;
        this.phone = phone;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register_two, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void initViews() {
        yzma = et_chicknum.getText().toString();
        tv_phone.setText(phone);
        initPicture();
        btn_next.setOnClickListener(new View.OnClickListener() {        //填写完成验证码，点击下一步
            @Override
            public void onClick(View v) {
                yzma = et_chicknum.getText().toString();
//                验证验证码是否填写正确
                if (TextUtils.isEmpty(yzma)) {
                    showShortToast("请填写验证码");
                } else {
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentRegisterthree = new com.ceshi.android.ui.fragment.FragmentRegisterthree(iv_back, fl, phone, yzma, localCookieStr);
                    ft.replace(R.id.fl, fragmentRegisterthree);
                    ft.commit();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                com.ceshi.android.ui.fragment.FragmentRegisterone fragmentRegisterone = new com.ceshi.android.ui.fragment.FragmentRegisterone(fl, iv_back);
                ft.replace(R.id.fl, fragmentRegisterone);
                ft.commit();
            }
        });

//        设置点击获取验证码按钮
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picnum = et_pic_chicknum.getText().toString();
                System.out.println("eee" + picnum);
                Log.d("TAG", "图片密码为：" + picnum);
                sendMsgCode(picnum);
            }
        });
        iv_yz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPicture();
            }
        });
    }

    //    使用okHttp框架请求加上cookie
    private void sendMsgCode(String picnum) {
        localCookieStr = "JSESSIONID=" + BitmapFromURL.getJsessionId();
        RequestParams requestParams = new RequestParams(Const.SendMsgCode);
        requestParams.addHeader("cookie", localCookieStr);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("imageCode", picnum);
        requestParams.addBodyParameter("mobile", phone);
        requestParams.addBodyParameter("type", "REGIST");

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
                if (entity.isResult()) {
                    timeCountUtil =   new TimeCountUtil(getActivity(),60000,1000,mGetCode);
                    timeCountUtil.start();
                    showShortToast("验证码已发送");
                } else {
                    failMessage = entity.getMessage();
                    if (failMessage != null) {
                        showShortToast(failMessage);
                    } else {
                        showShortToast("验证码发送失败");
                    }
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","FragmentRegistertwo 的 sendMsgCode 异常"+throwable.toString());
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
    protected void initEvents() {

    }

    //    //显示图片验证码
    private void initPicture() {
        Log.i("luobo","下面开始获取验证码图片");
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                //得到可用的图片
                Log.i("luobo","下面开始获取验证码图片");
                bitmap = BitmapFromURL.getHttpBitmap(Const.RegisterPicture);
                System.out.println("oooo" + bitmap);
                if(bitmap!=null){
                    myHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
