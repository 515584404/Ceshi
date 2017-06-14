package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.LockPatternView;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.fragment.FragmentmineLogin;
import com.ceshi.android.ui.fragment.MineFragment;
import com.ceshi.android.util.LockPatternUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 从“我的”进入手势密码界面
 * Created by Administrator on 2016/5/17.
 */
@SuppressLint("ValidFragment")

public class FragmentmineGestureLogin extends BaseFragment {
    @Bind(R.id.lockview)
    LockPatternView mLockPatternView;
    @Bind(R.id.gestrue_text)
    TextView mGestureTex;
    @Bind(R.id.iv_gesture_back)
    ImageView iv_gesture_back;
    @Bind(R.id.tv_gesture_forgetMima)
    TextView tv_gesture_forgetMima;
    public LockPatternUtils mLockPatternUtils;
    private MineFragment mineFragment;
    private FragmentTransaction ft;
    private String lgname;//定义手机号
    private String mempsw;//定义密码
    //第二次确认密码
    private static final int INPUT_SECOND_GESTURE = 1;
    //第二次确认密码错误，重新确认
    private static final int INPUT_RE_SECOND_GESTURE = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INPUT_SECOND_GESTURE:
//                    mGestureTex.setText("请重新输入");
                    mGestureTex.setText(getString(R.string.lockpattern_need_to_confirm));
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
//                检查手势密码输入情况
                            if (pattern == null) {
                                return;
                            }
                            // 如果小于四个点
                            if (pattern.size() < 4) {
//                                mGestureTex.setText("最少连接4个点，请重新输入！");
                                mGestureTex.setText(getString(R.string.lockpattern_recording_incorrect_too_short));
                                mGestureTex.setTextColor(Color.RED);//TODO
//                    清除已经划到的痕迹
                                mLockPatternView.clearPattern();
//                    pattern.clear();
                                return;
                            } else {
//                                如果验证相符合
                                if (SharePrefUtil.getString(getActivity(), "pattern", null).equals(pattern.toString())) {
                                    mLockPatternUtils.saveLockPattern(pattern);
                                    Toast.makeText(getActivity(), "登录成功!", Toast.LENGTH_SHORT).show();
//		                            进入主界面，保存用户信息
                                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_phone, lgname);
                                    SharePrefUtil.saveString(getActivity(), SharePrefUtil.sp_pwd, mempsw);
                                    mApplication.mUserPhone = lgname;
                                    mApplication.mUserMima = mempsw;
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("gestureLoginSuccess", true);
                                    startActivity(intent);
                                    mLockPatternView.clearPattern();

//                                    if (getActivity() instanceof MainActivity){
//                                        ft = getActivity().getSupportFragmentManager().beginTransaction();
//
//                                        mineFragment = new MineFragment();
//
//                                        ft.replace(R.id.fl, mineFragment);
//
//                                        ft.commit();
//                                    }else {
//                                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                                        startActivity(intent);
//                                        getActivity().finish();
//                                    }

                                } else {
                                    Toast.makeText(getActivity(), "密码错误，请重新输入!", Toast.LENGTH_SHORT).show();
                                    mLockPatternView.clearPattern();
                                    Message message = new Message();
                                    message.what = INPUT_SECOND_GESTURE;
                                    handler.sendMessage(message);
                                }

                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    public FragmentmineGestureLogin(String lgName, String mempsw) {
        this.lgname = lgName;
        this.mempsw = mempsw;
    }

    public void setNamePwd(String lgName, String mempsw){
        this.lgname = lgName;
        this.mempsw = mempsw;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_gestrue_layout, container, false);
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

// 获取软键盘的显示状态
//        boolean isOpen = imm.isActive();

// 如果软键盘已经显示，则隐藏，反之则显示
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        if (getActivity() instanceof MainActivity) {
            Log.d(TAG, "FragmentmineGestureLogin里面的Activity是MainActivity");
            MainActivity activity = (MainActivity) getActivity();
            activity.rg_main.setVisibility(View.GONE);
        }
//        if (getActivity() instanceof LoginActivity) {
//            Log.d(TAG, "FragmentmineGestureLogin里面的Activity是LoginActivity");
//            LoginActivity activity = (LoginActivity) getActivity();
//            activity.holeView.getChildAt(1).setVisibility(View.GONE);
//            activity.holeView.getChildAt(0).setVisibility(View.VISIBLE);
//        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        mLockPatternUtils = new LockPatternUtils(getActivity());
        mGestureTex = (TextView) findViewById(R.id.gestrue_text);
        //第一次输入手势密码
//        mGestureTex.setText("请输入手势密码");
        mGestureTex.setText(getString(R.string.lockpattern_recording_intro_header));
        tv_gesture_forgetMima.setVisibility(View.GONE);
        iv_gesture_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentmineLogin gestureFragment = new FragmentmineLogin();
                ft.replace(R.id.fl, gestureFragment);
                ft.commit();
            }
        });


    }

    @Override
    protected void initEvents() {
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
//                检查手势密码输入情况
                if (pattern == null) {
                    return;
                }
//                检查手势密码是否正确
//               如果小于四个点
                if (pattern.size() < 4) {
//                    mGestureTex.setText("最少连接4个点，请重新输入！");
                    mGestureTex.setText(getString(R.string.lockpattern_recording_incorrect_too_short));
                    mGestureTex.setTextColor(Color.RED);
//                    清除已经划到的痕迹
                    mLockPatternView.clearPattern();
                    return;
                } else {        //符合长度要求
//                    保存pattern到本地文件
                    SharePrefUtil.saveString(getActivity(), "pattern", pattern.toString());
                    mLockPatternView.clearPattern();
//                    发送消息第二次确定密码
                    Message message = new Message();
                    message.what = INPUT_SECOND_GESTURE;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
