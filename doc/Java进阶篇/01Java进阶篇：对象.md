# Java编程篇：对象

**关于作者**

>郭孝星，程序员，吉他手，主要从事Android平台基础架构方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提issue或者发邮件至guoxiaoxingse@163.com与我交流。

## 对象的创建与销毁

### 1 静态工厂方法构建对象

我们在构建对象时通常会考虑采用静态工厂方法，而非是提供类的构造器.

#### 优点

1 静态工厂方法方法因为可以指定不同的名称，因此拥有更好的可读性。

例如：

```java
//返回值可能为素数
public (int bitLength, int certainty, Random random)

//我们用静态工厂方法来获取素数实例，则更加清晰易懂
public static BigInteger probablePrime(int bitLength, Random random) 
```

2 静态工厂方法不必每次调用它们的时候都创建一个新的实例这种情况就是我们通常用来实现一个单例，如下：

```java
public class Singleton {

    private volatile static Singleton instance;

    //通过将构造器私有化来避免外界调用
    private Singleton() {
        
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}
```
3 我们可以通过工厂方法返回该类型的任何子类型。这种情况通常用于基于接口的框架中，例如Java集合框架等，这种方法既可以返回对象，同时又不会使对象变成公有的。

4 静态工厂方法在创建参数化类型实例的时候，它使得代码变得更加简洁。

```java
Map<String, List<String>> map = new HashMap<>();
```

```java
public static Map<K, V> newInstance(){
    return new HashMap<K, V>();
}
```
#### 缺点

1 如果类只有private类型的构造器，那么它无法被子类化，这其实也是件好事，因为我们本来就鼓励使用复合，而非继承。

2 静态工厂方法与其他其他静态方法并没有任何区别，在API文档中，它并没有像构造器那样被明确标明出来，所以对于使用者来说不易
寻找，因此我们在使用静态工厂方法要遵循一些惯用名称。

- valueOF()
- of()
- getInstance()
- newInstance()
- getType()
- newType()

### 2 遇到多个构造器要考虑Builder设计模式

#### 优点

如果类的构造器或者静态工厂方法中存在多个参数，那么Builder模式是一个不错的选择，我们常用的Builder模式实现如下：

```java
public class InstanceBuilder {

    private String name;
    private String value;

    //通过将构造器私有化来避免外界调用
    private InstanceBuilder(Builder builder) {
        this.name = builder.name;
        this.value = builder.value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    static class Builder {
        private String name;
        private String value;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public InstanceBuilder build() {
            return new InstanceBuilder(this);
        }
    }
}

```

这种方法还有个好处就是可以对每个参数都添加默认值以及约束条件，当不满足约束条件时，你可以抛出IllegalStateException。

#### 缺点

Builder模式中你如果想创建对象就必需先创建Builder对象，这会带来额外的开销，这在一些重视性能的系统中会凸显的比较明显。

### 3 避免创建不必要的对象

有些对象的创建时重量级的，十分耗时，例如数据库连接池，这个时候我们就要去复用这些对象，而不是去频繁的创建，但是我们也不应该就
觉得创建对象的代价非常昂贵，我们应该尽可能的避免创建对象，这是一个错误的想法，对于一些小对象，它们的创建与回收是十分廉价的，通过
创建附加的对象，来提升程序的清晰性、简洁性与功能性，这通常是件好事。相反，为了避免创建对象而去维护自己的对象池，反而会破坏代码的结构。
得不偿失。

### 4 清除过期的对象引用

有些过期对象的引用由于这些引用不会被解除从而引发内存泄漏，常见的发生内存泄漏的情况有：

1 类自己管理内存

例如：Stack（栈）自己管理内存，当栈pop出一个元素之后，虽然我们知道这个元素已经没有用了，但是对于GC来说，栈内的每个元素同等有效，它
并不会去回收这些pop出来的元素。这个时候就需要我们手动把这些元素置null来清除过期引用。

2 缓存

当我们把一个对象引用放入缓存中，我们往往很容易遗忘它，这个时候它就一直存在于缓存中，这种情况下，我们应该启动一个后台线程，定期检查
这些缓存的有效性，然后选择性的移除。

3 监听器与其他回调

