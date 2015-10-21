package com.isee.goose.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.isee.goose.R;
import com.isee.goose.vo.AndroidInfo;

import java.util.List;

/**
 * Created by wuyun on 2015/10/21.
 */
public class GirlsAdapter extends RecyclerView.Adapter<GirlsAdapter.ViewHolder> {
    private List<AndroidInfo> mList;
    private Context mContext;

    public GirlsAdapter(Context context, List<AndroidInfo> meizhiList) {
        mList = meizhiList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_girls, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AndroidInfo meizhi = mList.get(i);
        viewHolder.meizhiView.setImageResource(R.drawable.header_view);
        Glide.with(mContext).load(meizhi.url).error(R.color.material_blue).placeholder(R.color.material_blue).into(viewHolder.meizhiView);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.meizhiView != null) holder.meizhiView.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView meizhiView;
        public ViewHolder(View itemView) {
            super(itemView);
            meizhiView = (ImageView) itemView.findViewById(R.id.iv_girl);

        }


    }
}
