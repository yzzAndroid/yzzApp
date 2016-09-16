package com.qianfeng.yyz.shoppingproject.json;

import android.util.Log;

import com.google.gson.Gson;
import com.qianfeng.yyz.shoppingproject.bean.Goods;
import com.qianfeng.yyz.shoppingproject.bean.SonGoods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class Goods2Java {

    private static List<Goods> list;

    public static List<Goods> getFatherGoods(String path){
        try {
            list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(path);
            JSONObject object = jsonObject.getJSONObject("data");
            JSONArray array = object.getJSONArray("items");
            int length = array.length();
            for (int i = 0;i<length;i++){
                JSONObject object1  = array.getJSONObject(i);
                JSONObject object2 = object1.getJSONObject("component");
                JSONArray array1 = object2.getJSONArray("items");
                int length2 = array1.length();
                Goods goods = new Goods();
                List<SonGoods> list1 = new ArrayList<>();
                for (int j = 0;j<length2;j++){
                   JSONObject object3 = array1.getJSONObject(j).getJSONObject("component");
                    String sonName = object3.getString("word");
                    String imageURL = object3.getString("picUrl");
                    SonGoods sonGoods = new SonGoods();
                    sonGoods.setName(sonName);
                    sonGoods.setPath(imageURL);
                    list1.add(sonGoods);
                    //Log.e("====","===="+sonName);
                   // Log.e("====","===="+imageURL);
                }
                String name = object2.getString("title");

                goods.setFather(name);
                goods.setList(list1);
               // Log.e("====","===="+name);
                list.add(goods);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
