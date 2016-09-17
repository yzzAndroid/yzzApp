package com.qianfeng.yyz.shoppingproject.acyivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.contants.Contants;

public class WelcomActivity extends AppCompatActivity {

    private TextView w_name;
    private ImageView w_img;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        w_img = (ImageView) findViewById(R.id.w_img);
        w_name = (TextView) findViewById(R.id.w_name);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (null!=msg){
                    if (1==msg.what){
                        startActivity(new Intent(WelcomActivity.this,MainActivity.class));
                        Toast.makeText(WelcomActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if (msg.what==2){
                        startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        };

        SharedPreferences sharedPreferences = getSharedPreferences(Contants.USERNAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name",null);
        String psw = sharedPreferences.getString("psw", null);
        if (null == name || null == psw) {
           handler.sendEmptyMessageDelayed(2,2000);
        }else {
            w_name.setText(name);
            w_img.setVisibility(View.VISIBLE);
            handler.sendEmptyMessageDelayed(1,2000);
        }
    }
}
