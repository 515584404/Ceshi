package com.ceshi.android.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 一个继承于TextView的View
 * @author killer
 * Created by killer on 15/1/6.
 */
public class HandyTextView extends TextView {

    public HandyTextView(Context context) {
        super(context);
    }

    public HandyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HandyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null) {
            text = "";
        }
        super.setText(text, type);
    }
}