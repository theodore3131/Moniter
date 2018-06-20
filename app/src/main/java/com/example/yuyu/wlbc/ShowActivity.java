package com.example.yuyu.wlbc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowActivity extends AppCompatActivity {

    private ExecutorService mThreadPool ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        final Bundle bundle=this.getIntent().getExtras();
        final String subject=bundle.getString("subject");
        this.setTitle(subject);
        mThreadPool = Executors.newCachedThreadPool();
        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                    ObjectOutputStream oos = MySocket.getOos();
//                    String str = "read"+' '+bundle.getInt("mail_id");
                    String str="type: askForData\r\n"+"data_type: 1\r\n"+"page_number: 1";
                    oos.writeObject(str);

                    ObjectInputStream ois=MySocket.getOis();
                    try{
                       String json=(String) ois.readObject();
                       try {
                           JSONObject jsonObject1 = new JSONObject(json);
                           Log.e("Json", json);

                       }catch (Exception e) {

                       }


                    }catch (ClassNotFoundException e){

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
