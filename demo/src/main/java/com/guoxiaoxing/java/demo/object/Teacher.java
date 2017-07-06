package com.guoxiaoxing.java.demo.object;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/5 下午3:56
 */
public class Teacher implements Cloneable {

    private String name;
    private int age;
    private Course course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

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
        if (!(name == null ? teacher.name == null : this.name.equals(teacher.name))) {
            return false;
        }
        return course == null ? teacher.course == null : course.equals(teacher.course);
    }

    @Override
    public int hashCode() {
        //1 将一个非0的常数值保存到一个名为result的int型变量中。
        int result = 31;
        //2 分别计算每个域的散列码并相加求和
        result = result + age + (name == null ? 0 : name.hashCode());
        result = result + (course == null ? 0 : course.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", course=" + course +
                '}';
    }

//    @Override
//    protected Teacher clone() throws CloneNotSupportedException {
//        return (Teacher) super.clone();
//    }


    @Override
    protected Teacher clone() throws CloneNotSupportedException {
        return (Teacher) super.clone();
    }
}
