package com.qianfeng.yyz.shoppingproject.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {

    private TextView f_info_name;
    private TextView f_info_time;
    private TextView f_info_state;
    private SharedPreferences user;
    private SharedPreferences time;
    private InitBallTitleCallback initBallTitleCallback;
    private TextView f_info_car;
    private TextView f_info_love;
    private TextView f_info_empty;
    public UserInfoFragment() {

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

        View view = inflater.inflate(R.layout.fragment_info, container, false);
       init(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        user = null;
        time = null;
    }


    public void init(View view){
        f_info_name = (TextView) view.findViewById(R.id.f_info_name);
        f_info_time = (TextView) view.findViewById(R.id.f_info_time);
        f_info_state = (TextView) view.findViewById(R.id.f_info_state);

        f_info_car = (TextView) view.findViewById(R.id.f_info_car);
        f_info_love = (TextView) view.findViewById(R.id.f_info_love);
        f_info_empty = (TextView) view.findViewById(R.id.f_info_empty);


        user = getActivity().getSharedPreferences(Contants.USERNAME,Context.MODE_PRIVATE);
        time = getActivity().getSharedPreferences("time",Context.MODE_PRIVATE);
        initBallTitleCallback.setTextListener("我的信息");
        if (!time.getBoolean("state",false)){

        }else {
            f_info_state.setVisibility(View.GONE);
            f_info_time.setVisibility(View.VISIBLE);
            f_info_name.setVisibility(View.VISIBLE);
            String name = user.getString("name", "");
            f_info_name.setText("亲爱的会员：" + name + " 您好！");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

            String timing = simpleDateFormat.format(new Date(time.getLong(name, 0)));
            f_info_time.setText("注册时间：" + timing);


            int count_love = SaveCollects.getInstance(getActivity(), Contants.TB_NAME).querry().getCount();
            SaveCollects.getInstance(getActivity(),Contants.TB_NAME).close();
            int count_car = SaveCollects.getInstance(getActivity(), Contants.TB_NAME_CAR).querry().getCount();
            if (count_car == 0 && count_love == 0) {
                f_info_love.setVisibility(View.GONE);
                f_info_car.setVisibility(View.GONE);
                return;
            }
            if (count_love>0){
                f_info_empty.setVisibility(View.GONE);
                f_info_love.setText("您收藏了 "+count_love+" 件商品，详情查看"+" 我的收藏");
            }
            if (count_car>0){
                f_info_empty.setVisibility(View.GONE);
                f_info_car.setText("您的购物车有 "+count_car+" 件商品，详情查看"+" 我的购物车");
            }
        }



    }
}
