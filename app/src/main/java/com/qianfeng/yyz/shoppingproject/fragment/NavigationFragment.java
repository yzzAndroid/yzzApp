package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;

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
    private TextView textView;
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


        textView = (TextView) view.findViewById(R.id.tv_me);
        SharedPreferences state = getActivity().getSharedPreferences("time",Context.MODE_PRIVATE);
        SharedPreferences user = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        if (state.getBoolean("state",false)){
            textView.setText(user.getString("name",""));
        }
        return view;

    }

}
