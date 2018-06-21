package com.example.yuyu.wlbc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowActivity extends AppCompatActivity {

    private ExecutorService mThreadPool ;
    InputStream ins;
    OutputStream ous;
    String result=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        final Bundle bundle=this.getIntent().getExtras();
        final String subject=bundle.getString("subject");
        final String userId=bundle.getString("userId");
        final String deviceId=bundle.getString("deviceId");
        this.setTitle(subject+userId+deviceId);


        // 利用线程池直接开启一个线程 & 执行该线程
        Thread a=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                        Socket socket=new Socket("120.79.237.210",5555);

                        OutputStream ous = socket.getOutputStream();
                        //String str="type:askForData\r\ndeviceId:123\r\nuserId:1\r\npage_number:1";
                        String str="type:askForData\r\n"+"deviceId:"+deviceId+"\r\n"+"userId:"+userId+"\r\n"+"page_number:1";
                        ous.write(str.getBytes());
                        ous.flush();
                        ins=socket.getInputStream();
                        byte[] buf = new byte[2048];
                        DataInputStream dins = new DataInputStream(ins);
                        int count = dins.read(buf);
                        result= new String(buf,0,count);
//                    String result2=new String();
 //                   result2=result;
//                    for(int i=result2.length()-2;i>=0;i--){
//                        if(result2.charAt(i)=='}'){
//                            String a = result.substring(i+1, result.length());
//                            a=','+a;
//                            result=result.substring(0, i+1)+a;
//                        }
//                    }
//                    result='['+result+']';
                        Log.e("Json", result);


//                           JSONObject jsonObject1 = new JSONObject(result);

//                            JSONArray jsonArray = new JSONArray(result);

//                           for (int i = 0; i < jsonArray.length(); i++) {
//                               JSONObject jsonObject = jsonArray.getJSONObject(i);
//                               //取出name
//                               int a=jsonObject.getInt("dataId");
//                               datavalue= String.valueOf(a);
//                               System.out.println(datavalue);
//                           }









                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        a.start();
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> data = new ArrayList<String>(){{add(result);}};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowActivity.this, android.R.layout.simple_list_item_1, data);
        final ListView listView =  findViewById(R.id.show);
        listView.setAdapter(adapter);


    }
}
