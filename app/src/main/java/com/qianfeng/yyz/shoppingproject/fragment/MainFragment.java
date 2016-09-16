package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.acyivity.InfoGoodsActivity;
import com.qianfeng.yyz.shoppingproject.adapter.MyAdapter;
import com.qianfeng.yyz.shoppingproject.adapter.MyFatherAdapter;
import com.qianfeng.yyz.shoppingproject.bean.Goods;
import com.qianfeng.yyz.shoppingproject.callback.GetData;
import com.qianfeng.yyz.shoppingproject.callback.ImageCallback;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.task.MyTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private String path1;
    private ListView lv_father;
    private GridView lv_son;
    private GetData getData;
    private ImageCallback imageCallback;
    private List<String> listFatherName;
    private MyTask myTask;
    private MyAdapter myAdapter;
    private List<Goods> listGoods;
    private MyFatherAdapter myFatherAdapter;
    private int size;

    View view;
    InitBallTitleCallback initBallTitleCallback;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InitBallTitleCallback){
            initBallTitleCallback = (InitBallTitleCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initBallTitleCallback.setTextListener("首页");

        init();


        return view;
    }

    public void init(){
        lv_son = (GridView) view.findViewById(R.id.lv_son);
        lv_father = (ListView) view.findViewById(R.id.lv_father);
        listFatherName = new ArrayList<>();
        listGoods = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity());


        path1 = "http://api-v2.mall.hichao.com/category/list?ga=%2Fcategory%2Flist";

        //listView先设初始值
        myFatherAdapter = new MyFatherAdapter(getActivity(),listFatherName);
        myFatherAdapter.setRealPosition(0);
        lv_father.setAdapter(myFatherAdapter);




        //异步接口回调
        getData = new GetData() {
            @Override
            public void getData(List<Goods> list) {
                Log.e("===","=="+list.size());
                size = list.size();
                for (int i = 0;i<size;i++){
                    String name = list.get(i).getFather();
                    listFatherName.add(name);
                }
                myFatherAdapter.notifyDataSetChanged();
                Log.e("===","==="+listFatherName.size());
            }
        };

        imageCallback = new ImageCallback() {
            @Override
            public void setGrideView(List<Goods> list) {
                for (Goods goods:list){
                    listGoods.add(goods);
                }
                //GridView设初始值
                myAdapter.setGoods(listGoods.get(0));
                lv_son.setAdapter(myAdapter);
                Log.e("====","==listGoods==="+listGoods.get(0).getList().size());
            }
        };
        //异步任务执行
        myTask = new MyTask(imageCallback,getData);
        myTask.execute(path1);




        //listView点击事件监听
        lv_father.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myFatherAdapter.setRealPosition(position);
                myFatherAdapter.notifyDataSetChanged();

                myAdapter.setGoods(listGoods.get(position));
                myAdapter.notifyDataSetChanged();
            }
        });


        //gridView的点击事件
        lv_son.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) view;
                TextView textView = (TextView) linearLayout.getChildAt(1);
                String goosName  = (String) textView.getText();
                Log.e("====","===="+goosName);
                try {
                    String real =  URLEncoder.encode(goosName,"utf-8");
                    String path = "http://api-v2.mall.hichao.com/search/skus?query="+real+"20%20&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1";
                    Intent intent = new Intent(getActivity(),InfoGoodsActivity.class);
                    intent.putExtra("path",path);
                    intent.putExtra("name",real);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
