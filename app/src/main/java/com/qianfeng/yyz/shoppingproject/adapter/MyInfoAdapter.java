package com.qianfeng.yyz.shoppingproject.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.bean.InfoGoods;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class MyInfoAdapter extends BaseAdapter {

    InfoGoods infoGoods;
    Context context;

    public MyInfoAdapter(Context context) {
        this.context = context;

    }

    public void setInfoGoods(InfoGoods infoGoods) {
        this.infoGoods = infoGoods;
    }

    @Override
    public int getCount() {
        if (null==infoGoods.getData()){
            return 0;
        }
        return infoGoods.getData().getItems().size();
    }

    @Override
    public Object getItem(int position) {

        if (null==infoGoods.getData()){
            return null;
        }
        return infoGoods.getData().getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null==convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_info,null);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(infoGoods.getData().getItems()
                .get(position).getComponent().getPicUrl())
                .into(viewHolder.imageView);
        viewHolder.textView1.setText(infoGoods.getData()
                .getItems().get(position).getComponent().getDescription());
        Log.e("===","=ttt="+viewHolder.textView1.getText());

        viewHolder.textView2.setText(infoGoods.getData()
                .getItems().get(position).getComponent().getPrice());

        viewHolder.textView3.setText("原￥:"+infoGoods.getData()
                .getItems().get(position).getComponent().getOrigin_price());
        viewHolder.textView3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.textView4.setText("销量:"+infoGoods.getData()
                .getItems().get(position).getComponent().getSales());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        View view;

        public ViewHolder(View view) {
            this.view = view;
            textView1 = (TextView) view.findViewById(R.id.tv_info_name);
            textView2 = (TextView) view.findViewById(R.id.tv_info_money);
            textView3 = (TextView) view.findViewById(R.id.info_orignal_price);
            textView4 = (TextView) view.findViewById(R.id.info_num);
            imageView = (ImageView) view.findViewById(R.id.iv_info_item);
            view.setTag(this);

        }
    }
}
