package com.ceshi.android.base.view;

import android.content.Context;
import android.view.View;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseDialog;


/**
 * Created by killer on 16/1/10.
 */
public class LoadingDialog extends BaseDialog implements View.OnClickListener{
    private com.ceshi.android.base.view.HandyTextView mHtvText;
    private String mText;

    public LoadingDialog(Context context, String text) {
        super(context);
        mText=text;
        init();
    }
    private void init() {
        setContentView(R.layout.base_common_loading_diloag);
        mHtvText = (com.ceshi.android.base.view.HandyTextView) findViewById(R.id.loadingdialog_htv_text);
        mHtvText.setText(mText);
    }
    public void setText(String text) {
        mText = text;
        mHtvText.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
