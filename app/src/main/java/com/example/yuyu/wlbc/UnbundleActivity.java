package com.example.yuyu.wlbc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UnbundleActivity extends AppCompatActivity {
    //private String[] data = { "Apple", "Banana", "Orange", "Watermelon"};
    private ArrayList<String> data = new ArrayList<String>(){{add("123");add("456");}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbundle);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnbundleActivity.this, android.R.layout.simple_list_item_1, data);
        final ListView listView = (ListView) findViewById(R.id.unbundle);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    data.remove((int)id);//我们需要的内容，跳转页面或显示详细信息
                    adapter.notifyDataSetChanged();
                }
            }

        );
    }
}
