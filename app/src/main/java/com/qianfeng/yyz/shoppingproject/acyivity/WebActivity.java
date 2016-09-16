package com.qianfeng.yyz.shoppingproject.acyivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.fragment.MyCollectFragment;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;
import com.qianfeng.yyz.shoppingproject.utils.Utils;
import com.squareup.picasso.Picasso;

public class WebActivity extends AppCompatActivity {

    private  int count = 1;
    private CheckBox cb_collect;
    private TextView tv_car;
    private TextView tv_buy;
    private TextView web_num;
    private WebView webView;
    private String pUrl;
    private String sweb_name;
    private String sweb_price;
    private  DisplayMetrics metrics;
    private Collects collects;
    private Toolbar toolbar;
    private TextView text_car_top;
    private ProgressBar progressBar;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Utils.setBarColor(this);
        //获取屏幕的宽度
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        webView = (WebView) findViewById(R.id.web);
        //查找
        tv_buy = (TextView) findViewById(R.id.web_bottom_buy);
        tv_car = (TextView) findViewById(R.id.web_bottom_shopping_car);
        cb_collect = (CheckBox) findViewById(R.id.web_bottom_collect);
        text_car_top = (TextView) findViewById(R.id.web_bootom_car_top);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        pUrl = intent.getStringExtra("pUrl");
        sweb_name = intent.getStringExtra("name");
        sweb_price = intent.getStringExtra("price");
        webView.loadUrl(path);
        collects = new Collects(((int)(count*Double.valueOf(sweb_price)*100))/100.00,sweb_name,count,path,Double.valueOf(sweb_price),pUrl);
        //数据库
        int count = SaveCollects.getInstance(this, Contants.TB_NAME).querry(collects).getCount();
        if (count>0){
            cb_collect.setEnabled(false);
            cb_collect.setChecked(true);
        }
        //必须关闭
        SaveCollects.getInstance(this,Contants.TB_NAME).close();

        //ActionBar(toolbar)
        toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        toolbar.setTitle("商品详情");
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);

        //出事化小圆点
        initCarTop(true);
        //加进度条
        progressBar = (ProgressBar) findViewById(R.id.web_progress);
        progressBar.setMax(100);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
               progressBar.setProgress(newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
       finish();
    }


    public void webCollect(View view){
        cb_collect.setEnabled(false);
        //保存
        Log.e("==collects==","==="+collects.getName());
        SaveCollects.getInstance(this,Contants.TB_NAME).loade(collects);
        Toast.makeText(WebActivity.this, "添加收藏成功", Toast.LENGTH_SHORT).show();

    }
    public void webShoppingCar(View view){

        dialogForShoppingCar();
    }
    public void webBuy(View view){
        Log.e("===","===="+"点击了购物车");
    }

    public void dialogForShoppingCar(){
        Log.e("===","===="+"点击了购物车");
        AlertDialog dialog ;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.add_item,null);
        builder.setView(v);
        builder.setCancelable(false);
        dialog = builder.create();
        WindowManager.LayoutParams lp= dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.Them);
        dialog.getWindow().getDecorView().setPadding(0,0,0,0);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setWindowAnimations(R.style.dialog);
        dialog.show();




        web_num= (TextView) v.findViewById(R.id.web_tv_one);
        web_num.setText(String.valueOf(count));
        Button btn_add = (Button) v.findViewById(R.id.web_add);
        Button btn_sub = (Button) v.findViewById(R.id.web_sub);
        Button buttonsure = (Button) v.findViewById(R.id.web_car_sure);
        ImageView web_image = (ImageView) v.findViewById(R.id.web_image);
        TextView web_name = (TextView) v.findViewById(R.id.web_name);
        TextView web_price = (TextView) v.findViewById(R.id.web_price);
        Picasso.with(this).load(pUrl).into(web_image);
        web_name.setText(sweb_name);

        web_price.setText("价格："+sweb_price);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==10){
                    return;
                }
                count++;
                web_num.setText(String.valueOf(count));
                Log.e("===","==="+count);
            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    Log.e("===","====");
                    return;
                }
                count--;
                web_num.setText(String.valueOf(count));
            }
        });

        final AlertDialog finalDialog = dialog;
        buttonsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count>0){
                    collects = new Collects(((int)(count*Double.valueOf(sweb_price))*100)/100.00,sweb_name,count,path,Double.valueOf(sweb_price),pUrl);
                    SaveCollects.getInstance(WebActivity.this,Contants.TB_NAME_CAR).loade(collects);
                    Toast.makeText(WebActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                finalDialog.dismiss();
                initCarTop(false);
            }
        });

        v.findViewById(R.id.wen_care_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebActivity.this, "取消", Toast.LENGTH_SHORT).show();
                finalDialog.dismiss();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭流
        Log.e("===","==关闭=");
        SaveCollects.getInstance(WebActivity.this,Contants.TB_NAME).close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initCarTop(boolean first){
        if (!first){
            if (count==0){
                return;
            }
        }
        int num = SaveCollects.getInstance(this,Contants.TB_NAME_CAR).querry().getCount();
        if (num<=0){
            text_car_top.setVisibility(View.GONE);
        }else {
            text_car_top.setVisibility(View.VISIBLE);
            text_car_top.setText(String.valueOf(num));
        }
    }
}
