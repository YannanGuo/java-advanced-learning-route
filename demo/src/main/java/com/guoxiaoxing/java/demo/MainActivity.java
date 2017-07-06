package com.guoxiaoxing.java.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guoxiaoxing.java.demo.annotation.AnnotationActivity;
import com.guoxiaoxing.java.demo.object.ObjectActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_annotation).setOnClickListener(this);
        findViewById(R.id.btn_object).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_annotation:
                startActivity(new Intent(MainActivity.this, AnnotationActivity.class));
                break;
            case R.id.btn_object:
                startActivity(new Intent(MainActivity.this, ObjectActivity.class));
                break;
        }
    }
}
