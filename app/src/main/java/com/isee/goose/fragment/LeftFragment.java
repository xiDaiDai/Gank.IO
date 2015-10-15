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
import com.isee.goose.app.Global;
import com.isee.goose.vo.ItemOne;


import de.greenrobot.event.EventBus;

/**
 * Created by wuyun on 2015/9/23.
 */
public class LeftFragment extends Fragment implements View.OnClickListener{
private TextView textview;
private MainActivity mainActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_left_menu, container);
        textview = (TextView) getActivity().findViewById(R.id.textView);
        textview.setOnClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.leftMenu)));

    }



    @Override
    public void onClick(View view) {
        mainActivity.closeDrawer();
        EventBus.getDefault().post(new ItemOne("One", "ItemOne"));
        Global.showToast("one");
    }

   /* @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Global.showToast("click item NO."+position);
    }*/
}
