package com.qianfeng.yyz.shoppingproject.utils;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.acyivity.WebActivity;
import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.contants.Contants;

/**
 * Created by Administrator on 2016/9/16 0016.
 */
public class Utils {

    public static void addViewToEmptyList(ListView listView, Context context, String msg) {
        TextView textView = new TextView(context);
        ViewGroup viewGroup = (ViewGroup) listView.getParent();
        viewGroup.addView(textView);
        textView.setText(msg);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        listView.setEmptyView(textView);
    }

    public static void setBarColor(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.Them));
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.Them));
        }
    }

    public static void toWeb(Cursor cursor,Context context,int position) {
        Collects collects = null;
        while (cursor.moveToNext()) {

            if (cursor.getPosition() == position) {
                collects = new Collects(
                        cursor.getDouble(cursor.getColumnIndex(Contants.MONEY)),
                        cursor.getString(cursor.getColumnIndex(Contants.NAME)),
                        cursor.getInt(cursor.getColumnIndex(Contants.NUM)),
                        cursor.getString(cursor.getColumnIndex(Contants.PATH)),
                        cursor.getDouble(cursor.getColumnIndex(Contants.PRICE)),
                        cursor.getString(cursor.getColumnIndex(Contants.PURL))
                );
            }

        }
        if (collects==null){
            Toast.makeText(context, "跳转失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("path", collects.getPath());
        intent.putExtra("pUrl", collects.getpUrl());
        intent.putExtra("price", String.valueOf(collects.getPrice()));
        intent.putExtra("name", collects.getName());
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }
}
