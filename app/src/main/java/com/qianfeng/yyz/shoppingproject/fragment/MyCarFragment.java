package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.adapter.MyCarAdapter;
import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.callback.CallbackBuyCar;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;
import com.qianfeng.yyz.shoppingproject.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCarFragment extends Fragment implements CallbackBuyCar{

    ListView listView;
    View clickView;
    CheckBox checkBox;
    Button textView_delete;
    Button textView_buy;
    List<Boolean> list;
    MyCarAdapter myCarAdapter;
    Cursor cursor;


    InitBallTitleCallback initBallTitleCallback;
    public MyCarFragment() {
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
       View view = inflater.inflate(R.layout.fragment_my_car, container, false);
        list = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.car_lv);
        checkBox = (CheckBox) view.findViewById(R.id.f_car_cb);
        textView_delete = (Button) view.findViewById(R.id.f_car_delete);
        textView_buy = (Button) view.findViewById(R.id.f_car_buy);
        cursor = SaveCollects.getInstance(getActivity(), Contants.TB_NAME_CAR).querry();
        int count  = cursor.getCount();
        for (int i = 0;i<count;i++){
            list.add(false);
        }
        myCarAdapter = new MyCarAdapter(getActivity(),cursor,this);
        myCarAdapter.setList(list);
        myCarAdapter.setCheckBoxF(checkBox);
        listView.setAdapter(myCarAdapter);

        initBallTitleCallback.setTextListener("我的购物车");

        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickView = view;
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).querry();
                Utils.toWeb(cursor,getActivity(),position);
            }
        });

        setListener();

        //给list设置空的视图
        Utils.addViewToEmptyList(listView,getActivity(),"暂无记录");

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("删除购物车");
        menu.add(0,0,0,"取消");
        menu.add(0,1,1,"删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:

                break;
            case 1:

                String name= ((TextView)clickView.findViewById(R.id.car_tv_name)).getText().toString();
                int d = SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).delete(new Collects(0,name,0,null,0,null));
                if (d>0){
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    deleteToUpDatalist();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).close();
    }

    public void setListener(){

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(checkBox.isChecked());
                boolean b = checkBox.isChecked();
                int c = cursor.getCount();
                for (int i = 0;i<c;i++){
                    list.set(i,b);
                }
                myCarAdapter.setList(list);
                myCarAdapter.notifyDataSetChanged();

            }
        });

        textView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                boolean b;
                int tag = 0;
                int numTag = 0;
                Cursor cursor = SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).querry();
                while (cursor.moveToNext()){
                    b =  list.get(cursor.getPosition());
                    if (b){
                         numTag++;
                         name = cursor.getString(cursor.getColumnIndex(Contants.NAME));
                        int u =  SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).delete(new Collects(0,name,0,null,0,null));
                        tag+=u;
                    }
                }
                if (tag==numTag&&tag!=0){
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
                //更新listView
                deleteToUpDatalist();

            }
        });
    }

    public void deleteToUpDatalist(){
        cursor = SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).querry();
        list.clear();
        int c = cursor.getCount();
        for (int i = 0;i<c;i++){
            list.add(false);
        }
        checkBox.setChecked(false);
        myCarAdapter = new MyCarAdapter(getActivity(),cursor,this);
        myCarAdapter.setList(list);
        myCarAdapter.setCheckBoxF(checkBox);
        listView.setAdapter(myCarAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyCarAdapter.closePool();
        SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).close();
    }

    @Override
    public void callBack() {
        cursor = SaveCollects.getInstance(getActivity(),Contants.TB_NAME_CAR).querry();
        myCarAdapter = new MyCarAdapter(getActivity(),cursor,this);
        myCarAdapter.setList(list);
        myCarAdapter.setCheckBoxF(checkBox);
        listView.setAdapter(myCarAdapter);
    }
}
