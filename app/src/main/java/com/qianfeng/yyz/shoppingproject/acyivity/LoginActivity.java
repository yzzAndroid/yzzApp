package com.qianfeng.yyz.shoppingproject.acyivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.contants.Contants;

import java.security.PrivateKey;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private EditText login_name;
    private EditText login_psw;
    private SharedPreferences sharedPreferences;
    private SharedPreferences time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_name = (EditText) findViewById(R.id.login_name);
        login_psw = (EditText) findViewById(R.id.login_psw);

        sharedPreferences = getSharedPreferences(Contants.USERNAME,MODE_PRIVATE);
        time = getSharedPreferences("time", MODE_PRIVATE);

    }

    public void cancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("取消注册");
        builder.setMessage("您确定取消注册吗？");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
        builder.setNegativeButton("确定",null);

        builder.setNeutralButton("继续注册",null);
        AlertDialog dialog = builder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 300;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void sure(View view){
        String name = login_name.getText().toString();
        String psw = login_psw.getText().toString();
        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(psw)){
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (psw.length()<6){
            Toast.makeText(LoginActivity.this, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("psw",psw);
        editor.commit();
        time.edit().putLong(name,System.currentTimeMillis()).
                putBoolean("state",true).commit();

        Log.e("=======","======="+sharedPreferences.getString("name",""));
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences = null;
        time = null;
    }
}
