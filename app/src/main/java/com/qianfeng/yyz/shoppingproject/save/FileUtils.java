package com.qianfeng.yyz.shoppingproject.save;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
//获取文件夹
public class FileUtils {

    static String fatherPath = Environment.getExternalStorageDirectory()+"/myShopping/father";

    public static File getFilePath(String name){
        File file = new File(fatherPath);
        if (!file.exists()){
            file.mkdirs();
        }
        return new File(fatherPath+"/"+name+".txt");
    }

    //保存数据
    public static void write(String str,File file){
        ByteArrayInputStream inputStream = null;
        try {

           inputStream = new ByteArrayInputStream(str.getBytes());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte temp[] = new byte[1024];
            int size = 0;
            while((size = inputStream.read(temp))!=-1){
                outputStream.write(temp,0,size);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取本地数据
    public static String read(File file){

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String temp = null;
            StringBuffer sb = new StringBuffer();
            while((temp = br.readLine())!=null){
                sb.append(temp);
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