如果你实现了一个API，客户端在这个API里注册监听，却没有显示的取消注册，那么这些监听就会集聚，从而引发内存泄漏。有两个方法，一是定期
根据一定的策略清楚这些监听，而是使用WeakHashMap以弱引用的方式来保存它们。

## 对象的通用方法

所有的Java对象都继承于Object，Object本身就被设计成一个呗继承的类。

### 1 equals()

equals()方法在Object里的默认实现如下：

```java
public boolean equals(Object obj) {
    return (this == obj);
}

```
可以看出"=="比较的是两个对象的地址，如果我们想要实现对象的逻辑相等，就需要去覆写equals()方法，并遵守以下约定：

- 自反性
- 对称性
- 传递性
- 一致性

我们来看一个具体的例子来学习如果正确的覆写equals()方法。

```java
public class Teacher {

    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        //1 使用==操作符检车参数是否为这个对象引用，如果是则返回true
        if (this == obj) {
            return true;
        }
        //2 使用instanceof操作符检查对象是否为该类的实例，如果不是则返回false
        if (!(obj instanceof Teacher)) {
            return false;
        }
        //3 将对象转换为具体的类型
        Teacher teacher = (Teacher) obj;

        //4 为了提交方法性能，首先检查哪些最有可能不一致的域或者比较开销最低的域
        if (this.age != teacher.age) {
            return false;
        }
        return name == null ? teacher.name == null : this.name.equals(teacher.name);

    }

    @Override
    public int hashCode() {
        //1 将一个非0的常数值保存到一个名为result的int型变量中。
        int result = 31;
        //2 分别计算每个域的散列码并相加求和
        result = result + age + (name == null ? 0 : name.hashCode());
        return result;
    }
}
```

覆写equals()方法的步骤：

1. 使用==操作符检车参数是否为这个对象引用，如果是则返回true。
2. 使用instanceof操作符检查对象是否为该类的实例，如果不是则返回false。
3. 将对象转换为具体的类型。
4. 为了提交方法性能，首先检查哪些最有可能不一致的域或者比较开销最低的域。

关于域的比较：

基本类型：==
float：Float.compare()
double: Double.compare()
引用类型: equals()

对于可能为null的域可以采用以下方式来比较：

```java
name == null ? teacher.name == null : this.name.equals(teacher.name)
```
### 2 hashCode()

Object里hashCode()方法的默认实现

```java
public int hashCode() {
    int lockWord = shadow$_monitor_;
    final int lockWordStateMask = 0xC0000000;  // Top 2 bits.
    final int lockWordStateHash = 0x80000000;  // Top 2 bits are value 2 (kStateHash).
    final int lockWordHashMask = 0x0FFFFFFF;  // Low 28 bits.
    if ((lockWord & lockWordStateMask) == lockWordStateHash) {
        return lockWord & lockWordHashMask;
    }
    return System.identityHashCode(this);
}
```

我们在覆写equals()方法时通常也会覆写hashCode()方法，否则会违反Object.hashCode()的通用规定，从而导致该类无法与所有基于散列的集合一起正常运作，例如
HashMap、HashSet等。

这些约定包括：

- 在应用程序执行期间，只要对象的equals()方法比较的信息没有被修改，则对这个对象调用多次，hashCode()必须返回同一个整数。
- 如果两个对象根据equals()方法是相等的，那么它们的hashCode()方法也应该返回相同的整数。
- 如果两个对象根据equals()方法是不等的，那么它们的hashCode()方法也应该返回不同的整数。


```java
public class Teacher {

    private String name;
    private int age;

    @Override
    public boolean equals(Object obj) {
        //1 使用==操作符检车参数是否为这个对象引用，如果是则返回true
        if (this == obj) {
            return true;
        }
        //2 使用instanceof操作符检查对象是否为该类的实例，如果不是则返回false
        if (!(obj instanceof Teacher)) {
            return false;
        }
        //3 将对象转换为具体的类型
        Teacher teacher = (Teacher) obj;

        //4 为了提交方法性能，首先检查哪些最有可能不一致的域或者比较开销最低的域
        if (this.age != teacher.age) {
            return false;
        }
        return name == null ? teacher.name == null : this.name.equals(teacher.name);

    }

    @Override
    public int hashCode() {
        //1 将一个非0的常数值保存到一个名为result的int型变量中。
        int result = 31;
        //2 分别计算每个域的散列码并相加求和
        result = result + age + (name == null ? 0 : name.hashCode());
        return result;
    }
}

```

