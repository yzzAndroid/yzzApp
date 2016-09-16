package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.adapter.MyCollectsAdapter;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;
import com.qianfeng.yyz.shoppingproject.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectFragment extends Fragment {

    private ListView listView;
    InitBallTitleCallback initBallTitleCallback;

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

        Utils.addViewToEmptyList(listView,getActivity(),"暂无数据");
        initBallTitleCallback.setTextListener("我的收藏");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SaveCollects.getInstance(getActivity(), Contants.TB_NAME).close();
    }
}
