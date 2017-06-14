package com.ceshi.android.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ceshi.android.R;
import com.ceshi.android.adapter.MainFragmentAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.MyListView;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.base.view.LoadingDialog;
import com.ceshi.android.entity.MainMessageEntity;
import com.ceshi.android.entity.MainPictureEntity;
import com.ceshi.android.entity.MainProduceEntity;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.MainSafeDetailActivity;
import com.ceshi.android.ui.MainmessageDetail;
import com.ceshi.android.ui.ProductDetailActivity;
import com.ceshi.android.ui.RegisterActivity;
import com.ceshi.android.ui.WebViewActivity;
import com.ceshi.android.util.ServicePhoneDialog;
import com.recker.flybanner.FlyBanner;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 * 首页
 */
public class MainFragment extends BaseFragment {
    @Bind(R.id.lv_main)
    MyListView lv_main;
    @Bind(R.id.iv_zhuce)
    ImageView iv_zhuce;
    @Bind(R.id.sv)
    ScrollView sv;
    @Bind(R.id.tv_message)
    HandyTextView tv_message;
    @Bind(R.id.ll_gs)
    LinearLayout ll_gs;
    @Bind(R.id.mainfragment_ll_safedetail)
    LinearLayout mainfragment_ll_safedetail;// 首页三个图片条目
    @Bind(R.id.flyBanner)
    FlyBanner mFlyBanner;
    //    @Bind(R.id.widget_title)
//    TextView widgetTitle;
    //private PagerIndicator indicator;
    //private SliderLayout mSliderLayout;
    private FragmentActivity mActivity;
//    private FlyBanner mFlyBanner;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MainFragmentAdapter mAdapter;
    private MainMessageEntity mResult;
    // 从网络请求的response
    private String loadedImagesFromWeb;
    private String pageUrl;

    MainPictureEntity mPicResult;

    protected LoadingDialog mLoadingDialog;
    private List<MainProduceEntity.PeriodicalProductsBean> ll;
    //开启线程池
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    /**
     * 保存JSONArray字符串
     *
     * @return
     */
    private String getSavedJSONArray(List<MainPictureEntity.LoadImagesBean> list) {
        if (list.size() > 0) {
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                MainPictureEntity.LoadImagesBean loadImagesBean = list.get(i);
                map.put("fileName", loadImagesBean.getFileName());
                map.put("fileUrl", loadImagesBean.getFileUrl());
                map.put("lastModifyTime", loadImagesBean.getLastModifyTime());
                map.put("pageUrl", loadImagesBean.getPageUrl());
                dataList.add(map);
            }
            return new Gson().toJson(dataList);
        }
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        mActivity = getActivity();

        //首页产品列表
        mainProduce();
        //请求轮播图片
        requestFoundPic();
        //首页公告
        mainMessage();


