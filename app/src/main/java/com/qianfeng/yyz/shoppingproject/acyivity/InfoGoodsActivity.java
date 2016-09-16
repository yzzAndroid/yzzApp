package com.qianfeng.yyz.shoppingproject.acyivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.adapter.MyInfoAdapter;
import com.qianfeng.yyz.shoppingproject.bean.InfoGoods;
import com.qianfeng.yyz.shoppingproject.callback.InfoTextCallBack;
import com.qianfeng.yyz.shoppingproject.task.MyInfoTask;
import com.qianfeng.yyz.shoppingproject.utils.Utils;

public class InfoGoodsActivity extends AppCompatActivity {

    MyInfoAdapter myInfoAdapter ;
    InfoTextCallBack infoTextCallBack;
    InfoGoods infoGoods;
    GridView gridView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_goods);

        Utils.setBarColor(this);
        //配置Toolbar
        toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        toolbar.setTitle("列表详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);

        infoGoods = new InfoGoods();
        //初始化适配器
        myInfoAdapter = new MyInfoAdapter(this);
        myInfoAdapter.setInfoGoods(infoGoods);

        gridView = (GridView) findViewById(R.id.gv_info_goods);
        gridView.setAdapter(myInfoAdapter);

        //获取意图
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String name = intent.getStringExtra("name");
        Log.e("==","=2="+path);

        infoTextCallBack = new InfoTextCallBack() {
            @Override
            public void setText(InfoGoods infoGoods) {

                InfoGoodsActivity.this.infoGoods = infoGoods;
                myInfoAdapter.setInfoGoods(infoGoods);
                myInfoAdapter.notifyDataSetChanged();
            }
        };
        MyInfoTask myInfoTask = new MyInfoTask(infoTextCallBack);
        myInfoTask.execute(path,name);
        //设置GrideView的监听
        openGoods();
    }



    public void openGoods(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                StringBuffer  sb = new StringBuffer();
                sb.append("http://m.hichao.com/lib/interface.php?m=goodsdetail&sid=");
                String idName = infoGoods.getData().getItems().get(position).getComponent().getAction().getSourceId();
                intent.putExtra("path",sb.append(idName).toString());
                intent.putExtra("pUrl",infoGoods.getData().getItems().get(position).getComponent().getPicUrl());
                intent.putExtra("price",infoGoods.getData().getItems().get(position).getComponent().getPrice());
                intent.putExtra("name",infoGoods.getData().getItems().get(position).getComponent().getDescription());
                intent.setClass(InfoGoodsActivity.this,WebActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
