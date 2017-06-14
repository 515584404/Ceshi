package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ceshi.android.R;
import com.ceshi.android.adapter.MessageAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.MessageEntity;
import com.ceshi.android.ui.fragment.FragmentMessageDetail;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/05/10.
 */
@SuppressLint("ValidFragment")
public class FragmentMessage extends BaseFragment {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_message)
    PullToRefreshListView lv_message;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private List<MessageEntity.UserMessagesEntity> mData = new ArrayList<>();
    private MessageAdapter mAdapter;
    //消息中心
    // public static String MessageCenter=AppUrl+"app/user/messages";
    private FrameLayout fl;
    private FragmentTransaction ft;
    private FragmentMessageDetail fragment;
    private int totalPage;
    private int currentPage = 1;
    @Bind(R.id.ll_zhuce)
    LinearLayout ll_zhuce;


    public FragmentMessage(FrameLayout fl) {
        this.fl = fl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        ll_zhuce.setVisibility(View.GONE);
        widget_title.setText("消息中心");
        lv_message.setEmptyView(tv_listview_empty);
        mAdapter = new MessageAdapter(mData, getActivity(), fl);
        lv_message.setAdapter(mAdapter);
        messageData(currentPage, true);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        lv_message.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        lv_message.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                messageData(currentPage, true);
                mAdapter.notifyDataSetChanged();
                new FinishRefresh().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = currentPage + 1;
                if (currentPage <= totalPage) {
                    messageData(currentPage, false);
                    mAdapter.notifyDataSetChanged();
                }
                new FinishRefresh().execute();
            }
        });
    }

    private void init() {
        ILoadingLayout startLabels = lv_message
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = lv_message.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一
    }


    private void messageData(int currentPage, final boolean isDelete) {
        RequestParams requestParams = new RequestParams(Const.MessageCenter);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("page", currentPage + "");

//        showLoadingDialog(getString(R.string.data));
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }

                Gson gson = new Gson();
                final MessageEntity mResult = gson.fromJson(result, MessageEntity.class);
                totalPage = mResult.getTotalpage();
                if (isDelete) {
                    mData.removeAll(mData);
                }
                mData.addAll(mResult.getUserMessages());
                Log.e(TAG, "onSuccess: "+mResult.getUserMessages());
                mAdapter.notifyDataSetChanged();
                final List<MessageEntity.UserMessagesEntity> data = mResult.getUserMessages();
                lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ft = getActivity().getSupportFragmentManager().beginTransaction();
                        fragment = new FragmentMessageDetail(data, fl, position - 1);
                        ft.replace(R.id.fl, fragment);
                        ft.commit();
                    }
                });


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "FragmentMessage的messageData异常" + throwable.toString());
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
    protected void initEvents() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            lv_message.onRefreshComplete();
        }
    }
}
