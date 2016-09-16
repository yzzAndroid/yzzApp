package com.qianfeng.yyz.shoppingproject.json;

import android.util.Log;

import com.google.gson.Gson;
import com.qianfeng.yyz.shoppingproject.bean.InfoGoods;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class GsonInfoGoods {

    public static InfoGoods getDta(String str){

        Gson gson = new Gson();
        InfoGoods goods = gson.fromJson(str, InfoGoods.class);
       return goods;
    }
}
