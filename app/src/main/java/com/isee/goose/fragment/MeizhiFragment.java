package com.isee.goose.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.isee.goose.R;
import com.isee.goose.adapter.MeiZhiAdapter;
import com.isee.goose.net.OkHttpManager;
import com.isee.goose.net.RemoteConfig;
import com.isee.goose.vo.AndroidInfo;
import com.isee.goose.vo.BaseResult;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.CLog;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.loadmore.LoadMoreGridViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wuyun on 2015/10/19.
 */
public class MeizhiFragment extends Fragment implements PtrHandler {
    private LoadMoreGridViewContainer loadMoreGridViewContainer;
    private GridViewWithHeaderAndFooter gridViewWithHeaderAndFooter;
    private PtrClassicFrameLayout ptrFrameLayout;
    private MeiZhiAdapter mAdapter;
    private  BaseResult result;
    private List<AndroidInfo> datas;

    private int page;
    private OkHttpClient mOkHttpClient;

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAdapter.update(result.androidInfoList);
                    ptrFrameLayout.refreshComplete();
                    break;
                case 1:
                    ptrFrameLayout.refreshComplete();
                    break;
            }


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizhi,container);
        loadMoreGridViewContainer = (LoadMoreGridViewContainer) view.findViewById(R.id.load_more_grid_view_container);
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.load_more_grid_view_ptr_frame);
        gridViewWithHeaderAndFooter = (GridViewWithHeaderAndFooter) view.findViewById(R.id.load_more_grid_view);
        ptrFrameLayout.setPtrHandler(this);
        datas = new ArrayList<AndroidInfo>();
        mAdapter = new MeiZhiAdapter(getActivity(),datas);
        gridViewWithHeaderAndFooter.setAdapter(mAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }


    void loadData(){
        page = 1;
        mOkHttpClient = OkHttpManager.getInstance();
        Request request = new Request.Builder().url(RemoteConfig.girlsApi+"/"+10+"/"+page).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseBody = response.body().string();
                CLog.e("result", responseBody);
                Gson gson = new Gson();
                result = gson.fromJson(responseBody, BaseResult.class);
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);


            }
        });
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, gridViewWithHeaderAndFooter, view1);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            loadData();
    }
}
