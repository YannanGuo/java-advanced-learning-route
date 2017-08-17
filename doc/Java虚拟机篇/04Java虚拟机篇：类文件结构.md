# Java虚拟机篇：类文件结构

**关于作者**

>郭孝星，非著名程序员，主要从事Android平台基础架构与中间件方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提交Issue或者发邮件至guoxiaoxingse@163.com与我联系。

**文章目录**

>Class文件是一组以8位字节为基础的单位的二进制流，各个数据项按严格的顺序紧密的排列在Class文件中，中间没有任何间隔。

Class文件采用一种类似于C语言结构体的伪结构来存储数据，这种结构有两种数据类型：

- 无符号数：基本数据类型，例如u1代表1个字节，u2代表2个字节。
- 表：由多个无符号数或者其他表作为数据项而构成的复合数据结构，用于描述有层次关系的复合数据结构，一般以"_info"结尾。

|    类型   |      名称      |      数量         |         含义        |
|:---------|:---------------|:-----------------|:--------------------|
|u4        |magic           |1                 |魔数
|u2        |minor_version   |1                 |次版本号
|u2        |major_version   |1                 |主版本号
|u2        |constant_pool_count|1              |常量池容量计数


我们来通过各简单的例子，组个分析每个位所代表的含义。

编写一个简单的Java类

```java
public class StandardClass {

    public int sum(int a, int b) {
        return a + b;
    }
}
```
编译成Class文件

```
javac StandardClass.java编
```

它的十六进制结构如下：

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_hex_structure.png"/>

>魔数：1-4字节，用来确定这个文件是否为一个能被虚拟机接受的Class文件，它的值为0xCAFEBABE。

如图所示：

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_hex_structure_1.png"/>

1-4字节就是魔数。

>版本号：5-6字节是次版本号，7-8字节是主版本号

如果所示：

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_hex_structure_2.png"/>

5-6字节是次版本号0x0000（即0），7-8字节是主版本号0x0034（即52）.

>常量池计数：常量池中常量的数量不是固定的，因此常量池入口处会防止一项u2类型的数据，代表常量池容器计数。注意容器计数从1开始，索引为0代表不引用任何一个
常量池的项目。

如果所示：

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_hex_structure_3.png"/>

9-10字节是常量池容器计数0x000f（即15）。说明常量池里有14个常量，从1-14.

我们用javap命令分析一下字节码文件

```
javap -verbose StandardClass.class
```

![](https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_constant_pool.png)

<img src="https://github.com/guoxiaoxing/java/raw/8a6ae84ba4b8284b2cf036468af267780e773c1c/art/jvm/class_constant_pool.png"/>

如图所示，正如我们分析的那样，常量池里有14个常量，


常量池主要存放字面量与符号引用。字面量包括：

- 类与接口的全限定名
- 字段的名称与描述符
- 方法的名称与描述符

