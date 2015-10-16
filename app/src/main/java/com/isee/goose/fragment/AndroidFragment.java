package com.isee.goose.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isee.goose.R;
import com.isee.goose.adapter.AndroidAdapter;
import com.isee.goose.app.Global;
import com.isee.goose.net.RemoteConfig;
import com.isee.goose.vo.AndroidInfo;
import com.isee.goose.vo.BaseResult;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.CLog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wuyun on 2015/10/15.
 */
public class AndroidFragment extends Fragment implements PtrHandler{

    private Activity mActivity;
    private ListView listview;
    private AndroidAdapter mAdapter;
    private List<AndroidInfo> datas;
    private  BaseResult result;
    private PtrClassicFrameLayout ptrFrameLayout;
    private  OkHttpClient mOkHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAdapter.update(result.androidInfoList);
                    ptrFrameLayout.refreshComplete();

                    break;

            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_item_one, container);
        mActivity = getActivity();
        ptrFrameLayout = (PtrClassicFrameLayout) mActivity.findViewById(R.id.load_more_list_view_ptr_frame);

        listview = (ListView) mActivity.findViewById(R.id.list_view);
        datas = new ArrayList<AndroidInfo>();
        mAdapter = new AndroidAdapter(mActivity,datas);
        listview.setAdapter(mAdapter);
        ptrFrameLayout.setPtrHandler(this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(RemoteConfig.androidApi+"/"+10+"/"+1).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ptrFrameLayout.refreshComplete();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseBody = response.body().string();
                CLog.e("result", responseBody);
                Gson gson = new Gson();
                result = gson.fromJson(responseBody, BaseResult.class);
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);


            }
        });
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, listview, view1);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        loadData();
    }
}
