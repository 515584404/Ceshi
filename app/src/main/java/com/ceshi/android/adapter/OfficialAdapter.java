package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ceshi.android.R;
import com.ceshi.android.base.view.HandyTextView;
import com.ceshi.android.model.AnnouncementsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class OfficialAdapter extends BaseAdapter {
    private List<AnnouncementsData.AnnouncementsEntity> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public OfficialAdapter(List<AnnouncementsData.AnnouncementsEntity> mData, Context context) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_official, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tv_title.setText(mData.get(position).getTitle());
        mViewHolder.tv_time.setText(mData.get(position).getCreateTime());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_title)
        HandyTextView tv_title;
        @Bind(R.id.tv_time)
        HandyTextView tv_time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
