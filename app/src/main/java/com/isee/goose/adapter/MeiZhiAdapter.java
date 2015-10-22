package com.isee.goose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.isee.goose.R;
import com.isee.goose.vo.AndroidInfo;
import java.util.List;

/**
 * Created by wuyun on 2015/10/16.
 */
public class MeiZhiAdapter extends BaseAdapter {
    private List<AndroidInfo> datas;
    private Context context;

    public MeiZhiAdapter(Context context, List<AndroidInfo> datas) {
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
        view = LayoutInflater.from(context).inflate(R.layout.grid_item_meizhi_info, null);
        viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView_meizhi);
        view.setTag(viewHolder);

        AndroidInfo data = getItem(i);

        Glide.with(context)
                .load(data.url)
                .placeholder(R.drawable.header_view)
                .error(R.drawable.header_view)
                .into(viewHolder.imageView);



        return view;
    }



    class ViewHolder {

        private ImageView imageView;
    }


}
