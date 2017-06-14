package com.ceshi.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.adapter.RedPacketFragmentAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.ui.RedPacketActivity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mac on 2017/6/12.
 * 红包
 */

public class RedPacketFragment extends BaseFragment {

    @Bind(R.id.redpacket_lv_listview)
    ListView mListview;
    @Bind(R.id.redpacket_srl_swiperefresh)
    SwipeRefreshLayout mSwiperefresh;
    private RedPacketFragmentAdapter adapter;
    private ArrayList<String> redPackets = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_redpacket, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

        getRedPacket();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    String url = "http://192.168.1.115:8080/fensantouziweb/activity/getUserRedPackage";

    //获取红包数据
    private void getRedPacket() {
//        ((RedPacketActivity)mActivity).mobile
        Log.e(TAG, "红包fragment中获取的电话号码getRedPacket: " + ((RedPacketActivity) mActivity).mobile);
        showShortToast("从网络获取红包数据");

        RequestParams requestParams = new RequestParams(url);
//        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("phone",((RedPacketActivity)mActivity).mobile);

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "onSuccess: "+result);
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    if (mSwiperefresh.isRefreshing()) {
                        mSwiperefresh.setRefreshing(false);
                    }
                    return;
                }
                // 解析图片信息
                Gson gson = new Gson();
//                mPicResult = gson.fromJson(loadedImagesFromWeb, MainPictureEntity.class);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
//                showShortToast("网络异常，请连接网络！");
                if (mSwiperefresh.isRefreshing()) {
                    mSwiperefresh.setRefreshing(false);
                }
                Log.i("xUtilsError", "RedPacketFragment 的 requestRedPacket 异常" + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {
//                showShortToast("网络请求取消！");
                if (mSwiperefresh.isRefreshing()) {
                    mSwiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFinished() {
                if (mSwiperefresh.isRefreshing()) {
                    mSwiperefresh.setRefreshing(false);
                }
            }
        });
    }


    @Override
    protected void initViews() {
        mSwiperefresh.setColorSchemeResources(R.color.new_color);
        if (adapter == null) {
            adapter = new RedPacketFragmentAdapter(mContext, redPackets);
            mListview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }


        mSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                if (mSwiperefresh.isRefreshing()) {
                    mSwiperefresh.setRefreshing(false);
                }
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
}
