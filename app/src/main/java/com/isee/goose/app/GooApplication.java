package com.isee.goose.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by wuyun on 2015/9/23.
 */
public class GooApplication extends Application {

    private Toast mToast;
    private Handler mHandler = new Handler();
    @Override
    public void onCreate() {
        super.onCreate();
        Global.init(this);
    }

    public void show(String msg) {
        if (TextUtils.isEmpty(msg)) return;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mHandler.post(new ToastRunnable(msg, Toast.LENGTH_LONG));
        } else {
            toast(msg, Toast.LENGTH_LONG);
        }
    }

    private void toast(String msg, int duration) {
        if (null == mToast) {
            mToast = Toast.makeText(this, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    class ToastRunnable implements Runnable {

        private String mMsg;
        private int mDuration;

        public ToastRunnable(String msg, int duration) {
            mMsg = msg;
            mDuration = duration;
        }

        @Override
        public void run() {
            toast(mMsg, mDuration);
        }

    }
}
