package com.ceshi.android.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 万能适配器
 * @author killer
 * @version 1.0
 * @since 2016-01-10 13:58:14
 */
public abstract class MyBaseAdpater<T> extends BaseAdapter {

    public Context context;
    public List<T> mData;
    public LayoutInflater mInflater;

    public MyBaseAdpater(Context context, List<T> mData) {
        this.context = context;
        this.mData = mData;
        this.mInflater= LayoutInflater.from(context);
    }

    public void updateListView(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
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

}
