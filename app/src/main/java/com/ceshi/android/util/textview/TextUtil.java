package com.ceshi.android.util.textview;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View.OnClickListener;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;


public class TextUtil {

    /**
     * 获取字符串显示宽度，与字体有关
     * @param text
     * @param Size
     * @return
     */
    public static float GetTextWidth(String text, float Size) {
        //第一个参数是要计算的字符串，第二个参数是字提大小
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(Size);
        return FontPaint.measureText(text);
    }
	/**
	 * 添加下划线
	 * 
	 * @param context
	 *            上下文
	 * @param textView
	 *            添加下划线的TextView
	 * @param start
	 *            添加下划线开始的位置
	 * @param end
	 *            添加下划线结束的位置
	 */
	public static void addUnderlineText(final Context context,
                                        final HandyTextView textView, final int start, final int end) {
		textView.setFocusable(true);
		textView.setClickable(true);
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
				textView.getText().toString().trim());
		spannableStringBuilder.setSpan(new UnderlineSpan(), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(spannableStringBuilder);
	}
	
	/**
	 * 添加超链接
	 * 
	 * @param textView
	 *            超链接的TextView
	 * @param start
	 *            超链接开始的位置
	 * @param end
	 *            超链接结束的位置
	 * @param listener
	 *            超链接的单击监听事件
	 */
	public static void addHyperlinks(final HandyTextView textView,
			final int start, final int end, final OnClickListener listener) {

		String text = textView.getText().toString().trim();
		SpannableString sp = new SpannableString(text);
		sp.setSpan(new com.ceshi.android.util.textview.IntentSpan(listener), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(textView.getContext().getResources()
				.getColor(R.color.black)), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(sp);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

	}
}
