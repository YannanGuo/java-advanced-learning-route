# Android开发工程师面试大纲

## Android

### Activity与Fragment的生命周期。
### Acitivty的四中启动模式与特点。
### Activity缓存方法。
### Service的生命周期，两种启动方法，有什么区别。
### 怎么保证service不被杀死。
### 广播的两种注册方法，有什么区别。
### Intent的使用方法，可以传递哪些数据类型。
### ContentProvider使用方法。
### Thread、AsycTask、IntentService的使用场景与特点。
### FrameLayout , LinearLayout  AbsoluteLayout 、 RelativeLayout 、 TableLayout各自特点及绘制效率对比。
### Android的数据存储形式。
### Sqlite的基本操作。
### Merge、ViewStub的作用。
### 动画有哪两类，各有什么特点？
### Handler、Loop消息队列模型，各部分的作用。
### 怎样退出终止App。
### Asset目录与res目录的区别。
### Android怎么加速启动Activity。
### Android内存优化方法：ListView优化，及时关闭资源，图片缓存等等。
### Android中弱引用与软引用的应用场景。
### Bitmap的四中属性，与每种属性队形的大小。
### View与View Group分类。自定义View过程：onMeasure()、onLayout()、onDraw()。
### Touch事件分发机制。
### Android长连接，怎么处理心跳机制。
### Zygote的启动过程。
### Android IPC，Binder原理。
### 你用过什么框架，是否看过源码，是否知道底层原理。
### Android5.0、6.0新特性。

## Java SE

### Object有哪些公用方法，各有什么作用？
### Java里的四种引用，它们的应用场景是什么？ 
### ArrayList、LinkedList与Vector的区别是什么，它们是如何实现的？
### TreeMap、HashMao、LinkedHashMao的区别是什么，它们是如何实现的？ 
### 线程同步有哪些方法，synchronized、volatile有什么含义和用途？
### ThreadLocal的设计概念与作用？
### ThreadPool的用法与优势？
### Thread里有哪些常用的方法，它们的作用是什么，wait()与sleep()有什么区别？
### for与for-each效率对比，它们有什么区别？
### 发射的作用和原理是什么？

## Java虚拟机

### 内存模型以及分区，需要详细到每个区放什么。
### 堆里面的分区：Eden，survival from to，老年代，各自的特点。
### 对象创建方法，对象的内存分配，对象的访问定位。
### GC的两种判定方法：引用计数与引用链。
### GC的三种收集方法：标记清除、标记整理、复制算法的原理与特点，分别用在什么地方，如果让你优化收集方法，有什么思路？
### GC收集器有哪些？CMS收集器与G1收集器的特点。
### Minor GC与Full GC分别在什么时候发生？
### 几种常用的内存调试工具：jmap、jstack、jconsole。
### 类加载的五个过程：加载、验证、准备、解析、初始化。
### 双亲委派模型：Bootstrap ClassLoader、Extension ClassLoader、ApplicationClassLoader。
### 分派：静态分派与动态分派。

## 计算机网络

### OSI与TCP/IP各层的结构与功能，都有哪些协议？
### TCP与UDP有什么区别？
### TCP的三次握手与四次挥手过程，各个状态名称与含义，TIMEWAIT的作用。
### TCP拥塞控制。
### TCP滑动窗口与回退N针协议。
### Http的报文结构。
### Http的状态码含义。
### Http request的几种类型。
### Http1.1和Http1.0的区别
### Http怎么处理长连接。
### Cookie与Session的作用于原理。

## 设计模式

### 单例、
### 工厂
### 适配器
### 责任链
### 观察者等等。

## 数据结构与算法

### 链表与数组。
### 队列和栈，出栈与入栈。
### 链表的删除、插入、反向。
### 字符串操作。
### Hash表的hash函数，冲突解决方法有哪些。
### 各种排序：冒泡、选择、插入、希尔、归并、快排、堆排、桶排、基数的原理、平均时间复杂度、最坏时间复杂度、空间复杂度、是否稳定。
### 快排的partition函数与归并的Merge函数。
### 对冒泡与快排的改进。
### 二分查找，与变种二分查找。
### 二叉树、B+树、AVL树、红黑树、哈夫曼树。
### 二叉树的前中后续遍历：递归与非递归写法，层序遍历算法。
### 图的BFS与DFS算法，最小生成树prim算法与最短路径Dijkstra算法。
### KMP算法。
### 排列组合问题。
### 动态规划、贪心算法、分治算法。（一般不会问到）
### 大数据处理：类似10亿条数据找出最大的1000个数.........等等

## 项目经验

### XXX（某个比较重要的点）是怎么实现的？
### 你在项目中遇到的最大的困难是什么，怎么解决的？
### 项目某个部分考虑的不够全面，如果XXXX，你怎么优化？
### XXX（一个新功能）需要实现，你有什么思路？

## 梦想系列

### 贵公司一向以XXX著称，能不能说明一下公司这方面的特点？
### 贵公司XXX业务发展很好，这是公司发展的重点么？
### 对技术和业务怎么看？
### 贵公司一般的团队是多大，几个人负责一个产品或者业务？
### 贵公司的开发中是否会使用到一些最新技术？
### 对新人有没有什么培训，会不会安排导师？
### 对Full Stack怎么看？
### 你觉得我有哪些需要提高的地方？