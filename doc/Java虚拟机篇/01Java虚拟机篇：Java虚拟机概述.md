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

JVM作为Java技术体系的重要组成部分，随着Java语言的发展，JVM也欣欣向荣的向前发展，本系列文章主要探讨HotSpot VM，这款同时作为Oracle JDK与Open JDK
所带的虚拟机，最初由LongView Technologics公司设计，后被Sun公司收购发展而来。

另外许多语言也提供了自己额JVM实现版本，举两个笔者常用的语言：

- JavaScript：Rhino
- Python：Jython

这些运行在Java虚拟机之上，Java语言之外的语言，正在推动着Java虚拟机从"Java语言的虚拟机"发展成"多语言虚拟机"发展。

要理解JVM以及JDK内部的实现机制，最便捷的途径莫过于在即去编译一套JDK，通过跟踪调试去理解Java技术体系的原理。




## 附录

- [jclasslib](http://git.oschina.net/mirrors/jclasslib)：字节码查看工具