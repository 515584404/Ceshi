package com.ceshi.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.LiCaiEntity;
import com.ceshi.android.ui.ZqActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class LicaiAdapter extends BaseAdapter {
    private List<LiCaiEntity.RecordsEntity> mData;
    private Context context;
    private LayoutInflater mInflater;

    public LicaiAdapter(List<LiCaiEntity.RecordsEntity> mData, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_licai, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_title.setText(mData.get(position).getProductTitle());
        mViewHolder.tv_benjin.setText(mData.get(position).getMoney());
        mViewHolder.tv_nlilv.setText(mData.get(position).getRate() + "%");
        mViewHolder.tv_shouyi.setText(mData.get(position).getInterest());
        mViewHolder.tv_time.setText(mData.get(position).getRepayDate());
        Log.d("tag","LicaiAdapter里面的数据：mViewHolder.tv_time="+mData.get(position).getRepayDate());
        if (mData.get(position).getStatus().equals("FIAL")) {
            mViewHolder.tv_state.setText("投资失败");
        } else if (mData.get(position).getStatus().equals("SUCCESS")) {
            mViewHolder.tv_state.setText("投资成功");
        } else if (mData.get(position).getStatus().equals("INTERESTING")) {
            mViewHolder.tv_state.setText("计息中");
        } else {
            mViewHolder.tv_state.setText("已还款");
        }
        mViewHolder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ZqActivity.class);
                intent.putExtra("tid",mData.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_title)
        HandyTextView tv_title;
        @Bind(R.id.tv_state)
        HandyTextView tv_state;
        @Bind(R.id.tv_benjin)
        HandyTextView tv_benjin;
        @Bind(R.id.tv_shouyi)
        HandyTextView tv_shouyi;
        @Bind(R.id.tv_nlilv)
        HandyTextView tv_nlilv;
        @Bind(R.id.tv_time)
        HandyTextView tv_time;
        @Bind(R.id.ll_top)
        LinearLayout ll_top;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
