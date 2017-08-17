# Java虚拟机篇：内存结构

**关于作者**

>郭孝星，非著名程序员，主要从事Android平台基础架构与中间件方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提交Issue或者发邮件至guoxiaoxingse@163.com与我联系。http://blog.csdn.net/allenwells)等博客平台上。文章中如果有什么问题，欢迎发邮件与我交流，邮件可发至guoxiaoxingse@163.com。

Java虚拟机在运行Java程序时会将它管理的内存划分为若干个不同的区域，这些区域有着各自的功能以及创建、销毁时间。其中程序计数器、虚拟机栈
与本地方法栈随线程而生，随线程而灭。而方法区与Java堆则由线程共享，在虚拟机启动时创建。

<img src="https://github.com/guoxiaoxing/java/raw/master/art/jvm/02/jvm_memory_structure.png"/>

## 方法区

>方法区：线程共享，用于存储已被虚拟机加载的类信息，常量，静态变量以及及时编译器编译后的戴拿等数据。方法区还有个别名叫"非堆"。

在Java虚拟机规范中，对方法去的要求比较宽松，可以和Java堆一样不需要连续的内存空间，可以选择固定大小或者扩展，还可以选择不实现垃圾收集。如果选择
实现垃圾收集，那么主要的回收目标在于针对常量池的回收以及类型的卸载。

方法区中有一部分称为运行时常量池，用于存放编译期生成的各种字面量和符号引用。

在Java虚拟机规范中，对该区域规定了一种异常情况：

- OutOfMemoryError：如果虚拟机栈是可以动态扩展的，当扩展时无法申请到足够的内存则抛出该异常。

通过上面关于方法区的描述，我们可以知道造成方法区内存溢出就可能是大佬的常量信息或者是大量的类信息。

方法区在HotSpot虚拟机中也称为永久代，针对这一块区域的垃圾收集主要包括：废弃常量与无用的类，废弃常量与回收Java堆中的对象十分类似，而无用的类的判断
条件要复杂许多，判断条件如下：

- Java堆中不存在该类的任何实例
- 加载该类的ClassLoader已经被回收
- 该类对应的java.lang.Class对象没有在任何地方被引用，无法在任何地方通过反射访问该类的方法。

在大量使用反射，动态代理，CGLib等字节码框架，动态生成JSP以及OSGi这类频繁自定义ClassLoader等场景都要具备类卸载功能，以保证方法区不会溢出。

## Java堆

>Java堆：线程共享，Java虚拟机管理的内存区域中最大的一块，在虚拟机启动时创建，该区域的作用是存放对象实例。

Java堆是垃圾收集器管理的主要区域，现在GC普遍采用分代收集算法，因此Java堆还可以细分为新生代与老年代。另外，Java堆可以处于物理上不连续的内存空间，只要
逻辑上连续就可以了。

在Java虚拟机规范中，对该区域规定了一种异常情况：

- OutOfMemoryError：如果虚拟机栈是可以动态扩展的，当扩展时无法申请到足够的内存则抛出该异常。

也就是说当我们不断地创建对象，当对象数量达到了最大堆容量限制之后就会产生OutOfMemoryError。

**举例**

1 指定堆的最大值与最小值

- -Xmx<size>：最大值，-Xmx1000m
- -Xms<size>：最小值，-Xms1000m

笔者使用的是Android Studio，还可以在gradle.properties中指定：

```java
org.gradle.jvmargs=-Xmx1000m

2 不断的去创建对象

```java
//不断的去创建对象
private void triggerOutOfMemory() {
    List<OutOfMemoryObject> list = new ArrayList<>();
    while (true) {
        list.add(new OutOfMemoryObject());
    }
}

