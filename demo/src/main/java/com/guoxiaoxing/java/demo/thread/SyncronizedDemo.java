package com.guoxiaoxing.java.demo.thread;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/9/20 下午2:16
 */
public class SyncronizedDemo {

    private int age;

    private synchronized void set(int age) {
        this.age = age;
    }

    private synchronized int getAgjavae() {
        return age;
    }
}