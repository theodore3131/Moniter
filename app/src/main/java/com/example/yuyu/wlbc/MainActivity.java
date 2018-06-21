package com.example.yuyu.wlbc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Runnable{
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    private boolean[] checkItems;
    private View view_custom;
    DataInputStream dins;

    ArrayAdapter<String> adapter;
    ListView listView;
    InputStream ins;
    final private int RED = 110;
    final private int GREEN = 111;
    private ArrayList<String> data = new ArrayList<String>(){{add("血氧");add("温度");add("PM2.5");}};
    String result =new String();
    User user;
    ArrayList<JSONObject> jsonObjects=new ArrayList<JSONObject>();
    ArrayList<JSONObject> jsonObjects2=new ArrayList<JSONObject>();
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                adapter.notifyDataSetChanged();
                jsonObjects.clear();
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = (User)getIntent().getSerializableExtra("user");
        run3();

        Thread a=new Thread(new Runnable(){
            @Override
            public void run() {

                    try {

                        // 创建Socket对象 & 指定服务端的IP 及 端
                         ins=MySocket.getIns();
                         dins = new DataInputStream(ins);
                        new Thread(MainActivity.this).start();

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




        builder = new AlertDialog.Builder(mContext);

        final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.activity_dialog, null,false);

        builder.setView(view_custom);
        builder.setCancelable(false);
        alert = builder.create();


//        view_custom.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//            }
//        });

        view_custom.findViewById(R.id.btn_blog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=view_custom.findViewById(R.id.dialog);
                final String query = "type:bind\r\nusername:"+user.getUsername()+"\r\ndeviceId:"+editText.getText().toString();
                Thread a = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Simulate network access.
                            Socket socket = new Socket("120.79.237.210",5555);
                            InputStream ins = socket.getInputStream();
                            OutputStream ous = socket.getOutputStream();


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

                Toast.makeText(getApplicationContext(), "绑定成功"+editText.getText().toString(), Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        view_custom.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "对话框已关闭~", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
                //显示对话框
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1,RED,4,"注销");
        menu.add(1,GREEN,2,"解绑");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case RED:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MySocket.close22();
                startActivity(intent);
                finish();
                break;
            case GREEN:
                Intent intent2 = new Intent(MainActivity.this, UnbundleActivity.class);
                intent2.putExtra("user",user);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {

        while (true){

            if(MySocket.getConnected()){
                System.out.print("5555");
                if(!MySocket.isOutputDown()){
                    byte[] buf = new byte[2048];

                    int count = 0;
                    try {
                        count = dins.read(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    result= new String(buf,0,count);

                    Log.e("Json", result);
                    if(!result.contains("name")) {
                        System.out.println("name");
                        try {

                            int j = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == '}') {
                                    JSONObject jsonObject1 = new JSONObject(result.substring(j, i + 1));
                                    jsonObjects.add(jsonObject1);
                                    if (i != result.length() - 1) {
                                        j = i + 1;
                                    }
                                }
//                               JSONObject jsonObject = jsonArray.getJSONObject(i);
//                               //取出name
//                               int a=jsonObject.getInt("dataId");
//                               datavalue= String.valueOf(a);
//                               System.out.println(datavalue);
                            }

                        } catch (Exception e) {
                            System.out.println("Pppppppppppppppppppppp");
                        }
                    }
                    run2();
                }

            }

        }
    }

    public void run2(){
        System.out.println(jsonObjects.size());
        if(jsonObjects.size()>0){
            String string4="false";
            for(int i=0;i<jsonObjects.size();i++){

                try {
                    if(jsonObjects.get(i).getString("deviceType").equals("3")){
                        data.add(2,"PM2.5："+jsonObjects.get(i).getString("dataValue")
                                +"\n设备id："+jsonObjects.get(i).getString("deviceId"));
                        if(jsonObjects.get(i).getString("warn").equals("true")){
                            string4="true";
                        }

                        data.remove(3);
                    }
                    if(jsonObjects.get(i).getString("deviceType").equals("1")){
                        data.add(0,"血氧："+jsonObjects.get(i).getString("dataValue")+"\n设备id："+jsonObjects.get(i).getString("deviceId"));
                        data.remove(1);
                        if(jsonObjects.get(i).getString("warn").equals("true")){
                            string4="true";
                        }
                    }
                    if(jsonObjects.get(i).getString("deviceType").equals("2")){
                        data.add(1,"温度："+jsonObjects.get(i).getString("dataValue")+"\n设备id："+jsonObjects.get(i).getString("deviceId"));
                        data.remove(2);
                        if(jsonObjects.get(i).getString("warn").equals("true")){
                            string4="true";
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("cnmmmm");
                }
            }
            if(string4.equals("true")){
                Vibrator vibrator = (Vibrator) getSystemService(mContext.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);

            }
        }
        jsonObjects2=new ArrayList<JSONObject>(jsonObjects);
        handler.sendEmptyMessage(0x123);

    }
    public void run3(){
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.main);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String string,string2="",string3="";

                                                if(id==0&&!data.get(0).equals("血氧")){
                                                    string="血氧";
                                                    for(int i=0;i<jsonObjects2.size();i++){
                                                        try {
                                                            if(jsonObjects2.get(i).getString("deviceType").equals("1")){
                                                                string2=jsonObjects2.get(i).getString("userId");
                                                                string3=jsonObjects2.get(i).getString("deviceId");

                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("subject", string);
                                                    bundle.putString("userId",string2);
                                                    bundle.putString("deviceId",string3);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                                else if(id==1&&!data.get(1).equals("温度")){
                                                    string="温度";
                                                    for(int i=0;i<jsonObjects2.size();i++){
                                                        try {
                                                            if(jsonObjects2.get(i).getString("deviceType").equals("2")){
                                                                string2=jsonObjects2.get(i).getString("userId");
                                                                string3=jsonObjects2.get(i).getString("deviceId");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("subject", string);
                                                    bundle.putString("userId",string2);
                                                    bundle.putString("deviceId",string3);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }
                                                else if(id==2&&!data.get(2).equals("PM2.5")){
                                                    string="PM2.5";
                                                    for(int i=0;i<jsonObjects2.size();i++){
                                                        try {
                                                            if(jsonObjects2.get(i).getString("deviceType").equals("3")){
                                                                string2=jsonObjects2.get(i).getString("userId");
                                                                string3=jsonObjects2.get(i).getString("deviceId");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("subject", string);
                                                    bundle.putString("userId",string2);
                                                    bundle.putString("deviceId",string3);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                }

                                            }
                                        }

        );
    }
}
