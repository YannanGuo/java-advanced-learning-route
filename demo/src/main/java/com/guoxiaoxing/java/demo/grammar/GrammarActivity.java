package com.guoxiaoxing.java.demo.grammar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guoxiaoxing.java.demo.R;

public class GrammarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GrammarActivity";

    private TextView mTvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

        findViewById(R.id.btn_break).setOnClickListener(this);
        findViewById(R.id.btn_continue).setOnClickListener(this);
        findViewById(R.id.btn_return).setOnClickListener(this);

        mTvLog = (TextView) findViewById(R.id.tv_log);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_break:
                break;
            case R.id.btn_continue:
                break;
            case R.id.btn_return:
                break;
        }
    }

    private void playBreak() {

        StringBuilder log = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                break;
            }
            log.append(i);
        }

        mTvLog.setText(log.toString());
    }

    private void playCoutinue() {

        StringBuilder log = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue;
            }
            log.append(i);
        }

        mTvLog.setText(log.toString());
    }

    private void playReturn() {

        StringBuilder log = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue;
            }
            log.append(i);
        }

        mTvLog.setText(log.toString());
    }
}
