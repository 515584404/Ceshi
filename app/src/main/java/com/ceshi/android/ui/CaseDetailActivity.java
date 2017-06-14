package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.ZqDetail;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;

/**
 * Created by ztr on 2016/04/26.
 * 项目详情
 */
public class CaseDetailActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.detail_zname)
    HandyTextView detail_zname;
    @Bind(R.id.detail_lilv)
    HandyTextView detail_lilv;
    @Bind(R.id.detail_re)
    HandyTextView detail_re;
    @Bind(R.id.detail_hk)
    HandyTextView detail_hk;
    @Bind(R.id.detail_time)
    HandyTextView detail_time;
    @Bind(R.id.detail_qx)
    HandyTextView detail_qx;
    @Bind(R.id.detail_yongtu)
    HandyTextView detail_yongtu;
    @Bind(R.id.detail_xb)
    HandyTextView detail_xb;
    @Bind(R.id.detail_nl)
    HandyTextView detail_nl;
    @Bind(R.id.detail_zh)
    HandyTextView detail_zh;
    @Bind(R.id.detail_qk)
    HandyTextView detail_qk;
    private int zqId;
    private RequestQueue mQueue;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        super.onCreate(savedInstanceState);
        widget_title.setText("项目详情");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        zqId = intent.getIntExtra("id", -1);
        if (zqId == -1) {
            zqId = intent.getIntExtra("creditId", -1);
        }
        Log.d("TAG", "项目详情：id" + zqId);
        detailZq();
    }

    private void detailZq() {
        String Url = Const.AppUrl + "app/credit/credit/" + zqId;
        RequestParams requestParams = new RequestParams(Url);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("{creditId}", zqId + "");

        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null) {
                    showShortToast("获取数据失败！");
                    return;
                }

                Log.d(TAG, "项目详情result: "+result.toString());
                Gson gson = new Gson();
                final ZqDetail mResult = gson.fromJson(result, ZqDetail.class);
                Log.d(TAG, "项目详情mResult: "+mResult.toString());
                //债权名称
                detail_zname.setText(mResult.getTitle() + "");
                //利率
                detail_lilv.setText(mResult.getRate() + "%");
                //融资金额
                detail_re.setText("￥" + mResult.getOwnMoney());
                //还款来源
                detail_hk.setText(mResult.getRepayFrom() + "");
                //还款日期
                detail_time.setText(mResult.getRepayTime() + "");
                //期限
                detail_qx.setText(mResult.getDuration() + "个月");
                //借款用途
                detail_yongtu.setText(mResult.getLoanPurpose() + "");
                //个人信息
                String s = mResult.getLoanerInfo().toString();
                if(s.contains("格式:")){
                    s=s.replace("格式:","");
                }
                String spStr[] = s.split("\\:|,");
                Log.d(TAG, "spStr[]: "+spStr.length);
                if (spStr.length>3){
                    detail_xb.setText(spStr[3]);
                }
                if (spStr.length>5){
                    detail_zh.setText(spStr[5]);
                }
//                String a = spStr[7].replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                if (spStr.length>7){
                    detail_nl.setText(spStr[7]);
                }
                System.out.println(",,,,," + spStr[7]);
                if (spStr.length>9){
                    detail_qk.setText(spStr[9]);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","CaseDetailActivity 的 detailZq 异常"+throwable.toString());
                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
                dismissLoadingDialog();
            }
        });

    }

    @Override
    protected void initEvent() {

    }


}
