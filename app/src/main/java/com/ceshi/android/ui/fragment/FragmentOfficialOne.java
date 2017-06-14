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
import com.ceshi.android.adapter.OfficialAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.model.AnnouncementsData;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/03.
 */
public class FragmentOfficialOne extends BaseFragment {
    @Bind(R.id.lv_official)
    ListView lv_official;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private OfficialAdapter adapter;
    private List<AnnouncementsData.AnnouncementsEntity> listData=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_official_one, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        lv_official.setEmptyView(tv_listview_empty);
        officialData();
        adapter=new OfficialAdapter(listData,getActivity());
        lv_official.setAdapter(adapter);
    }
    private void officialData() {
        RequestParams requestParams = new RequestParams(Const.Announcements);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("page", 1+"");

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson=new Gson();
                AnnouncementsData data=gson.fromJson(result.toString(),AnnouncementsData.class);
                listData.removeAll(listData);
                //将数据添加到实体
                listData.addAll(data.getAnnouncements());
                // System.out.println("lllllllllllllllllllllll"+mData);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showShortToast("网络异常，请连接网络！");
                Log.i("xUtilsError","FragmentOfficialOne 的 officialData 异常"+throwable.toString());
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
}
