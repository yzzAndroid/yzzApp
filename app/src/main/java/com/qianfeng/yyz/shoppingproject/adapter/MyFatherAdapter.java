package com.qianfeng.yyz.shoppingproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class MyFatherAdapter extends BaseAdapter{
    List<String> list;
    Context context;
   int realPosition;


    public void setRealPosition(int realPosition) {
        this.realPosition = realPosition;
    }

    public MyFatherAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("==","=="+position);
        ViewHolder viewHolder= null;
        if (null==convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_oone,null);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //给TexView设置唯一标记
        viewHolder.textView.setText(list.get(position));


        Integer p = position;
        //判断并且设置背景
            if (realPosition==position){
               convertView.setBackgroundResource(R.color.unselect);
            }else {
                convertView.setBackgroundResource(R.color.Them);
            }
        return convertView;
    }



class ViewHolder{
    TextView textView;
    View view;

    public ViewHolder(View view) {
        this.view = view;
        textView = (TextView) view.findViewById(R.id.tv_father);
        view.setTag(this);
    }
}
}
