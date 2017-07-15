package com.guoxiaoxing.java.demo.jvm.gc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.guoxiaoxing.java.demo.App;
import com.guoxiaoxing.java.demo.R;

public class GCActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gc);

        findViewById(R.id.btn_gc_mutual_refrence).setOnClickListener(this);
        findViewById(R.id.btn_gc_save_selft).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gc_mutual_refrence:
                triggerMutualReference();
                break;
            case R.id.btn_gc_save_selft:
                saveSelf();
                break;
        }
    }

    private void triggerMutualReference(){

        GCObjectA gcObjectA = new GCObjectA();
        GCObjectB gcObjectB = new GCObjectB();

        gcObjectA.gcObjectB = gcObjectB;
        gcObjectB.gcObjectA = gcObjectA;

        gcObjectA = null;
        gcObjectB = null;

        System.gc();
    }

    private void saveSelf(){
        SaveSelfObject.SAVE_SELF = new SaveSelfObject();

        //第一次执行
        SaveSelfObject.SAVE_SELF = null;
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(SaveSelfObject.SAVE_SELF == null){
            Log.d(App.TAG, "The SAVE_SELF is dead");
        }else {
            Log.d(App.TAG, "The SAVE_SELF is alive");
        }

        //第二次知悉
        SaveSelfObject.SAVE_SELF = null;
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(SaveSelfObject.SAVE_SELF == null){
            Log.d(App.TAG, "The SAVE_SELF is dead");
        }else {
            Log.d(App.TAG, "The SAVE_SELF is alive");
        }
    }
}
