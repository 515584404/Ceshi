package com.ceshi.android.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.LockPatternView;
import com.ceshi.android.util.LockPatternUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更改手势密码页面
 * Created by Mark on 2016/9/13.
 */
public class GestureModifyActivity extends BaseActivity {

    public LockPatternView mLockPatternView;

    public LockPatternUtils mLockPatternUtils;

    @Bind(R.id.iv_gesture_back)
    ImageView iv_gesture_back;
    @Bind(R.id.tv_gesture_forgetMima)
    TextView tvGestureForgetMima;

    private FragmentTransaction ft;

    private TextView mgestureText;

    private Message message;
    private Message message1 = new Message();


    //   第一次输入正确的手势密码
    private static final int INPUT_ORIGIN_GESTURE = 0;
    //    输入原始密码正确，跳到输入新密码
    private static final int INPUT_FIRST_NEW_GESTURE = 1;
    //   第一次输入新密码之后，跳到输入第二次新密码
    private static final int INPUT_SECOND_NEW_GESTURE = 2;
    //    输入第二次新密码错误，重新第二次输入新密码
    private static final int INPUT_SECOND_MUTI_NEW_GESTURE = 3;
    //    输入旧密码错误，重新输入旧密码
    private static final int INPUT_RIGHT_ORIGIN_GESTURE = 4;

    //    保存第一次输入的新密码
    private List<LockPatternView.Cell> mNewPattern;


    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case INPUT_ORIGIN_GESTURE:          //第一次输入旧密码
                    mgestureText.setText("请输入原密码");
                    mLockPatternView.setOnPatternListener(new OnGestureListener() {

                        @Override
                        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                            super.onPatternDetected(pattern);

                            if (pattern == null) {
                                return;
                            }
                            if (pattern.size() < 4) {
                                Toast.makeText(GestureModifyActivity.this, "至少连接四个点", Toast.LENGTH_SHORT).show();
//                                mLockPatternUtils.clearLock();
                                mLockPatternView.clearPattern();
//                                pattern.clear();
                                return;
                            }
                            if (mLockPatternUtils.checkPattern(pattern)) {
                                showToast("密码正确，请输入新密码");
                                //清除已经划到的痕迹
                                mLockPatternView.clearPattern();
                                message1.what = INPUT_FIRST_NEW_GESTURE;
                                handler.sendMessage(message1);
                            } else {
                                showToast("密码错误，请重新输入");
                                //清除已经划到的痕迹
                                mLockPatternView.clearPattern();
                                Message message = new Message();
                                message.what = INPUT_RIGHT_ORIGIN_GESTURE;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;
                case INPUT_FIRST_NEW_GESTURE:      //第一次旧密码输入正确，第一次输入新密码
                    mgestureText.setText("请输入新密码");
                    mLockPatternView.setOnPatternListener(new OnGestureListener() {

                        @Override
                        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                            super.onPatternDetected(pattern);

                            if (pattern == null) {
                                return;
                            }
                            if (pattern.size() < 4) {
                                mgestureText.setText("最少连接4个点，请重新输入！");
                                mgestureText.setTextColor(Color.RED);
                                mLockPatternView.clearPattern();
                                return;
                            }

//                           保存本次输入的密码
                            mNewPattern = pattern;
//                            保存进本地文件
                            SharePrefUtil.saveString(GestureModifyActivity.this, "pattern", mNewPattern.toString());
                            Log.d("PatternNew1", mNewPattern.toString());
                            showToast("请再次输入新密码");
                            //清除已经划到的痕迹
                            mLockPatternView.clearPattern();
                            Message message = new Message();
                            message.what = INPUT_SECOND_NEW_GESTURE;
                            Log.d("PatternNew2", mNewPattern.toString());
                            handler.sendMessage(message);
                        }
                    });
                    break;

                case INPUT_SECOND_NEW_GESTURE:  //第二次输入新密码
                    mgestureText.setText("请再次输入新密码");
                    Log.d("PatternNew3", mNewPattern.toString());
                    mLockPatternView.setOnPatternListener(new OnGestureListener() {

                        @Override
                        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                            super.onPatternDetected(pattern);

                            if (pattern == null) {
                                return;
                            }
                            if (pattern.size() < 4) {
                                mgestureText.setText("最少连接4个点，请重新输入！");
                                mgestureText.setTextColor(Color.RED);
                                mLockPatternView.clearPattern();
                                return;
                            }
                            String patternSave = SharePrefUtil.getString(GestureModifyActivity.this, "pattern", null);
                            if (patternSave.toString().equals(pattern.toString())) {
                                showToast("设置密码成功");
                                Log.d("PatternNew", mNewPattern.toString());
                                Log.d("Pattern", pattern.toString());
//                                保存密码
                                mLockPatternUtils.saveLockPattern(pattern);
                                finish();
                            } else {
                                showToast("密码错误，请重新输入");
                                //清除已经划到的痕迹
                                mLockPatternView.clearPattern();
                                Message message = new Message();
                                message.what = INPUT_SECOND_NEW_GESTURE;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;

                case INPUT_RIGHT_ORIGIN_GESTURE:       //第一次输入错误旧密码，第二次输入旧密码
                    mgestureText.setText("请输入原密码");
                    mLockPatternView.setOnPatternListener(new OnGestureListener() {

                        @Override
                        public void onPatternDetected(List<LockPatternView.Cell> pattern) {

                            if (pattern == null) {
                                return;
                            }
                            if (pattern.size() < 4) {
                                mgestureText.setText("最少连接4个点，请重新输入！");
                                mgestureText.setTextColor(Color.RED);//TODO
                                mLockPatternView.clearPattern();
                                return;
                            }
                            if (mLockPatternUtils.checkPattern(pattern)) {
                                showToast("密码正确，请输入新密码");
                                //清除已经划到的痕迹
                                mLockPatternView.clearPattern();
                                Message message = new Message();
                                message.what = INPUT_FIRST_NEW_GESTURE;
                                handler.sendMessage(message);
                            } else {
                                showToast("密码错误，请重新输入");
                                //清除已经划到的痕迹
                                mLockPatternView.clearPattern();
                                Message message = new Message();
                                message.what = INPUT_RIGHT_ORIGIN_GESTURE;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;

                default:
                    break;

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestrue_layout);
        ButterKnife.bind(this);
        tvGestureForgetMima.setVisibility(View.GONE);
        mLockPatternUtils = new LockPatternUtils(this);
        mgestureText = (TextView) findViewById(R.id.gestrue_text);
        mLockPatternView = (LockPatternView) findViewById(R.id.lockview);

        message = new Message();
//        输入原密码
        message.what = INPUT_ORIGIN_GESTURE;
        handler.sendMessage(message);


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() throws IOException {

    }

    @OnClick(R.id.iv_gesture_back)
    public void onClick() {
        finish();
    }

    class OnGestureListener implements LockPatternView.OnPatternListener {

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

        }
    }

    private void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }


}
