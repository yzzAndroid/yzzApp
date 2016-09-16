package com.qianfeng.yyz.shoppingproject.task;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.qianfeng.yyz.shoppingproject.bean.Goods;
import com.qianfeng.yyz.shoppingproject.callback.GetData;
import com.qianfeng.yyz.shoppingproject.callback.ImageCallback;
import com.qianfeng.yyz.shoppingproject.http.GetString;
import com.qianfeng.yyz.shoppingproject.json.Goods2Java;
import com.qianfeng.yyz.shoppingproject.save.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class MyTask extends AsyncTask<String,Void,List<Goods>> {


    GetData getData;
    ImageCallback callback;

    public MyTask(ImageCallback callback, GetData getData) {
        this.callback = callback;
        this.getData = getData;
    }

    @Override
    protected List<Goods> doInBackground(String... params) {
        List<Goods> goodsList = null;
        File file = FileUtils.getFilePath("fatherList");
        if (TextUtils.isEmpty(FileUtils.read(file))){
            String str = GetString.getString(params[0]);
            Log.e("==","=="+str);
            if (null==str){
                return null;
            }
           goodsList = Goods2Java.getFatherGoods(str);
            FileUtils.write(str,file);
        }else {
           String s =  FileUtils.read(file);
            goodsList = Goods2Java.getFatherGoods(s);
        }

        return goodsList;
    }


    @Override
    protected void onPostExecute(List<Goods> list) {
        super.onPostExecute(list);
        if (getData!=null&&list!=null){
            getData.getData(list);
        }
        if (callback!=null&&list!=null){
            callback.setGrideView(list);
        }

    }


}
