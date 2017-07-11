package com.guoxiaoxing.java.demo.concurrence;

import android.util.Log;

import com.guoxiaoxing.java.demo.App;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/11 下午6:22
 */
public class Worker implements Runnable {

    private String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Log.d(App.TAG, name + ": " + System.currentTimeMillis());
    }
}
