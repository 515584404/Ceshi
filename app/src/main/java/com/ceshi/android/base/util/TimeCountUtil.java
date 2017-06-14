package com.ceshi.android.base.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.ceshi.android.R;


/**
 * Created by Mark on 2016/12/1.
 * 倒计时工具类
 */
public class TimeCountUtil extends CountDownTimer {

    private Activity mActivity;
    private TextView txt;//按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil(Activity mActivity, long millisInFuture, long countDownInterval, TextView txt) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.txt = txt;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        txt.setClickable(false);//设置不能点击
        txt.setText(millisUntilFinished / 1000 + "s");//设置倒计时时间
        Log.d("TAG", "btn值===: "+txt.getText().toString());
        txt.setBackground(mActivity.getResources().getDrawable(R.drawable.edittext_background_code));

//        Spannable span = new SpannableString(txt.getText().toString());//获取按钮的文字
//        span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色tim
//        txt.setText(span);
        txt.setTextColor(Color.WHITE);

    }

    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        txt.setText("重新获取");
        txt.setClickable(true);//重新获得点击
        Log.d("TAG", "txt*********"+txt.getText().toString());
        txt.setTextColor(Color.WHITE);
        txt.setBackground(mActivity.getResources().getDrawable(R.drawable.btn_bg));//还原背景色

    }
}
