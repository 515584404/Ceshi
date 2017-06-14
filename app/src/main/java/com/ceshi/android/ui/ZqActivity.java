package com.ceshi.android.ui;

import android.content.Intent;
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
import com.ceshi.android.adapter.TzZqListAdapter;
import com.ceshi.android.adapter.ZqListAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.TzZqEntity;
import com.ceshi.android.entity.ZqListEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/04/26.
 * 债权列表界面
 */
public class ZqActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_zq)
    PullToRefreshListView lv_zq;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private int zid;
    private int investRecordId;
    private List<ZqListEntity.RecordsEntity> list = new ArrayList<>();

    private ZqListAdapter mAdapter;
    private List<TzZqEntity.RecordsEntity> mData = new ArrayList<>();
    private TzZqListAdapter adapter;
    private int currentPage = 1;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zq);
        super.onCreate(savedInstanceState);
        widget_title.setText("投资债权分配列表");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        zid = intent.getIntExtra("id", -1);
        investRecordId = intent.getIntExtra("tid", -1);
        System.out.println("iii" + investRecordId);
        if (zid == -1) {
            lzqList();
            adapter = new TzZqListAdapter(mData, this);
            lv_zq.setAdapter(adapter);
        } else {
            zqList(currentPage, true);
            mAdapter = new ZqListAdapter(list, ZqActivity.this);
            Log.d(TAG, "ZqActivity里面的数据：list=" + list);
            lv_zq.setAdapter(mAdapter);
        }
        lv_zq.setEmptyView(tv_listview_empty);
        lv_zq.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        lv_zq.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                if (zid == -1) {
                    lzqList();
                } else {
                    zqList(currentPage, true);
                    mAdapter.notifyDataSetChanged();
                    new FinishRefresh().execute();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = currentPage + 1;
                if (currentPage <= totalPage) {
                    if (zid == -1) {
                        lzqList();
                    } else {
                        zqList(currentPage, false);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                new FinishRefresh().execute();
            }
        });
    }

    private void init() {
        ILoadingLayout startLabels = lv_zq
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_zq.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一
    }


    @Override
    protected void initView() {

    }

    //投资记录债权分配列表
    private void lzqList() {
        RequestParams requestParams = new RequestParams(Const.TzZqList);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("investRecordId", investRecordId + "");
//        showLoadingDialog("Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                final TzZqEntity mResult = gson.fromJson(result, TzZqEntity.class);
                if (mResult == null) {
                    return;
                }
                mData.removeAll(mData);
                mData.addAll(mResult.getRecords());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ZqActivity 的 lzqList 异常"+throwable.toString());
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

    //产品债权列表
    private void zqList(int currentPage, final boolean isDelete) {
        RequestParams requestParams = new RequestParams(Const.ZqList);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("page", currentPage + "");
        requestParams.addBodyParameter("productId", zid + "");
//        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                Gson gson = new Gson();
                final ZqListEntity mResult = gson.fromJson(result, ZqListEntity.class);
                totalPage = mResult.getTotalPage();
                Log.d(TAG, "ZqActivity里面的mResult=" + mResult);
                if (isDelete) {
                    list.removeAll(list);
                }
                list.addAll(mResult.getRecords());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","ZqActivity 的 zqList 异常"+throwable.toString());
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
            lv_zq.onRefreshComplete();
        }


    }
}
