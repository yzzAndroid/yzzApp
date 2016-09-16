package com.qianfeng.yyz.shoppingproject.okhttp;

import android.content.Context;
import android.util.Log;

import com.qianfeng.yyz.shoppingproject.bean.Goods;
import com.qianfeng.yyz.shoppingproject.callback.GetData;
import com.qianfeng.yyz.shoppingproject.json.Goods2Java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class OkhttpUtils {


    public static void   getData(String path, final Context context){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String str = response.body().string();
                List<Goods> list = Goods2Java.getFatherGoods(str);
            }
        });
    }
}
