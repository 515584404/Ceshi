package com.ceshi.android.util.textview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;


/**
 * 超链接
 * @author killer
 *
 */
public class IntentSpan extends ClickableSpan {
	private final OnClickListener mOnClickListener;

	public IntentSpan(OnClickListener listener) {
		mOnClickListener = listener;
	}

	public void onClick(View view) {
		mOnClickListener.onClick(view);
	}

	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);
		ds.setUnderlineText(true);
	}
}
