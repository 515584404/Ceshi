package com.ceshi.android.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.LockPatternView;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.util.LockPatternUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Mark on 2016/9/14.
 * 手势界面
 */
public class GestureBackActivity extends BaseActivity {

    @Bind(R.id.gestrue_text)
    TextView mGestureText;

    @Bind(R.id.iv_gesture_back)
    ImageView iv_gesture_back;

    @Bind(R.id.tv_gesture_forgetMima)
    TextView tv_gesture_forgetMima;
    @Bind(R.id.lockview)
    public LockPatternView mLockPatternView;

    public LockPatternUtils mLockPatternUtils;

    private final static int INPUT_TWICE = 1;
    private int backCount = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == INPUT_TWICE) {       //输入手势密码

//                mGestureText.setText("请输入手势密码");
                mLockPatternView.setOnPatternListener(new LockPatternView.OnPatternListener() {
                    @Override
                    public void onPatternStart() {

                    }

                    @Override
                    public void onPatternCleared() {

                    }

                    @Override
                    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

                    }

                    @Override
                    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                        if (pattern == null) {
                            return;
                        }
                        if (pattern.size() < 4) {
//                            mGestureText.setText("最少连接4个点，请从新输入！");//TODO
                            mGestureText.setText(getString(R.string.lockpattern_recording_incorrect_too_short));
                            mGestureText.setTextColor(Color.RED);
                            mLockPatternView.clearPattern();
                            return;
                        }
                        if (mLockPatternUtils.checkPattern(pattern)) {
                            showToast("密码正确");
                            finish();
                        } else {
                            showToast("密码错误，请重新输入");
                            //清除已经划到的痕迹
                            mLockPatternView.clearPattern();
                            Message message = new Message();
                            message.what = INPUT_TWICE;
                            handler.sendMessage(message);
                        }
                    }
                });


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestrue_layout);
        mLockPatternUtils = new LockPatternUtils(this);
        mGestureText = (TextView) findViewById(R.id.gestrue_text);
        mLockPatternView = (LockPatternView) findViewById(R.id.lockview);

        Message message = new Message();
        message.what = INPUT_TWICE;
        handler.sendMessage(message);

        mGestureText.setText("请输入手势密码");
        iv_gesture_back.setVisibility(View.INVISIBLE);
        tv_gesture_forgetMima.setVisibility(View.VISIBLE);
        tv_gesture_forgetMima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtil.saveString(GestureBackActivity.this, SharePrefUtil.sp_phone, null);
                SharePrefUtil.saveString(GestureBackActivity.this, SharePrefUtil.sp_pwd, null);
                mApplication.mUserCookie = null;
                Intent intent = new Intent(GestureBackActivity.this, MainActivity.class);
                intent.putExtra("quitSuccess", true);
                startActivity(intent);
                finish();
            }
        });

       /* iv_gesture_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });*/
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() throws IOException {

    }

    private void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

}
