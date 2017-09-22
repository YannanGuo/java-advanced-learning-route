# Java编程篇：类与接口

**关于作者**

>郭孝星，程序员，吉他手，主要从事Android平台基础架构方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提issue或者发邮件至guoxiaoxingse@163.com与我交流。

### 1 类与成员的可访问性最小化

### 2 在公有类中使用方法而非公有域

### 3 使可变性最小化

>不可变类指的是其实例不能被修改的类，每个实例包含的信息必须在创建的时候提供，并在对象的整个生命周期类固定不变。不可变对象在本质

- 不要提供任何修改对象状态的方法
- 保证类不会被扩展
- 使所有域都是final的
- 使所有域都是private的
- 确保对任何可变组件的互斥访问
             
### 4 复合优先于继承

>当我们需要去扩展类的功能时，继承可能会破坏类的封装性，为了实现功能扩展，复合优先于继承。

例如，我们有个接口（当然它也可以是个抽象类），它实现了穿衣服的功能。

```java
public interface IDress {

    /**
     * 穿衣服
     */
    void dressClothes();
}
```

它有个实现类Boy，实现了基本的穿衣服的功能。

```java
public final class Boy implements IDress {

    @Override
    public void dressClothes() {
        //穿了简单的服装
    }
}
```
如果我们想扩展这个类的功能，不是选择直接继承它，而是在外包装层，内部持有这个类的引用。

```java
public class FashionBoy implements IDress {

    private IDress iDress;

    public FashionBoy(IDress iDress) {
        this.iDress = iDress;
    }

    @Override
    public void dressClothes() {
        iDress.dressClothes();
        DressLeather();
        DressJean();
    }

    public void DressLeather() {
        System.out.print("穿件皮衣");
    }

    public void DressJean() {
        System.out.print("穿件牛仔裤");
    }
}
```

### 要么专为继承而设计，要么禁止继承

>继承打破了类的封装性，子类在覆盖父类的方法时，可以会破坏父类内部的功能。

### 接口优于抽象类

>接口相对于抽象类，可以避免继承带来的麻烦，使得安全的增强类的功能称为可能。

通常来说，我们在使用接口定义一列功能时，会先定一个Interface，再定义一个它的骨架实现AbstractInterface，它提供了一些关于接口实现的指导
和默认实现。

例如在集合框架中，每个接口都有个骨架实现，List对应AbstractList。

```java
private List<Integer> createArrayList(){
    
    return new AbstractList<Integer>() {
        @Override
        public Integer get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    };
}
```
我们再来比较下接口与抽象类的优缺点

接口

优点：

1. 安全的扩展类的功能，

缺点：

1. 接口一旦公开发布，并且已经广泛实现，就很难再去更改这个接口。


抽象类

优点：

1. 抽象类比接口更加灵活，为抽象类增加一个功能，它的子类都可以使用这个功能，相应的，接口一旦公开发布，并且已经广泛实现，就很难再去更改这个接口。
几乎不可能，

缺点：

1. 多层次继承会带来代码的臃肿。