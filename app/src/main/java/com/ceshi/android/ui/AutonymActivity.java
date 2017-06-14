package com.ceshi.android.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.model.ResultVoNoData;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * 实名认证界面
 * Created by ztr on 2016/05/03.
 */
public class AutonymActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.et_zs_name)
    EditText et_zs_name;
    @Bind(R.id.et_login_name)
    EditText et_login_name;
    @Bind(R.id.login_queren)
    Button login_queren;
    @Bind(R.id.et_pic_chicknum)
    EditText et_pic_chicknum;
    @Bind(R.id.iv_yz)
    ImageView iv_yz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_autonym);
        super.onCreate(savedInstanceState);
        initPicture();
    }


    @Override
    protected void initView() {
        widget_title.setText("实名认证");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_yz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPicture();
            }
        });
        //认证
        login_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autonymData();
            }
        });


    }

    private void autonymData() {
        String name = et_zs_name.getText().toString();
        String id = et_login_name.getText().toString();
        String code = et_pic_chicknum.getText().toString();
        if (name.equals("")) {
            showShortToast("姓名不能为空！");
            return;
        }
        if (id.equals("")) {
            showShortToast("身份证号不能为空！");
            return;
        }
        if (code.equals("")) {
            showShortToast("验证码不能为空！");
            return;
        }
        showLoadingDialog("实名认证中...");
        RequestParams requestParams = new RequestParams(Const.updateIdentification);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("realName", name);
        requestParams.addBodyParameter("identification", id);
        requestParams.addBodyParameter("imageCode", code);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null){
                    showShortToast("网络请求获取数据失败！");
                    return;
                }
                Gson gson=new Gson();
                Log.d(TAG,"实名认证的response="+result);
                ResultVoNoData mResult=gson.fromJson(result,ResultVoNoData.class);
                if(mResult==null) {
                    return;
                }
                if(mResult.isResult()) {
                    dismissLoadingDialog();
                    Log.d(TAG,"实名认证成功了哦...");
                    finish();
                }
                if("系统错误，请稍候重试".equals(mResult.getMessage())){
                    initPicture();
                }
                showShortToast(mResult.getMessage());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                dismissLoadingDialog();
                showShortToast("网络异常，请连接网络！");
                Log.i("xUtilsError","AutonymActivity 的 autonymData 异常"+throwable.toString());
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


    private void initPicture() {
        RequestParams requestParams = new RequestParams(Const.RegisterPicture);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");

        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<byte[]>() {

            @Override
            public void onSuccess(byte[] result) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(result,0,result.length);
                Matrix matrix = new Matrix();
                matrix.postScale(2f, 2f);
                Bitmap newBtm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                iv_yz.setImageBitmap(newBtm);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","AutonymActivity 的 initPicture 异常"+throwable.toString());
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
