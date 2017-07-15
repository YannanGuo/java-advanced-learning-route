# Java虚拟机篇：Java虚拟机概述

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

Java中有四种引用。

- 强引用：代码中普遍存在的，只要强引用还存在，垃圾收集器就不会回收掉被引用的对象。
- 软引用：SoftReference，用来描述还有用但是非必须的对象，当内存不足的时候回回收这类对象。
- 弱引用：WeakReference，用来描述非必须对象，弱引用的对象只能生存到下一次GC发生时，当GC发生时，无论内存是否足够，都会回收该对象。
- 虚引用：PhantomReference，一个对象是否有虚引用的存在，完全不会对其生存时间产生影响，也无法通过虚引用取得一个对象的引用，它存在的唯一目的是在这个对象被回收时可以收到一个系统通知。

