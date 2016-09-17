package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.adapter.MyCollectsAdapter;
import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;
import com.qianfeng.yyz.shoppingproject.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectFragment extends Fragment {

    private ListView listView;
    private InitBallTitleCallback initBallTitleCallback;
    private View clickView;


    public MyCollectFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_collect, container, false);
        listView = (ListView) view.findViewById(R.id.collect_lv);
        MyCollectsAdapter myCollectsAdapter = new MyCollectsAdapter(getActivity(),
                SaveCollects.getInstance(getActivity(),Contants.TB_NAME).querry());
        listView.setAdapter(myCollectsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = SaveCollects.getInstance(getActivity(),Contants.TB_NAME).querry();
                Utils.toWeb(cursor,getActivity(),position);
            }
        });


        Utils.addViewToEmptyList(listView,getActivity(),"暂无数据");
        initBallTitleCallback.setTextListener("我的收藏");

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickView = view;
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SaveCollects.getInstance(getActivity(), Contants.TB_NAME).close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("删除我的收藏");
        menu.add(0,0,0,"取消");
        menu.add(0,1,1,"删除");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:

                break;
            case 1:

                String name= ((TextView)clickView.findViewById(R.id.collect_tv)).getText().toString();
                int d = SaveCollects.getInstance(getActivity(),Contants.TB_NAME).delete(new Collects(0,name,0,null,0,null));
                if (d>0){
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    listView.setAdapter(new MyCollectsAdapter(getActivity(),
                            SaveCollects.getInstance(getActivity(),Contants.TB_NAME).querry()));
                }
                break;
        }

        return super.onContextItemSelected(item);
    }
}
