package com.isee.goose.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isee.goose.MainActivity;
import com.isee.goose.R;
import com.isee.goose.vo.MenuItem;


import de.greenrobot.event.EventBus;

/**
 * Created by wuyun on 2015/9/23.
 */
public class LeftFragment extends Fragment implements View.OnClickListener{
private TextView textview,meizhi;
private MainActivity mainActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container);
        textview = (TextView) view.findViewById(R.id.textView);
        meizhi = (TextView) view.findViewById(R.id.textView_meizhi);
        textview.setOnClickListener(this);
        meizhi.setOnClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        mainActivity.closeDrawer();
        switch (view.getId()){
            case R.id.textView:
                EventBus.getDefault().post(new MenuItem("android",MenuItem.FragmentType.ANDROID,AndroidFragment.class));
                break;
            case R.id.textView_meizhi:
                EventBus.getDefault().post(new MenuItem("meizhi",MenuItem.FragmentType.MEIZHI,MeizhiFragment.class));
                break;
        }


    }


}
