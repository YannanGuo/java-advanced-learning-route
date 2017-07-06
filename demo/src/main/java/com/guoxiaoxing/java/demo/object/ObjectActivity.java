package com.guoxiaoxing.java.demo.object;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guoxiaoxing.java.demo.R;
import com.orhanobut.logger.Logger;

public class ObjectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        findViewById(R.id.btn_object_clone).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_object_clone:
                cloneObject();
                break;
        }
    }

    private void cloneObject() {
        Teacher teacher1 = new Teacher();
        teacher1.setAge(20);
        teacher1.setName("LiLei");
        Course course = new Course();
        course.setCourseName("Math");
        teacher1.setCourse(course);

        Teacher teacher2 = teacher1;

        Teacher teacher3 = null;
        try {
            teacher3 = teacher1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        teacher2.setAge(30);
        teacher3.setAge(40);

        Logger.d("modify");
        Logger.d("teacher1: " + teacher1.toString() + "/" + teacher1.hashCode());
        Logger.d("teacher2: " + teacher2.toString() + "/" + teacher2.hashCode());
        Logger.d("teacher3: " + teacher3.toString() + "/" + teacher3.hashCode());


        Stack stack1 = new Stack();
        stack1.push(1);
        stack1.push("Lilei");

        Stack stack2 = stack1.clone();
        Logger.d("stack1: " + stack1);
        Logger.d("stack2: " + stack2);
    }
}
