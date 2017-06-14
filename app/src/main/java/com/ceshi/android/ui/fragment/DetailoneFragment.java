package com.ceshi.android.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ceshi.android.R;
import com.ceshi.android.adapter.DetailDialogAdapter;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.ProduceDetailEntity;
import com.ceshi.android.ui.ConfirmActivity;
import com.ceshi.android.ui.DepositActivity;
import com.ceshi.android.ui.IntroduceActivity;
import com.ceshi.android.ui.ZqActivity;
import com.ceshi.android.util.UIHelper;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 * 产品详情页
 */

public class DetailoneFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_qixian)
    TextView tv_qixian;
    @Bind(R.id.tv_time)
    HandyTextView tv_time;
    @Bind(R.id.tv_rongzi_time)
    HandyTextView tv_rongzi_time;
    @Bind(R.id.tv_daoqi)
    HandyTextView tv_daoqi;
    @Bind(R.id.tv_jine)
    EditText et_jine;
    @Bind(R.id.tv_keyong)
    HandyTextView tv_keyong;
    @Bind(R.id.btn_chongzhi)
    Button btn_chongzhi;
    @Bind(R.id.tv_shengyu)
    HandyTextView tv_shengyu;
    @Bind(R.id.btn_add)
    Button btn_add;
    @Bind(R.id.tv_jieshao)
    LinearLayout tv_jieshao;
    @Bind(R.id.tv_zhaiquan)
    LinearLayout tv_zhaiquan;
    @Bind(R.id.tv_lv)
    TextView tv_lv;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.widget_title)
    TextView widget_title;
    @Bind(R.id.btn_countdown)
    TextView btnCountdown;
    @Bind(R.id.detail_ll_redpacket)
    LinearLayout detail_ll_redpacket;

    private int productId;
    private int position;
    String productType;
    private double availableMoney;  //可用金额


    private View inflate;
    private Dialog dialog;
    private ArrayList<String> redPackets = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_new, container, false);
//        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected void initViews() {
        Intent intent = getActivity().getIntent();
