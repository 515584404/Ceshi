package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.SpreadEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class SpreadAdapter extends BaseAdapter {
    private List<SpreadEntity.RecordsBean> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public SpreadAdapter(List<SpreadEntity.RecordsBean> mData, Context context) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spread, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_phone.setText(mData.get(position).getRecommendedMobile().toString());
        mViewHolder.tv_jine.setText("￥"+mData.get(position).getCommission());
        mViewHolder.tv_time.setText(mData.get(position).getRecommendTime());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_phone)
        HandyTextView tv_phone;
        @Bind(R.id.tv_time)
        HandyTextView tv_time;
        @Bind(R.id.tv_jine)
        HandyTextView tv_jine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
