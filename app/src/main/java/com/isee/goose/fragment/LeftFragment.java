package com.isee.goose.fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.isee.goose.R;
import com.isee.goose.app.Global;

/**
 * Created by wuyun on 2015/9/23.
 */

public class LeftFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_left_menu,container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.leftMenu)));

    }

   /* @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Global.showToast("click item NO."+position);
    }*/
}
