package com.qianfeng.yyz.shoppingproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class MyCollectsAdapter extends CursorAdapter {

    public MyCollectsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycollect_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       ViewHolder viewHolder = (ViewHolder) view.getTag();
        Log.e("====","===="+cursor.getString(cursor.getColumnIndex(Contants.PURL)));
        Log.e("====","===="+cursor.getDouble(cursor.getColumnIndex(Contants.PRICE)));
        viewHolder.textViewNum.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contants.NUM))));
        viewHolder.textViewName.setText(cursor.getString(cursor.getColumnIndex(Contants.NAME)));
        viewHolder.textViewPrice.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(Contants.PRICE))));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(Contants.PURL))).into(viewHolder.imageView);
    }

    class ViewHolder{
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewNum;
        View view;

        public ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.collect_image);
            textViewName = (TextView) view.findViewById(R.id.collect_tv);
            textViewNum = (TextView) view.findViewById(R.id.collect_tv_num);
            textViewPrice = (TextView) view.findViewById(R.id.collect_tv_price);
            view.setTag(this);
        }
    }
}
