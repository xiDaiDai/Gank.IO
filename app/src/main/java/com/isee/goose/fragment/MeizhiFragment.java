package com.isee.goose.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.isee.goose.PictureActivity;
import com.isee.goose.R;
import com.isee.goose.adapter.MeiZhiAdapter;
import com.isee.goose.net.OkHttpManager;
import com.isee.goose.net.RemoteConfig;
import com.isee.goose.util.ViewUtils;
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
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreGridViewContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wuyun on 2015/10/19.
 */
public class MeizhiFragment extends Fragment implements PtrHandler,LoadMoreHandler {
    private LoadMoreGridViewContainer loadMoreGridViewContainer;
    private GridViewWithHeaderAndFooter gridViewWithHeaderAndFooter;
    private PtrClassicFrameLayout ptrFrameLayout;
    private View loadingMask;
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
                    ViewUtils.goneView(loadingMask);
                    break;
                case 1:
                    ptrFrameLayout.refreshComplete();
                    break;
                case 2:
                    mAdapter.add(result.androidInfoList);
                    loadMoreGridViewContainer.loadMoreFinish(false,true);
                    break;
            }


        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizhi,container,false);
        loadMoreGridViewContainer = (LoadMoreGridViewContainer) view.findViewById(R.id.load_more_grid_view_container);
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.load_more_grid_view_ptr_frame);
        gridViewWithHeaderAndFooter = (GridViewWithHeaderAndFooter) view.findViewById(R.id.load_more_grid_view);
        loadingMask = view.findViewById(R.id.llLoadingMask);
        ptrFrameLayout.setPtrHandler(this);
        loadMoreGridViewContainer.setLoadMoreHandler(this);
        loadMoreGridViewContainer.useDefaultHeader();
        datas = new ArrayList<AndroidInfo>();
        mAdapter = new MeiZhiAdapter(getActivity(),datas);
        gridViewWithHeaderAndFooter.setAdapter(mAdapter);
        gridViewWithHeaderAndFooter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               AndroidInfo androidInfo = (AndroidInfo) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, androidInfo.url);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    void loadData(){
        page = 1;
        mOkHttpClient = OkHttpManager.getInstance();
        Request request = new Request.Builder().url(RemoteConfig.girlsApi+"/"+20+"/"+page).build();
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

    void loadMore(){
        page ++ ;
        mOkHttpClient = OkHttpManager.getInstance();
        Request request = new Request.Builder().url(RemoteConfig.girlsApi+"/"+20+"/"+page).build();
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
                msg.what = 2;
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

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        loadMore();
    }
}
