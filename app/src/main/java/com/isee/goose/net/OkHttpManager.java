package com.isee.goose.net;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by wuyun on 2015/10/19.
 */
public class OkHttpManager {
    private static OkHttpClient okHttpClient;
    public static OkHttpClient getInstance(){

        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }

        return okHttpClient;
    }
}
