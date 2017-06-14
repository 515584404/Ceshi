package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ceshi.android.R;
import java.util.ArrayList;
import butterknife.ButterKnife;

/**
 * Created by Mac on 2017/6/13.
 */

public class DetailDialogAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> redPackets;

    public DetailDialogAdapter(Context mContext, ArrayList<String> redPackets) {
        this.mContext = mContext;
        this.redPackets = redPackets;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_dialog_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
