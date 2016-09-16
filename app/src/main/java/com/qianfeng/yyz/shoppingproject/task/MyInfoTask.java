package com.qianfeng.yyz.shoppingproject.task;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.qianfeng.yyz.shoppingproject.bean.InfoGoods;
import com.qianfeng.yyz.shoppingproject.callback.InfoTextCallBack;
import com.qianfeng.yyz.shoppingproject.http.GetString;
import com.qianfeng.yyz.shoppingproject.json.GsonInfoGoods;
import com.qianfeng.yyz.shoppingproject.save.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class MyInfoTask extends AsyncTask<String,Void,InfoGoods>{

    InfoTextCallBack infoTextCallBack;


    public MyInfoTask(InfoTextCallBack infoTextCallBack) {
        this.infoTextCallBack = infoTextCallBack;
    }

    @Override
    protected InfoGoods doInBackground(String... params) {

        InfoGoods infoGoods = null;
        String str = null;
        if (TextUtils.isEmpty(params[0])){
            return null;
        }
        //文件读取
        File file = FileUtils.getFilePath(params[1]);
        String fil = FileUtils.read(file);
        //文件为空就网络下载
        if (TextUtils.isEmpty(fil)){
            str = GetString.getString(params[0]);
            if (TextUtils.isEmpty(str)){
                return null;
            }
            FileUtils.write(str,file);
        //本地为不为空就本地读取
        }else {
            str = fil;
        }

        infoGoods = GsonInfoGoods.getDta(str);
        return infoGoods;
    }

    @Override
    protected void onPostExecute(InfoGoods infoGoods) {
        super.onPostExecute(infoGoods);
        if (null==infoGoods||null==infoTextCallBack){
            return;
        }
        infoTextCallBack.setText(infoGoods);
    }
}
