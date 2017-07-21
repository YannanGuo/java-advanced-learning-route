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

1 类与成员的可访问性最小化

2 在公有类中使用方法而非公有域

3 使可变现最小化

>不可变类指的是其实例不能被修改的类，每个实例包含的信息必须在创建的时候提供，并在对象的整个生命周期类固定不变。不可变对象在本质

- 不要提供任何修改对象状态的方法
- 保证类不会被扩展
- 使所有域都是final的
- 使所有域都是private的
- 确保对任何可变组件的互斥访问

