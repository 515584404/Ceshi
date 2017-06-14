package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.ui.LoginActivity;
import com.ceshi.android.util.PhoneFormatCheckUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**忘记密码界面
 * Created by Administrator on 2016/5/12.
 */
public class ForgetPasswordPhoneActivity extends BaseActivity {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.rl_et)
    LinearLayout rlEt;
    @Bind(R.id.ll_into_login)
    LinearLayout llIntoLogin;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.widget_title)
    TextView widgetTitle;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.view3)
    AppBarLayout view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_one);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        widgetTitle.setText("找回密码");
        llIntoLogin.setVisibility(View.GONE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
//                判断手机号码
                boolean isTruePhone = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                if(!isTruePhone){
                    Toast.makeText(mApplication, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.equals("")) {
                    showShortToast("手机号不能为空！");
                    return;
                }
                Intent intent = new Intent(ForgetPasswordPhoneActivity.this, com.ceshi.android.ui.ForgetPasswordActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });

        llIntoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordPhoneActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initEvent() {
    }
}
