package com.ceshi.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ceshi.android.R;
import com.ceshi.android.adapter.JiaInterestFragmentAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.ui.RedPacketActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mac on 2017/6/12.
 * 加息券
 */

public class JiaInterestFragment extends BaseFragment {

    @Bind(R.id.jiainterest_lv_listview)
    ListView mListview;
    @Bind(R.id.jiainterest_srl_swiperefresh)
    SwipeRefreshLayout mSwiperefresh;

    private JiaInterestFragmentAdapter adapter;
    private ArrayList<String> jiaInterests = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_jiainterest, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

        getJiaInterest();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    String url = "http://192.168.1.128:8081/fensantouziweb/user/account/myRedPackages";

    //获取加息券数据
    private void getJiaInterest() {
//        ((RedPacketActivity)mActivity).mobile
        Log.e(TAG, "加息券fragment中获取的电话号码getRedPacket: " + ((RedPacketActivity) mActivity).mobile);
        showShortToast("从网络获取加息券数据");
    }

    @Override
    protected void initViews() {
        mSwiperefresh.setColorSchemeResources(R.color.new_color);
        if (adapter == null) {
            adapter = new JiaInterestFragmentAdapter(mContext, jiaInterests);
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
