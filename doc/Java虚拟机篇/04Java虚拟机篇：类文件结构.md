# Java虚拟机篇：类文件结构

**关于作者**

>郭孝星，非著名程序员，主要从事Android平台基础架构与中间件方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提交Issue或者发邮件至guoxiaoxingse@163.com与我联系。

>Class文件是一组以8位字节为基础的单位的二进制流，各个数据项按严格的顺序紧密的排列在Class文件中，中间没有任何间隔。

Class文件采用一种类似于C语言结构体的伪结构来存储数据，这种结构有两种数据类型：

- 无符号数：基本数据类型，例如u1代表1个字节，u2代表2个字节。
- 表：由多个无符号数或者其他表作为数据项而构成的复合数据结构，用于描述有层次关系的复合数据结构，一般以"_info"结尾。

|    类型   |      名称      |      数量         |         含义        |
|:---------|:---------------|:-----------------|:--------------------|
|u4        |magic           |1                  |
|u4
|u4

编写一个简单的Java类

```java
public class StandardClass {

    public int sum(int a, int b) {
        return a + b;
    }
}
```
它的十六进制结构如下：

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_hex_structure.png"/>

>魔数：1-4字节，用来确定这个文件是否为一个能被虚拟机接受的Class文件，它的值为0xCAFEBABE。


>版本号：5-6字节次版本号，7-8主版本号

