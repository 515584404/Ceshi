package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.TradeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class TradeAdapter extends BaseAdapter {
    private List<TradeEntity.RecordsEntity> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public TradeAdapter(List<TradeEntity.RecordsEntity> mData, Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;
        this.context = context;
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
            convertView = mInflater.inflate(R.layout.item_trade, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //交易行为
        mViewHolder.tv_action.setText(mData.get(position).getType());
//        if (mData.get(position).getType().equals("DEPOSIT")) {
//            mViewHolder.tv_action.setText("充值");
//        } else if  (mData.get(position).getType().equals("BUY")) {
//            mViewHolder.tv_action.setText("投资");
//        }  else if  (mData.get(position).getType().equals("REPAID")) {
//            mViewHolder.tv_action.setText("回款");
//        }  else {
//            mViewHolder.tv_action.setText("提现");
//        }
        //交易金额
        mViewHolder.money.setText(mData.get(position).getMoney());
        //交易时间
        mViewHolder.time.setText(mData.get(position).getCreateTime());
        //交易状态
        mViewHolder.tv_state.setText(mData.get(position).getStatus());
//        if (mData.get(position).getStatus().equals("VALID")) {
//            mViewHolder.tv_state.setText("成功");
//        } else if  (mData.get(position).getStatus().equals("WAITING")) {
//            mViewHolder.tv_state.setText("申请");
//        }  else {
//            mViewHolder.tv_state.setText("无效");
//        }
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_action)
        HandyTextView tv_action;
        @Bind(R.id.money)
        HandyTextView money;
        @Bind(R.id.time)
        HandyTextView time;
        @Bind(R.id.tv_state)
        HandyTextView tv_state;
        @Bind(R.id.ll_iv_pay)
        ImageView ll_iv_pay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
