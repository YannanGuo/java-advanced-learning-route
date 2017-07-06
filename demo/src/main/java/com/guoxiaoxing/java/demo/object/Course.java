package com.guoxiaoxing.java.demo.object;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/7/6 下午3:40
 */
public class Course {

    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return courseName != null ? courseName.equals(course.courseName) : course.courseName == null;
    }

    @Override
    public int hashCode() {
        return courseName != null ? courseName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                '}';
    }

    @Override
    protected Course clone() throws CloneNotSupportedException {
        return (Course) super.clone();
    }
}
