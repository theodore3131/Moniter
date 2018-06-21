package com.example.yuyu.wlbc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UnbundleActivity extends AppCompatActivity {
    //private String[] data = { "Apple", "Banana", "Orange", "Watermelon"};
    private ArrayList<String> data = new ArrayList<String>(){{add("123");add("456");}};
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbundle);
        user = (User)getIntent().getSerializableExtra("user");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnbundleActivity.this, android.R.layout.simple_list_item_1, data);
        final ListView listView = (ListView) findViewById(R.id.unbundle);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                    Thread a = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // Simulate network access.
                                Socket socket = new Socket("120.79.237.210",5555);
                                InputStream ins = socket.getInputStream();
                                OutputStream ous = socket.getOutputStream();
                                System.out.println(user.getUsername());
                                String str = "type:unbind\r\nusername:"+user.getUsername()+"\r\ndeviceId:"+data.get((int)id);
                                ous.write(str.getBytes());
                                ous.flush();

                                byte[] buf = new byte[2048];
//
                                DataInputStream dins = new DataInputStream(ins);
                                int count = dins.read(buf);

                                String result = new String(buf,0,count);
                                System.out.println(result);

                            } catch (IOException e) {

                            }
                        }
                    });
                    a.start();

                    try {
                        a.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data.remove((int)id);//我们需要的内容，跳转页面或显示详细信息
                    adapter.notifyDataSetChanged();
                }
            }

        );
    }
}
