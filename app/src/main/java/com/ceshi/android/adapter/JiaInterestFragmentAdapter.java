package com.ceshi.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ceshi.android.R;

import java.util.ArrayList;

/**
 * Created by Mac on 2017/6/13.
 */

public class JiaInterestFragmentAdapter extends BaseAdapter{

    private ArrayList<String> jiaInterests; // 存放红包的集合
    private Context mContext;

    public JiaInterestFragmentAdapter(Context mContext, ArrayList<String> jiaInterests) {
        this.mContext = mContext;
        this.jiaInterests = jiaInterests;
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
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_jiainterest_item, parent, false);
        }
        return convertView;
    }
}
