package com.qianfeng.yyz.shoppingproject.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/16 0016.
 */
public class Utils {

    public static void addViewToEmptyList(ListView listView, Context context,String msg){
        TextView textView = new TextView(context);
        ViewGroup viewGroup = (ViewGroup) listView.getParent();
        viewGroup.addView(textView);
        textView.setText(msg);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        listView.setEmptyView(textView);
    }
}
