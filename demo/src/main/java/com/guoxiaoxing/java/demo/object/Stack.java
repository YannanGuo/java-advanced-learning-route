package com.guoxiaoxing.java.demo.object;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/6 下午4:16
 */
public class Stack {

    private int size = 0;
    private Object[] elements;

    public Stack() {
        this.elements = new Object[10];
    }

    public void push(Object object) {
        elements[size++] = object;
    }

    public Object pop() {
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    @Override
    public Stack clone() {
        try {
            return (Stack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
