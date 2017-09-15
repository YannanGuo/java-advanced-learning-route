# Java编程篇：Java IO 概述

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


>Java的IO建立在流之上，InputStream读取数据，OutputStream写入数据，不同的流类会读/写某个特定的数据源，但是它们都会使用相同的方法
来读/写数据。Filter可以串链到输入流/输出流上，以此来实现加密或压缩等功能。Reader/Writer可以串链到输入流/输出流上，允许程序读写文本
即字符，而不是字节。


## 输出流

Java的基本输出流类是OutputStream，它由5个基本方法：

```java
public abstract class OutputStream implements Closeable, Flushable {

    public abstract void write(int b) throws IOException;

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                   ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        for (int i = 0 ; i < len ; i++) {
            write(b[off + i]);
        }
    }

    public void flush() throws IOException {
    }


    public void close() throws IOException {
    }
}
```
OutputStream由许多子类，它们分别实现了向某种特定介质写入数据的功能，例如：FileOutputStream将数据写入文件，TelnetOutputStream将
数据写入网络连接，不管是哪一个子类，它都会用上面的5个方法进行操作。

```
public abstract void write(int b) throws IOException;
```
write方法接收一个int作为参数，实际上它会写入一个无符号字节，接收的范围为0~255，如果超出255则写入这个数的最低字节，其他3个字节将被忽略。
