package com.qianfeng.yyz.shoppingproject.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;

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
}
