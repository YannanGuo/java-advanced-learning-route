# Java编程篇：创建与销毁对象

**关于作者**

>郭孝星，非著名程序员，主要从事Android平台基础架构与中间件方面的工作，爱好广泛，技术栈主要涉及以下几个方面
>
>- Android/Linux
>- Java/Kotlin/JVM
>- Python
>- JavaScript/React/ReactNative
>- DataStructure/Algorithm
>
>文章首发于[Github](https://github.com/guoxiaoxing)，后续也会同步在[简书](http://www.jianshu.com/users/66a47e04215b/latest_articles)与
[CSDN](http://blog.csdn.net/allenwells)等博客平台上。文章中如果有什么问题，欢迎发邮件与我交流，邮件可发至guoxiaoxingse@163.com。

## 静态工厂方法构建对象

我们在构建对象时通常会考虑采用静态工厂方法，而非是提供类的构造器，这样做有以下优点：

1 静态工厂方法方法因为可以指定不同的名称，因此拥有更好的可读性。

例如：

```java
//返回值可能为素数
public (int bitLength, int certainty, Random random)

//我们用静态工厂方法来获取素数实例，则更加清晰易懂
public static BigInteger probablePrime(int bitLength, Random random) 
```

2 静态工厂方法不必每次调用它们的时候都创建一个新的实例

这种情况就是我们通常用来实现一个单例，如下：

```java
public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}
```
3 我们可以通过工厂方法返回该类型的任何子类型。

这种情况通常用于基于接口的框架中，例如Java集合框架等，这种方法既可以返回对象，同时又不会使对象变成公有的。