//        获取位置，产品Id
        productId = intent.getIntExtra("id", -1);
        Log.d("TAG", "productId:" + productId);
        position = intent.getIntExtra("position", -1);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "活动类名：" + getActivity().getClass().toString());
                getActivity().finish();

            }
        });
        productDetail();
        //数据
        detail_ll_redpacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redPacketDialog();
            }
        });
    }

    //红包  体验金 加息券 dialog
    private void redPacketDialog() {
        dialog = new Dialog(mActivity,R.style.ActionSheetDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        //填充对话框的布局
        inflate = LayoutInflater.from(mActivity).inflate(R.layout.dialog_redpacket, null);
        //初始化控件
        ListView listview = (ListView) inflate.findViewById(R.id.dialog_listview);

        listview.setAdapter(new DetailDialogAdapter(mContext,redPackets));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }


    private void productDetail() {
        UIHelper.showDialogForLoading(getActivity(), "Loading...", true);
        RequestParams requestParams = new RequestParams(Const.Detailproduct);
        requestParams.addHeader("cookie", mApplication.mUserCookie);
        requestParams.addHeader("User-Agent", "android");
        requestParams.addBodyParameter("productId", productId + "");

        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result == null || "".equals(result)) {
                    showShortToast("网络异常，请连接网络！");
                    return;
                }
                Log.i("yan", "获取的数据是" + result);
                Log.i("TAG", "获取的数据是：" + result);
                Gson gson = new Gson();
                final ProduceDetailEntity mResult = gson.fromJson(result, ProduceDetailEntity.class);
                if (mResult == null) {
                    Log.d("TAG", "mResult:");
                    return;
                }
                productType = mResult.getProduct().getTitle();
                if (tv_lv != null) {
                    Log.d("TAG", "tv_lv != null");
                    tv_lv.setText(mResult.getProduct().getRate() + "%");

                    tv_time.setText(mResult.getProduct().getDuration() + "个月");
                    tv_rongzi_time.setText(mResult.getProduct().getMoney() + "元");
                    tv_keyong.setText(mResult.getAvailableMoney() + "元");
//                            availableMoney = mResult.getAvailableMoney();
                    if (mResult.getAvailableMoney() != null && !"".equals(mResult.getAvailableMoney())) {
                        availableMoney = Double.parseDouble(mResult.getAvailableMoney());
                    }

//                    if("零乾袋".equals(mResult.getProduct().getTitle()) && "CURRENT".equals(mResult.getProduct().getType())){
//                        tv_time.setText("活期");
//                    }
                    if (mResult.getProduct().getType().equals("CURRENT")) {
                        tv_time.setText("活期");
                    }

                    tv_shengyu.setText(mResult.getProduct().getRestMoney() + "元");
                    widget_title.setText(mResult.getProduct().getTitle());
                    tv_daoqi.setText(mResult.getProduct().getRepayDescription());
                } else {
                    Log.d("TAG", "tv_lv == null");
                }

                //            判断是否开始售出,如果没有到时间
                if (mResult.getProduct().getCountDown() > 0) {
                    SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
                    String timeLine = format.format(new Date(mResult.getProduct().getCountDown()));
                    btnCountdown.setText("项目正在预热，倒计时：" + timeLine);
                    btnCountdown.setVisibility(View.VISIBLE);
                    btn_add.setVisibility(View.GONE);
                } else {              //如果到时间了
                    if (mResult.getProduct().getStatus().equals("SELLING")) {
                        btn_add.setVisibility(View.VISIBLE);
                        btn_add.setText("立即加入");
                        btn_add.setBackgroundResource(R.drawable.btn_bg);
                        btnCountdown.setVisibility(View.GONE);
                    } else if (mResult.getProduct().getStatus().equals("SELLOUT")) {
                        btn_add.setVisibility(View.VISIBLE);
                        btn_add.setText("已售完");
                        btn_add.setBackgroundResource(R.drawable.btn_bg_gray);
                        btnCountdown.setVisibility(View.GONE);
                    } else if (mResult.getProduct().getStatus().equals("INVALID")) {
                        btn_add.setVisibility(View.VISIBLE);
                        btn_add.setText("无效");
                        btn_add.setBackgroundResource(R.drawable.btn_bg_gray);
                        btnCountdown.setVisibility(View.GONE);
                    }
                }
            }


            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("TAG", "DetailoneFragment的productDetail异常" + throwable.toString());
                showShortToast("网络异常，请连接网络！");
            }

            @Override
            public void onCancelled(CancelledException e) {
                showShortToast("网络请求取消！");
            }

            @Override
            public void onFinished() {
                UIHelper.hideDialogForLoading();
                //  handler.sendEmptyMessageDelayed(0,1000);
            }
        });


    }

    @Override
    protected void initEvents() {
        tv_zhaiquan.setOnClickListener(this);
        tv_jieshao.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_chongzhi.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zhaiquan:
                Intent intent = new Intent(getActivity(), ZqActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
                break;
            case R.id.tv_jieshao:         //产品详情介绍
                Intent introIntent = new Intent(getActivity(), IntroduceActivity.class);
                Log.d("TAG", "产品详情22222:" + productType);
                introIntent.putExtra("productType", productType);
                introIntent.putExtra("productId", productId);
                startActivity(introIntent);
                break;
            case R.id.btn_add:
                if (!"立即加入".equals(btn_add.getText())) {
                    showShortToast("该产品" + btn_add.getText());
                    return;
                }
                String jine = et_jine.getText().toString();
                if (jine.equals("")) {
                    showShortToast("请输入投资金额!");
                    return;
                }
                if (Integer.valueOf(jine) <= 0) {
                    showShortToast("投资金额不能小于1元！");
                    return;
                }
                if (Integer.valueOf(jine) > availableMoney) {
                    showShortToast("可用金额不足，请充值！");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("money", jine);
                bundle.putInt("productId", productId);
                startActivity(ConfirmActivity.class, bundle);
                break;
            case R.id.btn_chongzhi:
                startActivity(DepositActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
