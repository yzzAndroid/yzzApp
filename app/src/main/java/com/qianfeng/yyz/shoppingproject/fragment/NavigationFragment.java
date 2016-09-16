package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.callback.UpdateContentCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    private ListView listView;
    private UpdateContentCallback contentCallback;
    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UpdateContentCallback){
            contentCallback = (UpdateContentCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        listView = (ListView) view.findViewById(R.id.lv_me);
        List<Map<String,Object>> list = new ArrayList();

        String name[] = new String[]{"首页","我的收藏","我的购物车","我的信息"};
        int p[]= new int[]{R.mipmap.guide_home_nm,R.mipmap.guide_tfaccount_nm,R.mipmap.guide_cart_nm,R.mipmap.guide_account_nm};
        for (int i = 0;i<name.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("text",name[i]);
            map.put("img",p[i]);
            list.add(map);
        }



        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        view.setMinimumWidth(metrics.widthPixels*(2/3));
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),list,R.layout.me_textview,new String[]{"text","img"},new int[]{R.id.na_tv,R.id.na_img});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               contentCallback.updateCallback(position);
            }
        });
        return view;

    }

}
