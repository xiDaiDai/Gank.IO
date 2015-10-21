package com.isee.goose.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isee.goose.R;
import com.isee.goose.vo.AndroidInfo;

import java.util.List;

/**
 * Created by wuyun on 2015/10/16.
 */
public class AndroidAdapter extends BaseAdapter {
    private List<AndroidInfo> datas;
    private Context context;

    public AndroidAdapter(Context context,List<AndroidInfo> datas) {
        this.context = context;
        this.datas = datas;
    }


    public void update(List<AndroidInfo> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void add(List<AndroidInfo> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public AndroidInfo getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        }
        view = LayoutInflater.from(context).inflate(R.layout.list_item_android_info, null);
        viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) view.findViewById(R.id.textView5);
        view.setTag(viewHolder);

        AndroidInfo data = getItem(i);
        viewHolder.textView.setText(data.desc);
        return view;
    }

    class ViewHolder{
        private TextView textView;


    }


}
