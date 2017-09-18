package com.guoxiaoxing.java.demo.thread;

import android.os.AsyncTask;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/9/18 下午3:10
 */
public class AsyncTaskDemo extends AsyncTask<String, Integer, String> {

    /**
     * 在后台任务开始执行之前调用，用于执行一些界面初始化操作，例如显示一个对话框，UI线程。
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 执行后台线程，执行完成可以通过return语句返回，worker线程
     *
     * @param strings params
     * @return result
     */
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    /**
     * 更新进度，UI线程
     *
     * @param values progress
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }


    /**
     * 后台任务执行完成并通过return语句返回后会调用该方法，UI线程。
     *
     * @param result result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    /**
     * 后台任务呗取消后回调
     *
     * @param reason reason
     */
    @Override
    protected void onCancelled(String reason) {
        super.onCancelled(reason);
    }

    /**
     * 后台任务呗取消后回调
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
