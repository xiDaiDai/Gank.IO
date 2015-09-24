package com.isee.goose.app;

import android.util.Log;

/**
 * Created by wuyun on 2015/9/23.
 */
public class Global{


    public static GooApplication application;

   public static void init(GooApplication mApplication){

        application = mApplication;
   }

    public static void showToast(String content){
       application.show(content);
    }




}
