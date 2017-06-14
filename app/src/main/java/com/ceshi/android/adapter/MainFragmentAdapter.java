package com.ceshi.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.MainProduceEntity;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.ProductDetailActivity;
import com.ceshi.android.util.ServicePhoneDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class MainFragmentAdapter extends BaseAdapter {

    //    private MainProduceEntity mData;
    private Context context;
    private LayoutInflater mInflater;
    private List<MainProduceEntity.PeriodicalProductsBean> list;

    public MainFragmentAdapter(Context context, List<MainProduceEntity.PeriodicalProductsBean> list) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_main_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        String title = list.get(position).getTitle();
        if(title.startsWith("新手")){
            mViewHolder.tv_chanpin1.setBackgroundResource(R.drawable.shape_main_product_new);
            mViewHolder.iv_xinShou.setVisibility(View.VISIBLE);
        }else if(title.startsWith("活期")){
            mViewHolder.tv_chanpin1.setBackgroundResource(R.drawable.shape_main_product_huo);
        }else if(title.startsWith("测试")){
            mViewHolder.tv_chanpin1.setBackgroundResource(R.drawable.shape_main_product_ding);
        }
        mViewHolder.tv_chanpin1.setText("" + list.get(position).getTitle());
        mViewHolder.tv_lilv.setText("" + list.get(position).getRate());
//            判断时间的单位
        if (list.get(position).getDurationType().equals("DAY")) {
            mViewHolder.tv_qixian.setText("" + list.get(position).getDuration() + "天");
        } else if (list.get(position).getDurationType().equals("MONTH")) {
            mViewHolder.tv_qixian.setText("" + list.get(position).getDuration() + "个月");
        }
        if(list.get(position).getType().equals("CURRENT")){
            mViewHolder.tv_qixian.setText("活期");
        }

//        if("零乾袋".equals(mResult.getProduct().getTitle()) && "CURRENT".equals(mResult.getProduct().getType())){
//            tv_time.setText("活期");
//        }
//        if("零乾袋".equals(list.get(position).getTitle()) && "CURRENT".equals(list.get(position).getType())){
//            mViewHolder.tv_qixian.setText("活期");
//        }
//        if (list.get(position).getTitle().startsWith("新手")){
//            mViewHolder.tv_chanpin1.setVisibility(View.GONE);
//            mViewHolder.tv_chanpin2.setVisibility(View.VISIBLE);
//            mViewHolder.tv_chanpin2.setText("" + list.get(position).getTitle());
//            mViewHolder.iv_xinShou.setVisibility(View.VISIBLE);
//
//        }
         mViewHolder.tv_rongzi.setText("" + list.get(position).getMoney() + "元");

//            判断是否开始售出,如果没有到时间
        if (list.get(position).getCountDown() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
            //  String timeLine = format.format(new Date(mData.getPeriodicalProducts().get(position - 2).getCountDown()));
            String timeLine = format.format(new Date(list.get(position).getCountDown()));
            mViewHolder.tv_top_title.setText(timeLine + "后开售");
            mViewHolder.btn_add.setText("暂未开售");
            mViewHolder.tv_time.setText("");
            mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);

        } else {              //如果到时间了
            if (list.get(position).getStatus().equals("SELLING")) {
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setText("");
                mViewHolder.btn_add.setText("发售中");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.new_btn_add);
            } else if (list.get(position).getStatus().equals("SELLOUT")) {
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setText("");
                mViewHolder.btn_add.setText("已售完");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);
            } else if (list.get(position).getStatus().equals("INVALID")) {
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setText("");
                mViewHolder.btn_add.setText("无效");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);
            }
        }
        mViewHolder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lgname = SharePrefUtil.getString(context, SharePrefUtil.sp_phone, null);
                String mempsw = SharePrefUtil.getString(context, SharePrefUtil.sp_pwd, null);
                if (lgname != null && mempsw != null) {
                    //     进入详情页
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("position", position);
                    intent.putExtra("countdown", list.get(position).getCountDown());
                    intent.putExtra("status", list.get(position).getStatus());
                    context.startActivity(intent);
                    Log.d("TAG", "定期产品展示：id" + list.get(position).getId());
                } else {

                    final ServicePhoneDialog servicePhoneDialog = new ServicePhoneDialog(context);
                    servicePhoneDialog.setTitle("提示");
                    servicePhoneDialog.setMessage("请先进行账号登陆！");
                    servicePhoneDialog.setYesOnclickListener("确定", new ServicePhoneDialog.onYesOnclickListener() {

                        @Override
                        public void onYesClick() {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("quitSuccess", true);
                            context.startActivity(intent);
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


        return convertView;
    }


    static class ViewHolder {

        @Bind(R.id.tv_chanpin1)
        HandyTextView tv_chanpin1;
        @Bind(R.id.tv_lilv)
        HandyTextView tv_lilv;
        @Bind(R.id.tv_main_qixian)
        TextView tv_qixian;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_rongzi)
        TextView tv_rongzi;
        @Bind(R.id.tv_top_title)
        TextView tv_top_title;
        @Bind(R.id.btn_add)
        Button btn_add;
        @Bind(R.id.iv_xinShou)
        ImageView iv_xinShou;
        @Bind(R.id.tv_chanpin2)
        HandyTextView tv_chanpin2;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
