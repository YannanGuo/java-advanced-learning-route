# Java进阶篇：多线程并发实践

**关于作者**

>郭孝星，非著名程序员，主要从事Android平台基础架构与中间件方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提交Issue或者发邮件至guoxiaoxingse@163.com与我联系。net/allenwells)等博客平台上。文章中如果有什么问题，欢迎发邮件与我交流，邮件可发至guoxiaoxingse@163.com。

**文章目录**

- 线程基础
- 并发实践

本篇文章用来分析Java中多线程并发原理与实践。:thinking:

实现任务的接口分为两类：

- Runnable：在run()方法里完成任务，无返回值，且不会抛出异常。
- Callable：在call()方法里完成任务，有返回值，且可能抛出异常。

## 一 线程基础

>进程（英语：process），是计算机中已运行程序的实体。在Android中进程就是一个运行的应用。

>线程（英语：thread）是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。一条线程指的是进程中一个单一顺序的控制流，一个进程中可以并发多个线程，每条线程并行执行不同的任务。

简单莱索，线程就是进程更加细粒度的划分，是独立调度的最小单位。

>线程安全是编程中的术语，指某个函数、函数库在多线程环境中被调用时，能够正确地处理多个线程之间的共享变量，使程序功能正确完成。

### 1.2 线程优先级

>线程优先级：每个线程都自己的优先级，优先级高的线程会被优先执行。

关于线程优先级

- 最小优先级MIN_PRIORITY = 1，默认优先级NORM_PRIORITY = 5，最大优先级MAX_PRIORITY = 10。
- 线程优先级具有继承性，例如线程A启动线程B，则B与A有相同的优先级。
- 优先级高的线程会被优先执行，因此，当两个线程优先级差别很大时，谁先执行完和代码的调用顺序无关，当然线程时间片的获取具有随机性，优先级高的线程未必就先执行完。

### 1.3 线程状态

- NEW：线程创建状态，线程创建之后，但是还未启动。
- RUNNABLE：运行状态，处于运行状态的线程，但有可能处于等待状态，例如等待CPU、IO等。
- WAITING：等待状态，一般是调用了wait()、join()、LockSupport.spark()等方法。
- TIMED_WAITING：超时等待状态，也就是带时间的等待状态。一般是调用了wait(time)、join(time)、LockSupport.sparkNanos()、LockSupport.sparkUnit()等方法。
- BLOCKED：阻塞状态，等待锁的释放，例如调用了synchronized增加了锁。
- TERMINATED：线程终止状态，一般是线程完成任务后退出或者异常终止。 

NEW、WAITING、TIMED_WAITING都比较好理解，我们重点说一说RUNNABLE运行态和BLOCKED阻塞态。

线程进入RUNNABLE运行态一般分为五种情况：

- 线程调用sleep(time)后查出了休眠时间
- 线程调用的阻塞IO已经返回，阻塞方法执行完毕
- 线程成功的获取了资源锁
- 线程正在等待某个通知，成功的获得了其他线程发出的通知
- 线程处于挂起状态，然后调用了resume()恢复方法，解除了挂起。

线程进入BLOCKED阻塞态一般也分为五种情况：

- 线程调用sleep()方法主动放弃占有的资源
- 线程调用了阻塞式IO的方法，在该方法返回前，该线程被阻塞。
- 线程视图获得一个资源锁，但是该资源锁正被其他线程锁持有。
- 线程正在等待某个通知
- 线程调度器调用suspend()方法将该线程挂起

### 1.4 线程方法

提到线程的状态，我们再来理解一下导致状态切换的几个调度方法。

- sleep()方法让当前正在执行的线程在指定时间内暂停执行，正在执行的线程可以通过Thread.currentThread()方法获取。
- yield()方法放弃线程持有的CPU资源，将其让给其他任务去占用CPU执行时间。但放弃的时间不确定，有可能刚刚放弃，马上又获得CPU时间片。
- wait()方法是当前执行代码的线程进行等待，将当前线程放入预执行队列，并在wait()所在的代码处停止执行，知道接到通知或者被中断为止。该方法可以使得调用该方法的线程释放共享资源的锁，
然后从运行状态退出，进入等待队列，直到再次被唤醒。该方法只能在同步代码块里调用，否则会抛出IllegalMonitorStateException异常。
- wait(long millis)方法等待某一段时间内是否有线程对锁进行唤醒，如果超过了这个时间则自动唤醒。
- notify()方法用来通知那些可能等待该对象的对象锁的其他线程，该方法可以随机唤醒等待队列中等同一共享资源的一个线程，并使该线程退出等待队列，进入可运行状态。
- notifyAll()方法可以是所有正在等待队列中等待同一共享资源的全部线程从等待状态退出，进入可运行状态，一般会是优先级高的线程先执行，但是根据虚拟机的实现不同，也有可能是随机执行。
- join()方法可以让调用它的线程正常执行完成后，再去执行该线程后面的代码，它具有让线程排队的作用。