        return super.onCreateView(inflater, container, savedInstanceState);
    }



    //    请求轮播图片
    private void requestFoundPic() {
        String loadedImages = SharePrefUtil.getString(mActivity, "mainloadedImages", "");
        Log.d("TAG", "参数：=======" + loadedImages);

        RequestParams requestParams = new RequestParams(Const.MainPicture);
//        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("loadedImages", loadedImages);
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    return;
                }
                loadedImagesFromWeb = result;//网络请求JSONObject
                Log.d("TAG", "发现图片信息成功：" + loadedImagesFromWeb);
                // 解析图片信息
                Gson gson = new Gson();
                mPicResult = gson.fromJson(loadedImagesFromWeb, MainPictureEntity.class);
                //2.获取保存到本地的图片的数组
                String mainloadedImages = SharePrefUtil.getString(mActivity, "mainloadedImages", "");
                //2.1如果长度为空(表示没有要增加或者删除的),表示第一次使用软件，把图片资源保存到本地就行了。
                List<MainPictureEntity.LoadImagesBean> picList;
                if ("".equals(mainloadedImages)) {
                    //从loadedImagesFromWeb中截取loadImages作为JSONArray保存到本地文件中。
                    String loadImages = getSavedJSONArray(mPicResult.getLoadImages());
                    Log.d("TAG", "MainFragment里面的loadImages001=" + loadImages);
                    SharePrefUtil.saveString(mActivity, "mainloadedImages", loadImages);
                    picList = mPicResult.getLoadImages();
                } else {
                    picList = gson.fromJson(mainloadedImages, new TypeToken<List<MainPictureEntity.LoadImagesBean>>() {
                    }.getType());
                    //删除已经过时的图片资源
                    if (picList.size() >= mPicResult.getDeletedImages().size()) {
                        for (int i = 0; i < mPicResult.getDeletedImages().size(); i++) {
                            if (picList.get(i).getFileName().equals(mPicResult.getDeletedImages().get(i).getFileName())) {
                                picList.remove(i);
                            }
                        }
                    }
                    //添加新增的图片资源
                    for (int i = 0; i < mPicResult.getLoadImages().size(); i++) {
                        MainPictureEntity.LoadImagesBean loadImagesBean = mPicResult.getLoadImages().get(i);
                        picList.add(loadImagesBean);
                    }
                    String loadImages = getSavedJSONArray(picList);
                    Log.d("TAG", "MainFragment里面的loadImages002=" + loadImages);
                    SharePrefUtil.saveString(mActivity, "mainloadedImages", loadImages);
                }

                Log.d("TAG", "首页轮播图标题及图片个数：" + picList.size());
                List<String> imagesUrl = new ArrayList<>();

                for (int i = 0; i < picList.size(); i++) {
                    pageUrl = picList.get(i).getPageUrl();
                    String imageUrl = picList.get(i).getFileUrl();
                    imagesUrl.add(imageUrl);

                }
                mFlyBanner.setImagesUrl(imagesUrl);
                Log.d(TAG, "图片imageUrl: " + imagesUrl);
                Log.d(TAG, "图片mFlyBanner: " + mFlyBanner);
                Log.d(TAG, "图片pageUrl: " + pageUrl);
                mFlyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pageUrl", pageUrl);
                        startActivity(WebViewActivity.class, bundle);
                    }
                });

            }


            @Override
            public void onError(Throwable throwable, boolean b) {
//                showShortToast("网络异常，请连接网络！");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Log.i("xUtilsError", "MainFragment 的 requestFoundPic 异常" + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {
//                showShortToast("网络请求取消！");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFinished() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    @Override
    protected void initViews() {
        Log.d("TAG", "initViews");
        iv_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.class);
                mActivity.finish();
            }
        });
        lv_main.setSelection(ListView.FOCUS_UP);

        mLoadingDialog = new LoadingDialog(getContext(), "Loading...");

        //条目点击事件
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(mActivity, "条目点击事件", Toast.LENGTH_SHORT).show();
                String lgname = SharePrefUtil.getString(mContext, SharePrefUtil.sp_phone, null);
                String mempsw = SharePrefUtil.getString(mContext, SharePrefUtil.sp_pwd, null);
                if (lgname != null && mempsw != null) {
                    //     进入详情页
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("id", ll.get(position).getId());
                    intent.putExtra("position", position);
                    intent.putExtra("countdown", ll.get(position).getCountDown());
                    intent.putExtra("status", ll.get(position).getStatus());
                    mContext.startActivity(intent);
                    Log.d("TAG", "定期产品展示：id" + ll.get(position).getId());
                } else {
                    final ServicePhoneDialog servicePhoneDialog = new ServicePhoneDialog(mContext);
                    servicePhoneDialog.setTitle("提示");
                    servicePhoneDialog.setMessage("请先进行账号登陆！");
                    servicePhoneDialog.setYesOnclickListener("确定", new ServicePhoneDialog.onYesOnclickListener() {

                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("quitSuccess", true);
                            mContext.startActivity(intent);
                            servicePhoneDialog.dismiss();
                        }
                    });
                    servicePhoneDialog.setNoOnclickListener("取消", new ServicePhoneDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            servicePhoneDialog.dismiss();
                        }
                    });
                    servicePhoneDialog.show();
                }
            }
        });
        //首页三个图片
        mainfragment_ll_safedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainSafeDetailActivity.class);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //        首页在未登录的情况下不显示登录注册按钮
       /* if (mApplication.mUserPhone != null) {
            iv_zhuce.setVisibility(View.GONE);
            iv_zhuce.setEnabled(false);
        } else {
            iv_zhuce.setVisibility(View.VISIBLE);
            iv_zhuce.setEnabled(true);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mApplication.mUserPhone != null) {
            iv_zhuce.setVisibility(View.GONE);
            iv_zhuce.setEnabled(false);
        } else {
            iv_zhuce.setVisibility(View.VISIBLE);
            iv_zhuce.setEnabled(true);
        }
    }

    //通告
    private void mainMessage() {
        RequestParams requestParams = new RequestParams(Const.MainMessage);
//        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result == null || "".equals(result)) {
//                    showShortToast("网络异常，请连接网络！");
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    return;
                }
                Gson gson = new Gson();
                Log.d("TAG", "MainFragment里面的response: " + result);
                mResult = gson.fromJson(result, MainMessageEntity.class);
                if (mResult != null) {
                    if (mResult.getTitle() == null) {
                        tv_message.setText("");
                    } else {
                        tv_message.setText(mResult.getTitle() + "");
                    }
                }
                ll_gs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Bundle bundle = new Bundle();
                        bundle.putString("title", mResult.getTitle().toString());
                        bundle.putString("content", mResult.getContent().toString());
                        bundle.putString("time", mResult.getTime().toString());
                        startActivity(MainmessageDetail.class, bundle);*/
                        startActivity(MainmessageDetail.class);
                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("xUtilsError", "MainFragment 的 requestFoundPic 异常" + throwable.toString());
                if(swipeRefreshLayout != null){
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
//                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFinished() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void initEvents() {
        //设置进度颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        //设置刷新大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置位置
        swipeRefreshLayout.setProgressViewEndTarget(true, 200);
        //设置监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //自定义的数据刷新![这里写图片描述](http://img.blog.csdn.net/20160506183745437)
                requestFoundPic();
                mainProduce();
                mainMessage();
            }
        });
    }

    //首先产品列表
    public void mainProduce() {
        RequestParams requestParams = new RequestParams(Const.MainPath);
//        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.setUseCookie(false);
        requestParams.addHeader("User-Agent", "android");
        // showLoadingDialog("Loading...");
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    return;
                }
                if (result.contains("404")) {
                    return;
                }
                Gson gson = new Gson();
                final MainProduceEntity mResult = gson.fromJson(result, MainProduceEntity.class);
                if (mResult == null) {
                    return;
                }
                Log.i("TAG", result);
                ll = new LinkedList<>();
                if (mResult.getNewUserProduct() != null) {
                    MainProduceEntity.PeriodicalProductsBean newUserProduct = new MainProduceEntity.PeriodicalProductsBean();
                    newUserProduct.setDurationType(mResult.getNewUserProduct().getDurationType());
                    newUserProduct.setDuration(mResult.getNewUserProduct().getDuration());
                    newUserProduct.setMoney(mResult.getNewUserProduct().getMoney());
                    newUserProduct.setCountDown(mResult.getNewUserProduct().getCountDown());
                    newUserProduct.setStatus(mResult.getNewUserProduct().getStatus());
                    newUserProduct.setId(mResult.getNewUserProduct().getId());
                    newUserProduct.setTitle(mResult.getNewUserProduct().getTitle());
                    newUserProduct.setRestMoney(mResult.getNewUserProduct().getRestMoney());
                    newUserProduct.setRate(mResult.getNewUserProduct().getRate());
                    newUserProduct.setStartTime(mResult.getNewUserProduct().getStartTime());
                    newUserProduct.setEndTime(mResult.getNewUserProduct().getEndTime());
                    newUserProduct.setLockDuration(mResult.getNewUserProduct().getLockDuration());
                    newUserProduct.setCategory(mResult.getNewUserProduct().getCategory());
                    newUserProduct.setInterestValueType(mResult.getNewUserProduct().getInterestValueType());
                    newUserProduct.setInterestPayType(mResult.getNewUserProduct().getInterestPayType());
                    newUserProduct.setPrinciplePayType(mResult.getNewUserProduct().getPrinciplePayType());
                    newUserProduct.setMinMoney(mResult.getNewUserProduct().getMinMoney());
                    newUserProduct.setMaxMoney(mResult.getNewUserProduct().getMaxMoney());
                    newUserProduct.setRedeemFeeRate(mResult.getNewUserProduct().getRedeemFeeRate());
                    newUserProduct.setCreateTime(mResult.getNewUserProduct().getCreateTime());
                    // newUserProduct.setUpdateTime(mResult.getNewUserProduct().getUpdateTime());
                    newUserProduct.setSecurityDescription(mResult.getNewUserProduct().getSecurityDescription());
                    newUserProduct.setRepayDescription(mResult.getNewUserProduct().getRepayDescription());
                    newUserProduct.setType(mResult.getNewUserProduct().getType());
                    ll.add(newUserProduct);
                }

                if (mResult.getCurrentProduct() != null) {
                    MainProduceEntity.PeriodicalProductsBean currentUserProduct = new MainProduceEntity.PeriodicalProductsBean();
                    currentUserProduct.setDurationType(mResult.getCurrentProduct().getDurationType());
                    currentUserProduct.setDuration(mResult.getCurrentProduct().getDuration());
                    currentUserProduct.setMoney(mResult.getCurrentProduct().getMoney());
                    currentUserProduct.setCountDown(mResult.getCurrentProduct().getCountDown());
                    currentUserProduct.setStatus(mResult.getCurrentProduct().getStatus());
                    currentUserProduct.setId(mResult.getCurrentProduct().getId());
                    currentUserProduct.setTitle(mResult.getCurrentProduct().getTitle());
                    currentUserProduct.setRestMoney(mResult.getCurrentProduct().getRestMoney());
                    currentUserProduct.setRate(mResult.getCurrentProduct().getRate());
                    currentUserProduct.setLockDuration(mResult.getCurrentProduct().getLockDuration());
                    currentUserProduct.setCategory(mResult.getCurrentProduct().getCategory());
                    currentUserProduct.setInterestValueType(mResult.getCurrentProduct().getInterestValueType());
                    currentUserProduct.setInterestPayType(mResult.getCurrentProduct().getInterestPayType());
                    currentUserProduct.setPrinciplePayType(mResult.getCurrentProduct().getPrinciplePayType());
                    currentUserProduct.setMinMoney(mResult.getCurrentProduct().getMinMoney());
                    currentUserProduct.setMaxMoney(mResult.getCurrentProduct().getMaxMoney());
                    currentUserProduct.setRedeemFeeRate(mResult.getCurrentProduct().getRedeemFeeRate());
                    currentUserProduct.setCreateTime(mResult.getCurrentProduct().getCreateTime());
                    // newUserProduct.setUpdateTime(mResult.getNewUserProduct().getUpdateTime());
                    currentUserProduct.setSecurityDescription(mResult.getCurrentProduct().getSecurityDescription());
                    currentUserProduct.setRepayDescription(mResult.getCurrentProduct().getRepayDescription());
                    currentUserProduct.setType(mResult.getCurrentProduct().getType());
                    ll.add(currentUserProduct);
                }

                if (mResult.getPeriodicalProducts() != null && mResult.getPeriodicalProducts().size() > 0) {
                    ll.addAll(mResult.getPeriodicalProducts());
                }
                mAdapter = new MainFragmentAdapter(mContext, ll);
                lv_main.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
//                lv_main.post(new Runnable() {
//                    @Override
//                    public void run() {
//                mAdapter = new MainFragmentAdapter(mContext, mResult);
//                lv_main.setAdapter(mAdapter);
//                swipeRefreshLayout.setRefreshing(false);
//
//                    }
//                });
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                showShortToast("网络异常，请连接网络！");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Log.i("xUtilsError", "MainFragment 的 mainProduce 异常" + throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {
//                showShortToast("网络请求取消！");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFinished() {
                // dismissLoadingDialog();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
