package com.guoxiaoxing.java.demo.Annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.guoxiaoxing.java.demo.R;

public class AnnotationActivity extends AppCompatActivity {

    private Fruit mFruit;

    private TextView mTvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);


        setupView();
        try {
            setupData();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void setupView() {
        mTvDisplay = (TextView) findViewById(R.id.tv_display);
    }

    private void setupData() throws NoSuchFieldException {
        FruitName fruitName = Fruit.class.getDeclaredField("fruitName").getAnnotation(FruitName.class);
        mTvDisplay.setText(fruitName.value());
    }
}
