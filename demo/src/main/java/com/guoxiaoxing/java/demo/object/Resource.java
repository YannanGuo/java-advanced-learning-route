package com.guoxiaoxing.java.demo.object;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/5 下午3:12
 */
public class Resource {

    private boolean isClose;

    public void open() {
        //记录状态
        isClose = false;

        //初始化资源

    }

    public void close() {
        //我们通常在finally里关闭资源，这样即使发生了异常，也能确保资源被关闭
        try {
            //其他必要的操作
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记录状态
            isClose = true;

            //关闭资源
        }
    }


    private final Teacher finalizeGuardian = new Teacher(){
        @Override
        protected void finalize() throws Throwable {
            try {

            }
            //保证即便该对象发生异常也能正常的调用finalize()方法
            finally {
                //终结外围实例，释放资源
                super.finalize();
            }
        }
    };

    @Override
    protected void finalize() throws Throwable {

        try {

        }
        //保证即便该对象发生异常也能正常的调用finalize()方法
        finally {
            super.finalize();
        }
    }
}
