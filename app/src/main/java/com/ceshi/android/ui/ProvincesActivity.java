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
import com.ceshi.android.adapter.ProvincesAdapter;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.ProvincesEntity;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/13.
 */
public class ProvincesActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.lv_bank)
    ListView lv_province;
    @Bind(R.id.tv_listview_empty)
    TextView tv_listview_empty;
    private List<ProvincesEntity> mData=new ArrayList<>();
    private ProvincesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_card);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        widget_title.setText("省份列表");
        lv_province.setEmptyView(tv_listview_empty);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("provinceCode","");
                intent.putExtra("provinceName","省份");
                setResult(0,intent);
                finish();
            }
        });
        mAdapter=new ProvincesAdapter(this,mData);
        lv_province.setAdapter(mAdapter);
        provinceData();
    }
    private void provinceData() {
        RequestParams requestParams = new RequestParams(Const.Provinces);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        showLoadingDialog("数据加载中...");
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray Jarray = parser.parse(result).getAsJsonArray();
                ArrayList<ProvincesEntity> lcs = new ArrayList<ProvincesEntity>();
                for (JsonElement obj : Jarray) {
                    ProvincesEntity cse = gson.fromJson(obj, ProvincesEntity.class);
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
                Log.i("xUtilsError","ProvincesActivity 的 provinceData 异常"+throwable.toString());
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
    @Override
    public void onBackPressed()
    {

        setResult(0);
        super.onBackPressed();
    }
}
