package com.guoxiaoxing.java.demo.thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.guoxiaoxing.java.demo.R;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/9/15 下午2:39
 */
public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Thread";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        findViewById(R.id.btn_thread_thread_info).setOnClickListener(this);
        findViewById(R.id.btn_thread_interrupt_thread).setOnClickListener(this);
        findViewById(R.id.btn_thread_thread_priority).setOnClickListener(this);
        findViewById(R.id.btn_thread_synchronized).setOnClickListener(this);
        findViewById(R.id.btn_thread_volatile).setOnClickListener(this);
        findViewById(R.id.btn_thread_current_thread).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_thread_thread_info:
                checkThreadInfo();
                break;
            case R.id.btn_thread_interrupt_thread:
                interruptThread();
                break;
            case R.id.btn_thread_thread_priority:
                threadPriority();
                break;
            case R.id.btn_thread_synchronized:
                synchronizedKeyword();
                break;
            case R.id.btn_thread_volatile:
                volatileKeyword();
                break;
            case R.id.btn_thread_current_thread:
                currentThread();
                break;
        }
    }

    private void checkThreadInfo() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long start = SystemClock.currentThreadTimeMillis();
                for (int i = 0; i < 10000; i++) {
//                    Thread.yield();
                    Log.d(TAG, "checkThreadInfo() running " + i);
                }
                long end = SystemClock.currentThreadTimeMillis();
                Log.d(TAG, "cost time " + (end - start));
            }
        };

        Thread thread = new Thread(runnable);
        thread.setName("checkThreadInfo() thread");
        thread.start();

        Log.d(TAG, "thread.getName(): " + thread.getName());
        Log.d(TAG, "thread.isAlive(): " + thread.isAlive());
        Log.d(TAG, "thread.getPriority(): " + thread.getPriority());
        Log.d(TAG, "thread.getContextClassLoader(): " + thread.getContextClassLoader());
        Log.d(TAG, "thread.getStackTrace(): " + thread.getStackTrace());
    }

    private void interruptThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "interruptThread() running");
            }
        };

        Thread thread = new Thread(runnable);
        thread.setName("interruptThread() thread");
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        Log.d(TAG, "thread.isAlive(): " + thread.isAlive());
    }

    private void threadPriority() {

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                long start = SystemClock.currentThreadTimeMillis();
                for (int i = 0; i < 100; i++) {
                    Log.d(TAG, "thread1 running " + i);
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.setPriority(1);


        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                long start = SystemClock.currentThreadTimeMillis();
                for (int i = 0; i < 100; i++) {
                    Log.d(TAG, "thread2 running " + i);
                }
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.setPriority(10);

        thread1.start();
        thread2.start();
    }

    private void synchronizedKeyword() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 10; i++) {
                        Log.d(TAG, "synchronizedKeyword: " + i);
                    }
                }
            }
        };

        Thread thread1 = new Thread(runnable, "thread1");
        Thread thread2 = new Thread(runnable, "thread2");

        thread1.start();
        thread2.start();
    }


    private volatile int start = 0;

    private void volatileKeyword() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 10; i++) {
                        start++;
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        Log.d(TAG, "start = " + start);
    }

    private void currentThread() {
        Log.d(TAG, "Thread.currentThread(): " + Thread.currentThread());
    }
}