### 1.5 线程同步

#### synchronized

>互斥锁：在Java中，每一个对象都拥有一个锁标记（monitor），也称为监视器，多线程同时访问某个对象时，线程只有获取了该对象的锁才能访问。

>synchronized：当某个线程访问被synchronized标记的方法或代码块时，这个线程便获得了该对象的锁，其他线程暂时无法访问这个方法，只有等待这个方法执行完毕或者代码块执行完毕，这个
线程才会释放该对象的锁，其他线程才能执行这个方法或代码块。

关于synchronized关键字
                                               
- synchronized代码块 ：同步代码块，作用范围是整个代码块，作用对象是调用这个代码块的对象。
- synchronized方法 ：同步方法，作用范围是整个方法，作用对象是调用这个方法的对象。
- synchronized静态方法 ：同步静态方法，作用范围是整个静态方法，作用对象是调用这个类的所有对象。
- synchronized(ClassName.class) ：全局锁，作用对象是这个类的所有对象。
- synchronized(this)：作用范围是该对象中所有被synchronized标记的变量、方法或代码块，作用对象是对象本身。

#### volatile

- 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
- 禁止进行指令重排序。

### 线程调度

自Java 1.5开始，Java引入Executor框架来控制线程的启动、执行与关闭，来简化并发编程的操作。

在整套Executor框架里，Executor接口位于顶层的位置，它就定义了一个方法用来执行Runnable。

```java
public interface Executor {
    void execute(Runnable command);
}

```
我们更常用的是ExecutorService接口，该接口继承于Executor接口，提供了更多的功能。

#### 线程池

我们知道线程的创建、切换与销毁都会花费比较大代价，所以很自然的我们使用线程池来复用和管理线程。Java里的线程池通过ThreadPoolExecutor来实现。

我们先来看看ThreadPoolExecutor的构造方法：

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```

- int corePoolSize：核心线程池大小
- int maximumPoolSize：线程池最大容量大小
- long keepAliveTime：线程不活动时存活的时间
- TimeUnit unit：时间单位
- BlockingQueue<Runnable> workQueue：任务队列
- ThreadFactory threadFactory：线程工程
- RejectedExecutionHandler handler：线程拒绝策略

线程池的五种状态：

- RUNNING：可以接受新任务，也可以处理等待队列里的任务。
- SHUTDOWN：不接受新任务，但可以处理等待队列里的任务。
- STOP：不接受新的任务，不再处理等待队列里的任务。中断正在处理的任务。
- TIDYING：所有任务都已经处理完了，当前线程池没有有效的线程，并且即将调用terminated()方法。
- TERMINATED：调用了erminated()方法，线程池终止。


Executors提供了一系列工厂方法用来创建线程池。

- newCachedThreadPool()：无界可自动回收线程池，查看线程池中有没有以前建立的线程，如果有则复用，如果没有则建立一个新的线程加入池中，池中的线程超过60s不活动则自动终止。适用于生命
周期比较短的异步任务。

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```

- newFixedThreadPool(int nThreads)：固定大小线程池，与newCachedThreadPool()类似，但是池中持有固定数目的线程，不能随时创建线程，如果创建新线程时，超过了固定
线程数，则放在队列里等待，直到池中的某个线程被移除时，才加入池中。适用于很稳定、很正规的并发线程，多用于服务器。


```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

- newScheduledThreadPool(int corePoolSize)：周期任务线程池，该线程池的线程可以按照delay依次执行线程，也可以周期执行。

```java
public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
    return new ScheduledThreadPoolExecutor(corePoolSize);
}
```

- newSingleThreadExecutor()：单例线程池，任意时间内池中只有一个线程。

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

## 并发实践

- AsyncTask：为UI线程与工作线程之间提供了一种快速便捷的切换机制，适用于当下需要立即启动，而且异步执行的生命周期比较短暂的使用场景。
- HandlerThread：就像它的名字那样，由Handler和Thread组合而成，它适用于为回调方法或者等待某些任务的执行设置一个专属线程，并提供线程任务的调度机制。
- ThreadPool：线程池，把任务分解成不同的单元，分发到各个不同的线程上，进行同时并发处理。
- IntentService：适用于由UI触发的后台Service任务，并可以把后台任务的执行情况反馈给UI。

### 

AsyncTask




