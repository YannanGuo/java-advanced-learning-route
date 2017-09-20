package com.guoxiaoxing.java.demo.thread;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/9/20 下午5:02
 */
public class Singleton {

    //volatile保证了：1 instance在多线程并发的可见性 2 禁止instance在操作是的指令重排序
    private volatile static Singleton instance;

    public static Singleton getInstance() {
        //第一次判空，保证不必要的同步
        if (instance == null) {
            //synchronized对Singleton加全局所，保证每次只要一个线程创建实例
            synchronized (Singleton.class) {
                //第二次判空时为了在null的情况下创建实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
