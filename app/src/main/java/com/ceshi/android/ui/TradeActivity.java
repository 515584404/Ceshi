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
import com.ceshi.android.adapter.TradeAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.TradeEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/01. item_trade  交易流水
 */
public class TradeActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_tuiguang)
    PullToRefreshListView lv_trade;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;
    private TradeAdapter mAdapter;
    private int totalPage;
    private int currentPage=1;
    private List<TradeEntity.RecordsEntity> mData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_spread);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("交易流水");
        ll_zhuce.setVisibility(View.GONE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_trade.setEmptyView(tv_listview_empty);
        tradeData(currentPage,true);
        mAdapter=new TradeAdapter(mData,this);
        lv_trade.setAdapter(mAdapter);
        lv_trade.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        lv_trade.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                tradeData(currentPage,true);
                new FinishRefresh().execute();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage=currentPage+1;
                if (currentPage <= totalPage){
                    tradeData(currentPage,false);
                    mAdapter.notifyDataSetChanged();
                }
                new FinishRefresh().execute();
            }
        });
    }

    private void init() {
        ILoadingLayout startLabels = lv_trade
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_trade.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一
    }


    private void tradeData(int currentPage,final boolean isDelete) {
        RequestParams requestParams = new RequestParams(Const.TradeList);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("page",currentPage+"");

//        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                final TradeEntity mResult = gson.fromJson(result, TradeEntity.class);
                if(mResult==null)
                {
                    return;
                }
                if (isDelete){//上拉刷新的时候，需要把原来的数据全部清空
                    mData.removeAll(mData);
                }
                totalPage = mResult.getTotalPage();
                mData.addAll(mResult.getRecords());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","SpreadActivity 的 spreadData 异常"+throwable.toString());
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
            lv_trade.onRefreshComplete();
        }
    }
}
