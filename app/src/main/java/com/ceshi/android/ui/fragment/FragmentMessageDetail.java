package com.ceshi.android.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.entity.MessageEntity;
import com.ceshi.android.model.ResultVoNoData;

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
public class FragmentMessageDetail extends BaseFragment {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.tv_title)
    TextView tv_title;
    /*@Bind(R.id.tv_time)
    TextView tv_time;*/
    @Bind(R.id.webv_content)
    WebView webv_content;
    private int id;
    private FrameLayout fl;
    private FragmentTransaction ft;
    private com.ceshi.android.ui.fragment.FragmentMessage fragment;

    int position;
    private List<MessageEntity.UserMessagesEntity> data=new ArrayList<>();

    public FragmentMessageDetail(List<MessageEntity.UserMessagesEntity> data, FrameLayout fl, int position) {
        this.data = data;
        this.fl = fl;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message_detail, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        widget_title.setText("消息中心");
        id=data.get(position).getId();
        tv_title.setText(data.get(position).getTitle());
        /*tv_time.setText(data.get(position).getCreateTime());*/
        webv_content.loadData(data.get(position).getContent(), "text/html; charset=UTF-8", "UTF-8");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                fragment = new com.ceshi.android.ui.fragment.FragmentMessage(fl);
                ft.replace(R.id.fl, fragment);
                ft.commit();
            }
        });
        readMessage();
    }

//    @Override
//    public void onDetach() {
//        ft = getActivity().getSupportFragmentManager().beginTransaction();
//        fragment = new FragmentMessage(fl);
//        ft.replace(R.id.fl, fragment);
//        ft.commit();
//        super.onDetach();
//    }

    private void readMessage() {
        RequestParams requestParams = new RequestParams(Const.ReadMessage);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent","android");
        requestParams.addBodyParameter("messageId", id+"");
        showLoadingDialog("Loading...");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result == null || "".equals(result)){
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Gson gson = new Gson();
                ResultVoNoData mResult=gson.fromJson(result,ResultVoNoData.class);
                if (mResult != null && mResult.isResult()) {
                    Log.d(TAG,"FragmentMessageDetail里面的度读消息成功了");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError","FragmentMessageDetail的readMessage异常"+throwable.toString());
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
