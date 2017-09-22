# Java编程篇：创建与销毁对象

**关于作者**

>郭孝星，程序员，吉他手，主要从事Android平台基础架构方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提issue或者发邮件至guoxiaoxingse@163.com与我交流。

## 集合框架概述

>Java里的集合使用Collection接口来描述，Collection接口继承于Iterable接口，它表示一个独立的元素序列，这些元素服从一条或者多条规则。

Iterable接口

```java
public interface Iterable<T> {
    
    Iterator<T> iterator();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }

    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
```
Collection接口

```java
public interface Collection<E> extends Iterable<E> {

    //返回集合大小
    int size();

    //集合是否为空
    boolean isEmpty();
    
    //是否包含对象
    boolean contains(Object o);

    //返回迭代器
    Iterator<E> iterator();

    //返回一个数组，该数组包含集合中的所有元素
    Object[] toArray();

    //返回一个数组，该数组包含集合中的所有元素，返回指定的参数类型
    <T> T[] toArray(T[] a);

    //添加元素
    boolean add(E e);

    //移除元素
    boolean remove(Object o);

    //是否包含指定集合里的所有元素
    boolean containsAll(Collection<?> c);

    //添加指定集合里的所有元素
    boolean addAll(Collection<? extends E> c);

    //移除指定集合包含的所有元素。基于equals()方法
    boolean removeAll(Collection<?> c);

    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    //取得两个集合的交集，基于equals()方法
    boolean retainAll(Collection<?> c);

    //清空集合里的元素
    void clear();

    boolean equals(Object o);

    int hashCode();

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
```
迭代器用来迭代并返回集合里的元素，它屏蔽了集合的具体实现细节。Java里额Iterator是单向易懂的，它的接口如下：

```java
public interface Iterator<E> {
    //是否还有下一个元素
    boolean hasNext();

    //f返回下一个元素
    E next();

    //删除next()最近返回的元素，所以在调用remove()方法之前需要先调用next()方法
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
```

另外，关于List类还额外提供了ListIterator接口，它可以双向移动，它的接口实现如下：

```java
public interface ListIterator<E> extends Iterator<E> {
    //是否还有下一个元素
    boolean hasNext();
    
    //返回下一个元素
    E next();

    //是否还有上一个元素
    boolean hasPrevious();

    //返回上一个元素
    E previous();

    //迭代器所处当前位置的下一个索引
    int nextIndex();

    //迭代器所处当前位置上一个索引
    int previousIndex();

    //移除当前next()或previous()产生的元素
    void remove();

    //替换当前next()或previous()产生的元素
    void set(E e);
    
    //在当前next()或previous()产生元素的下一个位置添加新元素
    void add(E e);
}

```

Java集合框架主要包含4大接口：

- Set：定义一个无序的集合，它保证不能有重复的元素。
- List：定义一个有序的集合，它保证按照插入的顺序来保存元素。
- Map：定义一个映射关系的集合，它允许你使用键来查找值。
- Queue：基于一个队列的结合，它保证按照排队规则来确定对象产生的顺序。

我们来具体说一说每个接口的优缺点与具体实现。

## Set应用场景与实现原理

优点：插入与删除效率高，因为不会引起元素位置改变。

缺点：检索效率低下

应用场景：

HashSet

```
以哈希表的形式存放元素，为快速查找而设计的Set。
```

TreeSet

```
实现了Set接口，可以实现排序等功能。
```

LinkedHashSet

```
具有可预知迭代顺序的Set接口的哈希表与链表实现
```

## List应用场景与实现原理

优点：可以动态增长，查找元素效率高。

缺点：插入与删除效率低，因为会引起元素位置改变。

>List接口继承于Collection，它的实现类是AbstractList。

List接口在Collection接口的基础上新增了以下操作：

```java
public interface List<E> extends Collection<E> {
    
    ...

    E get(int index);
    
    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(Object o);

    int lastIndexOf(Object o);

    //获取ListIterator
    ListIterator<E> listIterator();

    //获取指定index的ListIterator
    ListIterator<E> listIterator(int index);

    //获取子List
    List<E> subList(int fromIndex, int toIndex);

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }

    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }
    
    //根据指定的Comparator进行排序操作
    default void sort(Comparator<? super E> c) {
        Collections.sort(this, c);
    }
}

```

### ArrayList

>ArrayList擅长于随机访问元素，但在中间插入与移除元素时较慢。

### LinkedList

>LinkedList擅长于在中间插入与删除元素，但是随机访问速度较慢。实现了List接口，允许null元素，提供额外的get、remove、insert方法在LinkedList的首部与尾部，这些操作使得LinkedList可以被
用作堆栈（Stack）、队列（Queue）或者双向队列（Deque）。


Vector

Stack：实现了后进先出的集合类

## Map应用场景与实现原理

HashMap

```
基于哈希表的Map接口实现，该实现提供所有可选的映射操作，并允许使用null值与null键。
```

WeakHashMao

```
继承与AbstractMap，使用弱密钥的哈希表。
```


LinkedHashMap

```
继承与AbstractMap，使用元素的自然顺序对元素进行排序
```

TreeMap

```
基于红黑树的SortedMap接口的实现，该实现保值映射按照升序顺序排列关键字，根据使用方法的不同，可能会按照键的类的自然顺序进行排序，或者按照
创建时提供的比较器进行排序。

```
Hashtable

```
该类实现一个哈希表，该哈希表将键映射到相应的值，任何非null的对象都可以用作键或值。
```

另外，除了Vector、Stack、Hashtable、Enumeration是线程安全的，其他都是非线程安全的，线程安全的类方法都是同步的，每次只能有一个线程方法，是重量级
对象，效率低下。对于非线程安全的类与接口，在多线程中我们自己要去处理线程安全问题。

## Queue应用场景与实现原理

```java
public interface Queue<E> extends Collection<E> {
    
    //插入元素
    boolean add(E e);

    //插入元素
    boolean offer(E e);

    //检索并删除队列头部元素，如果队列为空则抛出NoSuchElementException
    E remove();

    //检索并删除队列头部元素，如果队列为空则返回null
    E poll();

    //检索队列头部元素，如果队列为空则抛出NoSuchElementException
    E element();
    
    //检索队列头部元素，如果队列为空则返回null
    E peek();
}

```