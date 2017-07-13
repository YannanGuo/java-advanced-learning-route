package com.guoxiaoxing.java.demo.jvm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.guoxiaoxing.java.demo.R;

import java.util.ArrayList;
import java.util.List;

public class JvmActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jvm);

        findViewById(R.id.btn_jvm_vm_stack_oom).setOnClickListener(this);
        findViewById(R.id.btn_jvm_vm_stack_sof).setOnClickListener(this);
        findViewById(R.id.btn_jvm_method_area_oom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jvm_vm_stack_oom:
                triggerVMStackOOM();
                break;
            case R.id.btn_jvm_vm_stack_sof:
                triggerVMStackSOF();
                break;
            case R.id.btn_jvm_method_area_oom:
                triggerMethodAreaOOM1();
                break;
        }
    }

    private void triggerVMStackOOM() {
        List<OutOfMemoryObject> list = new ArrayList<>();
        while (true) {
            list.add(new OutOfMemoryObject());
        }
    }

    private void triggerVMStackSOF(){
        StackOverflowObject stackOverflowObject = new StackOverflowObject();
        stackOverflowObject.stackLeak();
    }

    private void triggerMethodAreaOOM1(){
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true){
            //intern()方法，如果常量池已经包含一个等于String对象的字符串，则返回常量池中代表这个字符串的String对象，
            //否则将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。
            list.add(String.valueOf(i++).intern());
        }
    }

    private void triggerMethodAreaOOM2(){
        while (true){

        }
    }

}
