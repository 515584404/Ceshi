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
import com.ceshi.android.entity.ZqListEntity;
import com.ceshi.android.ui.CaseDetailActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ztr on 2016/04/25.
 */
public class ZqListAdapter extends BaseAdapter {
    private List<ZqListEntity.RecordsEntity> mData;
    private Context context;
    private LayoutInflater mInflater;
    private int productId;

    public ZqListAdapter(List<ZqListEntity.RecordsEntity> mData, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_zq, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_title.setText(mData.get(position).getTitle());
        mViewHolder.tv_rongzi_e.setText("￥" + mData.get(position).getOwnMoney());
        mViewHolder.tv_lilv.setText(mData.get(position).getRate() + "%");
        Log.d("tag","ZqListAdapter里面的数据mData.tv_rongzi_e="+mData.get(position).getOwnMoney());
        mViewHolder.ll_zq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, CaseDetailActivity.class);
                intent.putExtra("id",mData.get(position).getId());
                Log.d("TAG", "ZqListAdapter里面的ZQ" + mData.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void onDateChange(List<ZqListEntity.RecordsEntity> list) {
        this.mData=list;
        this.notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.iv_title)
        HandyTextView tv_title;
        @Bind(R.id.tv_lilv)
        HandyTextView tv_lilv;
        @Bind(R.id.tv_rongzi_e)
        HandyTextView tv_rongzi_e;
        @Bind(R.id.ll_zq)
        LinearLayout ll_zq;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
