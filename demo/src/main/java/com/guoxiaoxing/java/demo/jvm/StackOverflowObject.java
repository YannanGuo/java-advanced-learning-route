package com.guoxiaoxing.java.demo.jvm;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/13 下午3:00
 */
public class StackOverflowObject {

    private int stackLength = 1;

    //不断调用方法，创建栈帧，栈帧入栈，最红会超过栈容量
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
}
