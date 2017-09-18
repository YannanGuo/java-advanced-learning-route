package com.guoxiaoxing.java.demo.thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.guoxiaoxing.java.demo.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        findViewById(R.id.btn_thread_wait_and_notify).setOnClickListener(this);
        findViewById(R.id.btn_thread_join).setOnClickListener(this);
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
            case R.id.btn_thread_wait_and_notify:
                waitAndNotigy();
                break;
            case R.id.btn_thread_join:
                join();
                break;
            case R.id.btn_thread_thread_pool:
                threadPool();
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

        try {
            Log.d(TAG, "try");
            return;
        } catch (Exception e) {
            Log.d(TAG, "catch");
        } finally {
            Log.d(TAG, "finally");
        }
    }

    private void waitAndNotigy() {
        final String str = new String("I am wait");

        Runnable waitRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "synchronized before");
                    synchronized (str) {
                        Log.d(TAG, "wait before");
                        str.wait();
                        Log.d(TAG, "wait after");
                    }
                    Log.d(TAG, "synchronized after");
                } catch (Exception e) {
                    Log.d(TAG, "waitRunnable error: " + e.getMessage());
                }
            }
        };

        Thread waitThread = new Thread(waitRunnable);
        waitThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable notifyRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "synchronized before");
                synchronized (str) {
                    Log.d(TAG, "notify before");
                    str.notify();
                    Log.d(TAG, "notify after");
                }
                Log.d(TAG, "synchronized after");
            }
        };
        Thread notifyThread = new Thread(notifyRunnable);
        notifyThread.start();
    }

    private void join() {
        Runnable joinRunnable = new Runnable() {
            @Override
            public void run() {
                int sleepTIme = (int) (Math.random() * 10000);
                Log.d(TAG, "sleepTIme: " + sleepTIme);
                try {
                    Thread.sleep(sleepTIme);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG, "InterruptedException: " + e.getMessage());
                }
            }
        };

        Thread joinThread = new Thread(joinRunnable);
        joinThread.start();
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "InterruptedException: " + e.getMessage());
        }
        Log.d(TAG, "joinThread已经执行完毕");
    }

    private void threadPool() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "I am runnable");
            }
        };

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                Log.d(TAG, "I am runnable");
                return "callable";
            }
        };

        executorService.submit(runnable);
        Future<Object> result = executorService.submit(callable);

        try {
            Log.d(TAG, "Callable reslut: " + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void currentThread() {
        Log.d(TAG, "Thread.currentThread(): " + Thread.currentThread());
    }
}
