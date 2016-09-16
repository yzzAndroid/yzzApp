package com.qianfeng.yyz.shoppingproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.bean.Goods;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class MyAdapter extends BaseAdapter{
    Goods goods;
    Context context;

    public MyAdapter(Context context) {
        this.context = context;

    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public int getCount() {
        return goods.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return goods.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null==convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_two,null);

            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       String name =  goods.getList().get(position).getName();
        String path = goods.getList().get(position).getPath();
        Picasso.with(context).load(path).into(viewHolder.imageView);
        viewHolder.textView.setText(name);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
        View view;

        public ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.iv_son_image);
            textView = (TextView) view.findViewById(R.id.tv_son_text);
            view.setTag(this);
        }
    }
}
