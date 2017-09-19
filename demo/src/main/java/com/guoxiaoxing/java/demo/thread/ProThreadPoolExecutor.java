package com.guoxiaoxing.java.demo.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/9/19 上午11:43
 */
public class ProThreadPoolExecutor {

    /**
     * CPU核心数，注意该方法并不可靠，它返回的有可能不是真实的CPU核心数，因为CPU在某些情况下会对某些核
     * 心进行睡眠处理，这种情况返回的知识已激活的CPU核心数。
     */
    private static final int NUMBER_OF_CPU = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数
     */
    private static final int corePoolSize = Math.max(2, Math.min(NUMBER_OF_CPU - 1, 4));

    /**
     * 最大线程数
     */
    private static final int maximumPoolSize = NUMBER_OF_CPU * 2 + 1;

    /**
     * 线程空闲后的存活时间
     */
    private static final int keepAliveTime = 60;

    /**
     * 阻塞队列
     */
    BlockingDeque<Runnable> blockingDeque;

    /**
     * 拒绝策略
     */
    RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

    ThreadPoolExecutor proThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            blockingDeque,
            rejectedExecutionHandler);
}
