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
import com.isee.goose.R;
import com.isee.goose.adapter.AndroidAdapter;
import com.isee.goose.app.Global;
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
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wuyun on 2015/10/15.
 */
public class AndroidFragment extends Fragment implements PtrHandler,LoadMoreHandler{

    private Activity mActivity;
    private ListView listview;
    private AndroidAdapter mAdapter;
    private List<AndroidInfo> datas;
    private  BaseResult result;
    private PtrClassicFrameLayout ptrFrameLayout;
    private View loadingMask;
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private  OkHttpClient mOkHttpClient;
    private int page;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    datas.clear();
                    datas.addAll(result.androidInfoList);
                    mAdapter.notifyDataSetChanged();
                    ptrFrameLayout.refreshComplete();
                    ViewUtils.goneView(loadingMask);
                    break;
                case 1:
                    ptrFrameLayout.refreshComplete();
                    break;
                case 2:
                    mAdapter.add(result.androidInfoList);
                    loadMoreListViewContainer.loadMoreFinish(false,true);
                    break;

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_one,container,false);
        mActivity = getActivity();
        loadingMask = view.findViewById(R.id.llLoadingMask);
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.load_more_list_view_ptr_frame);
        loadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        listview = (ListView) view.findViewById(R.id.list_view);
        datas = new ArrayList<AndroidInfo>();
        mAdapter = new AndroidAdapter(mActivity,datas);
        listview.setAdapter(mAdapter);
        ptrFrameLayout.setPtrHandler(this);
        loadMoreListViewContainer.setLoadMoreHandler(this);
        loadMoreListViewContainer.useDefaultHeader();
        return view ;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    void loadData(){
        page = 1;
        mOkHttpClient = OkHttpManager.getInstance();
        Request request = new Request.Builder().url(RemoteConfig.androidApi+"/"+20+"/"+page).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
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

    void loadMore(){
        page ++ ;
        mOkHttpClient = OkHttpManager.getInstance();
        Request request = new Request.Builder().url(RemoteConfig.androidApi+"/"+20+"/"+page).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseBody = response.body().string();
                CLog.e("result", responseBody);
                Gson gson = new Gson();
                result = gson.fromJson(responseBody, BaseResult.class);
                Message msg = new Message();
                msg.what = 2;
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

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        loadMore();
    }
}
