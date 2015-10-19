package com.isee.goose.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.isee.goose.MainActivity;
import com.isee.goose.R;
import com.isee.goose.app.Config;
import com.isee.goose.app.Global;
import com.isee.goose.vo.ItemOne;


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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onClick(View view) {
        mainActivity.closeDrawer();
        switch (view.getId()){
            case R.id.textView:
                EventBus.getDefault().post(new ItemOne("One",Config.ANDROID));
                break;
            case R.id.textView_meizhi:
                EventBus.getDefault().post(new ItemOne("two",Config.MEIZHI));
                break;
        }


    }


}
