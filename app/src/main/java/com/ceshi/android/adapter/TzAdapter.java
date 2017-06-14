package com.ceshi.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.InvestProduceEntity;
import com.ceshi.android.ui.MainActivity;
import com.ceshi.android.ui.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class TzAdapter extends BaseAdapter {
    private List<InvestProduceEntity.ProductsEntity> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public TzAdapter(Context context, List<InvestProduceEntity.ProductsEntity> mData) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tz, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.btn_add.setBackgroundResource(R.drawable.new_btn_bg);
        //产品名称
        mViewHolder.tv_chanpin1.setText(mData.get(position).getTitle());
        //利率
        mViewHolder.tv_lilv.setText(mData.get(position).getRate());
        //期限
        if (mData.get(position).getDurationType().equals("DAY")){
            mViewHolder.tv_qixian.setText(mData.get(position).getDuration() + "天");
        }else if (mData.get(position).getDurationType().equals("MONTH")){
            mViewHolder.tv_qixian.setText(mData.get(position).getDuration() + "个月");
        }
        if(mData.get(position).getType().equals("CURRENT")){
            mViewHolder.tv_qixian.setText("活期");
        }
//        if ("零乾袋".equals(mData.get(position).getTitle())&&"CURRENT".equals(mData.get(position).getType())){
//            mViewHolder.tv_qixian.setText("活期");
//        }

        //融资金额
        mViewHolder.tv_rongzi.setText(mData.get(position).getMoney() + "元");
        //设置新手专享图片
        if (mData.get(position).getTitle().startsWith("新手专享")){
            mViewHolder.iv_xinShou.setVisibility(View.VISIBLE);
            mViewHolder.tv_chanpin1.setBackgroundResource(R.mipmap.red_round);
        }

//        删除新手专享
//        if(mData.get(position).getType().equals("NEWER")) {
//            Drawable drawable = context.getResources().getDrawable(R.drawable.new_shou);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            //左上右下
//            mViewHolder.tv_top_title.setCompoundDrawables(null, null, drawable, null);
//        }

        Log.d("TAG", "我要投资界面数据="+mData);

        if (mData.get(position).getCountDown() > 0) {

            SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
            String t = format.format(new Date(mData.get(position).getCountDown()));
            mViewHolder.tv_top_title.setText(t + "后开售");
            mViewHolder.btn_add.setText("暂未开售");
            mViewHolder.tv_time.setText("");
            mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);
            if (mData.get(position).getType().equals("NEWER")){
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setVisibility(View.VISIBLE);
                mViewHolder.tv_time.setText(t + "后开售");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);
            }
        } else {
            if (mData.get(position).getStatus().equals("SELLING")){
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setText("");
                mViewHolder.btn_add.setText("发售中");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.new_btn_bg);
            }else if (mData.get(position).getStatus().equals("SELLOUT")){
                mViewHolder.tv_top_title.setText("");
                mViewHolder.tv_time.setText("");
                mViewHolder.btn_add.setText("已售完");
                mViewHolder.btn_add.setBackgroundResource(R.drawable.btn_bg_add_gry);
            }else if (mData.get(position).getStatus().equals("INVALID")){
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
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", mData.get(position).getId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }else {
                    View popupView = View.inflate(context, R.layout.dialog_tip_login, null);
                    final PopupWindow mPopupWindow= new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                    mPopupWindow.setTouchable(true);
                    mPopupWindow.setOutsideTouchable(true);

                    ImageView closeImageView = (ImageView) popupView.findViewById(R.id.dialog_login_iv_close);
                    TextView okBtn = (TextView) popupView.findViewById(R.id.dialog_login_ok);
                    TextView cancleBtn = (TextView) popupView.findViewById(R.id.dialog_login_cancle);

                    closeImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("quitSuccess", true);
                            context.startActivity(intent);
                            mPopupWindow.dismiss();
                        }
                    });
                    cancleBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                        }
                    });

                    mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                }



            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_chanpin1)
        HandyTextView tv_chanpin1;
        @Bind(R.id.tv_top_title)
        TextView tv_top_title;
        @Bind(R.id.tv_lilv)
        HandyTextView tv_lilv;
        @Bind(R.id.tv_qixian)
        TextView tv_qixian;
        @Bind(R.id.tv_rongzi)
        TextView tv_rongzi;
        @Bind(R.id.btn_add)
        Button btn_add;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.iv_xinShou)
        ImageView iv_xinShou;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
