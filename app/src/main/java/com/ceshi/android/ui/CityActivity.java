package com.ceshi.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ceshi.android.R;
import com.ceshi.android.adapter.CityAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.CityEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/13.
 */
public class CityActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_bank)
    ListView lv_city;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private String provinceCode;
    private CityAdapter mAdapter;
    private List<CityEntity> mData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_card);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        final Intent intent=getIntent();
        provinceCode=intent.getStringExtra("provinceCode");
        widget_title.setText("城市列表");
        lv_city.setEmptyView(tv_listview_empty);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("cityName","城市");
                intent.putExtra("cityCode","");
                setResult(2,intent);
                finish();
            }
        });
        mAdapter=new CityAdapter(this,mData);
        lv_city.setAdapter(mAdapter);
        cityData();
    }
    private void cityData() {
        RequestParams requestParams = new RequestParams(Const.Cities);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("provinceCode",provinceCode);

        showLoadingDialog("数据加载中...");

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result==null)
                {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(result).getAsJsonArray();
                ArrayList<CityEntity> lcs = new ArrayList<CityEntity>();
                for (JsonElement obj : Jarray) {
                    CityEntity cse = gson.fromJson(obj, CityEntity.class);
                    lcs.add(cse);
                }
                mData.removeAll(mData);
                //将数据添加到实体
                mData.addAll(lcs);
                System.out.println("lllllllllllllllllllllll" + mData);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","CityActivity 的 cityData 异常"+throwable.toString());
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
