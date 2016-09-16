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

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.callback.UpdateContentCallback;

import java.util.ArrayList;
import java.util.List;

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
        List list = new ArrayList();
        list.add("首页");
        list.add("我的收藏");
        list.add("我的购物车");
        list.add("我的信息");

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        view.setMinimumWidth(metrics.widthPixels*(2/3));
        listView.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.me_textview,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               contentCallback.updateCallback(position);
            }
        });
        return view;

    }

}
