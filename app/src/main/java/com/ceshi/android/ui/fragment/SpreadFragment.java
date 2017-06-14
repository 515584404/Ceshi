package com.ceshi.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.adapter.SpreadAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.SpreadEntity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create by chen on 2017/6/12
 */

public class SpreadFragment extends BaseFragment {

    @Bind(R.id.lv_tuiguang)
    ListView lv_tuiguang;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;

    private List<SpreadEntity.RecordsBean> list = new ArrayList<>();
    private SpreadAdapter mAdapter;
    private SpreadEntity spreadEntity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_spread, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {

        lv_tuiguang.setEmptyView(tv_listview_empty);
        mAdapter = new SpreadAdapter(list, mActivity);
        lv_tuiguang.setAdapter(mAdapter);
        spreadData();
    }

    private void spreadData() {
        RequestParams requestParams = new RequestParams(Const.SpreadList);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        //  requestParams.addBodyParameter("money",money);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("数据为空！");
                    return;
                }

                Gson gson = new Gson();
                SpreadEntity mResult = gson.fromJson(result, SpreadEntity.class);
                if (mResult == null) {
                    return;
                }
                System.out.println("mmmmm" + mResult);
                list.removeAll(list);
                list.addAll(mResult.getRecords());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "SpreadActivity 的 spreadData 异常" + throwable.toString());
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
    protected void initEvents() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
