# Java虚拟机篇：内存结构

**关于作者**

>郭孝星，程序员，吉他手，主要从事Android平台基础架构方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提issue或者发邮件至guoxiaoxingse@163.com与我交流。

Java虚拟机在运行Java程序时会将它管理的内存划分为若干个不同的区域，这些区域有着各自的功能以及创建、销毁时间。其中程序计数器、虚拟机栈
与本地方法栈随线程而生，随线程而灭。而方法区与Java堆则由线程共享，在虚拟机启动时创建。

<img src="https://github.com/guoxiaoxing/java/raw/master/art/jvm/02/jvm_memory_structure.png"/>


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