一般来说，我们在覆写hashCode()方法时，要为不同的对象生成不同的散列码。生成规则如下：

1 将一个非0的常数值保存到一个名为result的int型变量中。
2 分别计算每个域的散列码并相加求和，散列码的生成规则如下：

- byte、char、short、int: (int)(value)
- long: (int)(value ^ (value >>> 32))
- boolean: value == false ? 0 : 1
- float: Float.floatToIntBits(value)
- double: Double.doubleToLongBits(value)
- 引用类型：value.hashCode()

### 3 toString()

Object里toString()方法的默认实现

```java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}

```

你可以看到，默认实现中返回的是"类名@散列码"的形式，为了获取更多关于该对象的信息，我们通常以这样一种形式来覆写toString()方法

```java
@Override
public String toString() {
    return "Teacher{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
}
```
当然我们也可以根据需求的不同，将信息以json、xml等形式返回，只要我们事先在文档中做好约定。

### 4 clone()

Object里clone()方法的默认实现

```java
protected Object clone() throws CloneNotSupportedException {
    if (!(this instanceof Cloneable)) {
        throw new CloneNotSupportedException("Class " + getClass().getName() +
                                             " doesn't implement Cloneable");
    }

    return internalClone();
}

/*
 * Native helper method for cloning.
 */
private native Object internalClone();
```



我们知道要实现克隆，就需要实现Cloneable接口，该接口并没有任何方法，只是当一个类实现了Cloneable接口接口，OBject的clone()方法就会返回该类的逐域拷贝。

不过这种拷贝要分两种情况来讨论：

如果对象的每个域都包含一个基本类型的值或者包含一个指向不可变对象的引用，那么克隆的对象正是我们需要的。我们要做的就是声明实现Cloneable接口，然后将clone方法声明
为public。

如果包含的域引用了可变对象，那么就要去修正这些域

### 5 finalizer()

finalize()方法是Object里的一个方法，按照API里的说法，当GC准备清除对象引用，回收内存时调用，但是这个方法并不靠谱，Java规范并不保证该方法会被
及时地执行，甚至都不保存它会被执行，因此我们通过重写该方法来释放一些资源是一种错误的做法。

正确的做法应该是显示声明一个方法，在该方法中释放资源, 并提供一个私有域来记录资源状态，如下：

```java
public class Resource {

    private boolean isClose;

    public void open() {
        //记录状态
        isClose = false;

        //初始化资源

    }

    public void close() {
        //我们通常在finally里关闭资源，这样即使发生了异常，也能确保资源被关闭
        try {
            //其他必要的操作
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记录状态
            isClose = true;

            //关闭资源
        }
    }
}
```
既然不提倡使用finalizer()方法，那finalizer()方法存在的意义在哪里呢，它存在的意义有两点：

1 当客户端忘记显示调用上述的终止方法，finalizer()方法可以充当安全网，虽然finalizer()方法并不保证被即使执行，但是有机会释放资源，总比一点机会都没有强。

2 释放本地对等体所占有的资源，本地对等体是一个本地对象，它是由Java对象通过本地方法将自己委托给了一个本地对象，由于他不是普通的Java对象，所以无法被GC所回收。

所以如果这个本地对等体占用的是关键资源，我们依然要通过显示的方法去释放资源，但是如果它占用的不是关键资源，我们就可以用finalizer()方法来释放这些非关键资源。

finalize()方法链并不会自动的执行，因此我们要显示的调用父类的finalize()方法，如下：

```java
@Override
protected void finalize() throws Throwable {
    
    try {
        
    }
    //保证即便该对象发生异常也能正常的调用finalize()方法
    finally {
        super.finalize();
    }
}
```

但是总有些情况，由于开发者的粗心大意，并没有在子类显示的去调用finalize()方法，这个时候我们就需要把finalize()放在一个匿名类中，该匿名类的唯一用途就是终结
它的外围实例。

```java
private final Object finalizeGuardian = new Object(){
    @Override
    protected void finalize() throws Throwable {
        try {

        }
        //保证即便该对象发生异常也能正常的调用finalize()方法
        finally {
            //终结外围实例，释放资源
            super.finalize();
        }
    }
};
```

### 6 Comparable

Comparable是一个接口，它并没有在Object中声明。

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```