```

程序运行时抛出异常：

```java
Process: com.guoxiaoxing.java.demo, PID: 5508
  java.lang.OutOfMemoryError: Failed to allocate a 30536292 byte allocation with 18525904 free bytes and 17MB until OOM
     at java.util.ArrayList.add(ArrayList.java:118)
     at triggerVMStackOOM(JvmActivity.java:38)
     at com.guoxiaoxing.java.demo.jvm.memory.MemoryActivity.onClick(JvmActivity.java:27)
     at android.view.View.performClick(View.java:5207)
     at android.view.View$PerformClick.run(View.java:21177)
     at android.os.Handler.handleCallback(Handler.java:739)
     at android.os.Handler.dispatchMessage(Handler.java:95)
     at android.os.Looper.loop(Looper.java:148)
     at android.app.ActivityThread.main(ActivityThread.java:5438)
     at java.lang.reflect.Method.invoke(Native Method)
     at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:739)
     at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:629)
```

## 虚拟机栈

>虚拟机栈：线程私有，生命周期与线程相同，虚拟机栈描述Java方法执行的内存模型，每个方法在执行时都会创建一个栈帧，栈帧用于存储局部变量表，操作数
栈，动态链接，方法出口等信息，每个方法从调用到结束就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。

局部变量表存放了各种编译期可知的基本数据类型：

- boolean
- byte
- char
- short
- int
- float
- long
- double
- reference：对象引用，它不等同于对象本身，可能指向对象起始地址的引用指针，也可能指向一个代表对象的句柄或者其他与此对象的相关的位置。

在Java虚拟机规范中，对该区域规定了两种异常情况：

- StackOverFlowError：如果线程请求的栈深度大于虚拟机允许的深度，则抛出该异常。
- OutOfMemoryError：如果虚拟机栈是可以动态扩展的，当扩展时无法申请到足够的内存则抛出该异常。

我们举例看看虚拟机栈里的这两个异常。

**举例**

1 指定栈大小

- -Xss<Size>：栈大小，-Xss1m

2 循环调用方法

```java
public class StackOverflowObject {

    private int stackLength = 1;

    //不断调用方法，创建栈帧，栈帧入栈，最红会超过栈容量
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
}
```
```java
StackOverflowObject stackOverflowObject = new StackOverflowObject();
stackOverflowObject.stackLeak();
```
最终不断调用方法，导致超过栈容量，抛出异常：

```java
Process: com.guoxiaoxing.java.demo, PID: 5309
  java.lang.StackOverflowError: stack size 8MB
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
     at com.guoxiaoxing.java.demo.jvm.memory.StackOverflowObject.stackLeak(StackOverflowObject.java:16)
```

这便是发生StackOverFlowError的情况，当栈帧太大/太多或者虚拟机栈容量太小都会导致StackOverFlowError。那么什么时候会发生OutOfMemoryError，我们知道
虚拟机是线程私有的，随着线程的创建而创建，当我们不断去创建新线程，当内存被分配殆尽时就会发生OutOfMemoryError。

## 本地方法栈

>本地方法栈：线程私有，它与虚拟机栈十分相似，相对于虚拟机栈为Java方法服务，本地方法栈为Native方法服务。

本地方法栈也同虚拟机栈一样规定了StackOverFlowError与OutOfMemoryError两种异常。

值得一提的是，我们常用的HotSpot虚拟机并不区分虚拟机栈与本地方法栈，它们是一体的。

## 程序计数器

>程序计数器：空间较小，每个线程私有，当前线程所执行字节码的行号指示器，字节码解释器工作时就是通过改变这个计数器来选择下一条需要执行的字节码指令。
如果执行的是Java方法，则该计数器记录的是正在执行的虚拟机字节码指令的地址；如果是Native方法，则计数器的值为空。

## 附录

JVM配置参数

|参数                   |含义                          |
|:---------------------|:-----------------------------|
|-Xmixed               |混合模式执行
|-Xint                 |解释模式执行
|-Xbootclasspath       |设置zip/jar资源或者类（.class文件）存放目录路径
|-Xnoclassgc           |关闭类垃圾回收功能  
|-Xincgc               |开启类的垃圾回收功能  
|-Xloggc               |记录垃圾回日志到一个文件  
|-Xbatch               |关闭后台编译  
|-Xms<size>            |设置Java堆最小值
|-Xmx<size>            |设置Java堆最大值 
|-Xss<size>            |设置虚拟机栈大小