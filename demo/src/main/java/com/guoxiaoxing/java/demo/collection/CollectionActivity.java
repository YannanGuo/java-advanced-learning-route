package com.guoxiaoxing.java.demo.collection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.guoxiaoxing.java.demo.App;
import com.guoxiaoxing.java.demo.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        findViewById(R.id.btn_collection_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_collection_list:
                printList();
                break;
        }
    }

    private void printMap() {

    }

    private void printList() {

        List<String> arrayList1 = new ArrayList<>();
        arrayList1.add("a");
        arrayList1.add("b");
        arrayList1.add("c");
        arrayList1.add("d");
        arrayList1.add("e");
        Iterator<String> iterator = arrayList1.iterator();

        Log.d(App.TAG, "------iterator------");
        while (iterator.hasNext()) {
            String result = iterator.next();
            if (TextUtils.equals(result, "c")) {
                iterator.remove();
            }
        }

        for (String str : arrayList1) {
            Log.d(App.TAG, str);
        }


        List<String> arrayList2 = new ArrayList<>();
        arrayList2.add("a");
        arrayList2.add("b");
        arrayList2.add("c");
        arrayList2.add("d");
        arrayList2.add("e");
        for (ListIterator<String> listIterator = arrayList2.listIterator(); listIterator.hasNext(); ) {
            String result = listIterator.next();
            if (TextUtils.equals(result, "c")) {
                listIterator.add("f");
            }
        }

        for (String str : arrayList2) {
            Log.d(App.TAG, str);
        }
    }
}
