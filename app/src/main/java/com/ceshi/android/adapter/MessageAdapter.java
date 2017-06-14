package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class MessageAdapter extends BaseAdapter {
    private List<MessageEntity.UserMessagesEntity> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private FrameLayout fl;

    public MessageAdapter(List<MessageEntity.UserMessagesEntity> mData, Context context,FrameLayout fl) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;
        this.context = context;
        this.fl=fl;
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
            convertView = mInflater.inflate(R.layout.item_message, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        MessageEntity.UserMessagesEntity userMessagesEntity = mData.get(position);
        mViewHolder.tv_title.setText(userMessagesEntity.getTitle());
        mViewHolder.tv_time.setText(userMessagesEntity.getCreateTime());
        if ("UNREAD".equalsIgnoreCase(userMessagesEntity.getStatus())){
            mViewHolder.img_one.setVisibility(View.VISIBLE);
        }else if ("READED".equalsIgnoreCase(userMessagesEntity.getStatus())){
            mViewHolder.img_one.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }



    static class ViewHolder {
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.img_one)
        ImageView img_one;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
