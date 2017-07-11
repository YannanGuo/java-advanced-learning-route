package com.guoxiaoxing.java.demo.concurrence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guoxiaoxing.java.demo.R;

public class ConcurrenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concurrence);

        Thread thread1 = new Thread(new Worker("thread1"));
        thread1.start();

        Thread thread2 = new Thread(new Worker("thread2"));
        thread2.start();
    }
}
