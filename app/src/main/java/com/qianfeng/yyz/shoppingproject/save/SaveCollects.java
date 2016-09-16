package com.qianfeng.yyz.shoppingproject.save;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.contants.Contants;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class SaveCollects {

    private static String TB_name;
    private static SQLiteDatabase database;
    private static SaveCollects saveCollects;

    private SaveCollects(Context context,String name){
        database = context.openOrCreateDatabase(Contants.DB_NAME,Context.MODE_PRIVATE,null);

            String sql = "create table if not exists "+name+"("
                    +Contants.TB_ID+" integer primary key autoincrement ,"
                    +Contants.NAME+" text ,"
                    +Contants.NUM+" integer ,"
                    +Contants.PRICE+" real ,"
                    +Contants.MONEY+" real ,"
                    +Contants.PURL +" text ,"
                    +Contants.PATH +" text "
                    + ")";

        database.execSQL(sql);
    }


    public static SaveCollects getInstance(Context context,String name){
        TB_name = name;
        if (null==saveCollects){
            saveCollects = new SaveCollects(context,name);
            return saveCollects;
        }
        return saveCollects;
    }


    /**
     * @param collects
     * 判断
     */
    public void loade(Collects collects){
        int count = querry().getCount();
        if (count>0){
            if (querry(collects).getCount()>0){
                updata(collects);
                return;
            }
            insert(collects);
            return;
        }
        insert(collects);
    }

    public Cursor querry(){
        Cursor cursor = database.query(TB_name,null,null,null,null,null,null);
        //不能这样，Cursor只能movetoNext一次
//        while (cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex(Contants.NAME));
//            Log.e("====","===="+name);
//        }
        return cursor;
    }

    public Cursor querry(Collects collects){
        Cursor cursor = database.query(TB_name,null,Contants.NAME+" = ? ",new String[]{collects.getName()},null,null,null);
        return cursor;
    }

    public int delete(Collects collects){
        int d = database.delete(TB_name,Contants.NAME+" = ? ",new String[]{collects.getName()});
        Log.e("=dd=","=dd="+d);
        return d;
    }

    public void updata(Collects collects){

        int u = database.update(TB_name,updataValues(collects),Contants.NAME+" = ? ",new String[]{collects.getName()});
        Log.e("=u==","==u="+u);
    }

    public void insert(Collects collects){

       Long id = database.insert(TB_name,null, loadValues(collects));

    }

    public ContentValues loadValues(Collects collects){
        ContentValues values = new ContentValues();
        values.put(Contants.NAME,collects.getName());
        values.put(Contants.PRICE,collects.getPrice());
        values.put(Contants.NUM,collects.getNum());
        values.put(Contants.MONEY,collects.getMoney());
        values.put(Contants.PURL,collects.getpUrl());
        values.put(Contants.PATH,collects.getPath());
        return values;
    }

    public ContentValues updataValues(Collects collects){
        ContentValues values = new ContentValues();
        values.put(Contants.NUM,collects.getNum());
        values.put(Contants.MONEY,collects.getMoney());
        return values;
    }



    public void close(){
        saveCollects = null;
        database.close();
    }

}
