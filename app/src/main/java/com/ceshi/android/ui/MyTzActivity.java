package com.ceshi.android.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ceshi.android.R;
import com.ceshi.android.adapter.TzAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.InvestProduceEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/26. 我要投资 fragment_main_item
 */
public class MyTzActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.lv_tz)
    PullToRefreshListView lv_tz;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    @Bind(R.id.widget_title)
    TextView widgetTitle;
    private List<InvestProduceEntity.ProductsEntity> mData = new ArrayList<>();
    private TzAdapter mAdapter;
    private int currentPage = 1;
//    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tz);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() throws IOException {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myTzData();
        widgetTitle.setText("我要投资");
        lv_tz.setEmptyView(tv_listview_empty);
        mAdapter = new TzAdapter(this, mData);
        tzData(currentPage, true);
        lv_tz.setAdapter(mAdapter);
        lv_tz.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        lv_tz.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                tzData(currentPage, true);
                new FinishRefresh().execute();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = currentPage + 1;
//                if (currentPage <= totalPage) {
                tzData(currentPage, false);
                mAdapter.notifyDataSetChanged();
//                }
                new FinishRefresh().execute();
            }
        });
    }

    private void init() {
        ILoadingLayout startLabels = lv_tz.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_tz.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一
    }

    private void myTzData() {
    }

    private void tzData(int currentPage, final boolean isDelete) {
        RequestParams requestParams = new RequestParams(Const.MyTzList);
        Log.d(TAG, "我要投资界面requestParams: "+requestParams.toString());
        //requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent","android");
//        requestParams.addBodyParameter("page",1+"");
        requestParams.addBodyParameter("page", currentPage + "");
//        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "我要投资界面result: "+result.toString());
                if (result == null || "".equals(result)) {
                    showShortToast("网络请求失败！");
                    return;
                }
                Gson gson = new Gson();
                InvestProduceEntity mResult = gson.fromJson(result, InvestProduceEntity.class);
                Log.d("TAG", "我要投资界面mResult: "+mResult);
                if (mResult == null) {
                    return;
                }
//                mData.removeAll(mData);
                //将数据添加到实体
                if (isDelete) {
                    mData.removeAll(mData);
                }
                mData.addAll(mResult.getProducts());
                Log.d("TAG", "mData值： "+mData);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","MyTzActivity 的 initView 异常"+throwable.toString());
                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
//                dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void initEvent() {

    }
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//          adapter.notifyDataSetChanged();
            lv_tz.onRefreshComplete();
        }
    }
}
