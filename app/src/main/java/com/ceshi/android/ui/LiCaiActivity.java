package com.ceshi.android.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ceshi.android.R;
import com.ceshi.android.adapter.LicaiAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.LiCaiEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/01. item_licai 投资记录
 */
public class LiCaiActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_tuiguang)
    PullToRefreshListView lv_licai;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private LicaiAdapter mAdapter;
    private int totalPage;
    private int currentPage = 1;
    private List<LiCaiEntity.RecordsEntity> mData = new ArrayList<>();
@Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_spread);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("投资记录");
        ll_zhuce.setVisibility(View.GONE);
        lv_licai.setEmptyView(tv_listview_empty);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new LicaiAdapter(mData, this);//TODO:adapter和licaiData相互换行
        licaiData(currentPage, true);
        lv_licai.setAdapter(mAdapter);
        lv_licai.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        lv_licai.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                licaiData(currentPage, true);
                new FinishRefresh().execute();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = currentPage + 1;
                if (currentPage <= totalPage) {
                    licaiData(currentPage, false);
                    mAdapter.notifyDataSetChanged();
                }
                new FinishRefresh().execute();
            }
        });
    }


    private void init() {
        ILoadingLayout startLabels = lv_licai
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_licai.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一
    }


    private void licaiData(int currentPage, final boolean isDelete) {
        RequestParams requestParams = new RequestParams(Const.LiCaiList);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("page", currentPage + "");
//        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络请求失败！");
                    return;
                }
                Gson gson = new Gson();
                final LiCaiEntity mResult = gson.fromJson(result, LiCaiEntity.class);
                if (mResult == null) {
                    showShortToast("网络请求失败！");
                    return;
                }
                totalPage = mResult.getTotalPage();
                if (isDelete) {
                    mData.removeAll(mData);
                }
                mData.addAll(mResult.getRecords());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","LiCaiActivity 的 licaiData 异常"+throwable.toString());
                showShortToast("网络请求失败！");
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
            lv_licai.onRefreshComplete();
        }
    }
}
