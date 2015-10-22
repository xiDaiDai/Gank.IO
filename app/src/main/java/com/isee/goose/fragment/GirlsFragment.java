package com.isee.goose.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.isee.goose.R;
import com.isee.goose.adapter.GirlsAdapter;
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
public class GirlsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View loadingMask;
    private GirlsAdapter mAdapter;
    private  BaseResult result;
    private List<AndroidInfo> datas;
    private int page;
    private OkHttpClient mOkHttpClient;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;


    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    datas.clear();
                    datas.addAll(result.androidInfoList);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    ViewUtils.goneView(loadingMask);
                    break;
                case 1:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    datas.addAll(result.androidInfoList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }


        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girls, container,false);
        initView(view);
        datas = new ArrayList<AndroidInfo>();
        setUpRecyclerView();
        return view;
    }

    private void initView(View view) {
        loadingMask = view.findViewById(R.id.llLoadingMask);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_girls);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.material_blue,
                R.color.material_green
        );
        fab = (FloatingActionButton) view.findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFab();
            }
        });
    }


    private void setUpRecyclerView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new GirlsAdapter(getActivity(),datas);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView rv, int dx, int dy) {
                        if (!swipeRefreshLayout.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(
                                new int[2]
                        )[1] >= mAdapter.getItemCount() - 2) {
                            loadMore();
                        }
                    }
                }
        );
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
    public void onRefresh() {
        loadData();
    }

    public void onFab(){
        loadData();
        if(!swipeRefreshLayout.isRefreshing())
        swipeRefreshLayout.setRefreshing(true);

    }
}
