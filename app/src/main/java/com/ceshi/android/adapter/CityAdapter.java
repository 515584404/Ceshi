package com.ceshi.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.entity.CityEntity;
import com.ceshi.android.ui.CityActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class CityAdapter extends BaseAdapter {
    private List<CityEntity> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public CityAdapter(Context context, List<CityEntity> mData) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_bank, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_bank_name.setText(mData.get(position).getName());
        mViewHolder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("cityName",mData.get(position).getName());
                intent.putExtra("cityCode",mData.get(position).getCode());
                ((CityActivity)context).setResult(2,intent);
                ((CityActivity)context).finish();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_bank_name)
        HandyTextView tv_bank_name;
        @Bind(R.id.ll_top)
        LinearLayout ll_top;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
