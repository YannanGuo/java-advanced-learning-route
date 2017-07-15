package com.guoxiaoxing.java.demo.jvm.gc;

import android.util.Log;

import com.guoxiaoxing.java.demo.App;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/14 上午10:54
 */
public class SaveSelfObject {

    public static SaveSelfObject SAVE_SELF = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        SaveSelfObject.SAVE_SELF = this;
        Log.d(App.TAG, "finalize() executed");
    }
}
