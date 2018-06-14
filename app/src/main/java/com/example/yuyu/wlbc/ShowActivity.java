package com.example.yuyu.wlbc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        final Bundle bundle=this.getIntent().getExtras();
        final String subject=bundle.getString("subject");
        this.setTitle(subject);
    }
}
