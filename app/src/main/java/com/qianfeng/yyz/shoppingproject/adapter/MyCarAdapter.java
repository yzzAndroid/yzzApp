package com.qianfeng.yyz.shoppingproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.bean.Collects;
import com.qianfeng.yyz.shoppingproject.callback.CallbackBuyCar;
import com.qianfeng.yyz.shoppingproject.contants.Contants;
import com.qianfeng.yyz.shoppingproject.save.SaveCollects;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class MyCarAdapter extends CursorAdapter {

    private List<Boolean> list;
    private CheckBox checkBoxF;
    private Context context;
    private Collects collects;
    private  static ExecutorService pool;
    private CallbackBuyCar callback;
    private Handler handle;

    public static void closePool(){
        pool.shutdown();
    }

    public MyCarAdapter(Context context, Cursor c, final CallbackBuyCar callback) {
        super(context, c);
        this.context = context;
        collects = new Collects();
        pool = Executors.newFixedThreadPool(5);
        this.callback = callback;
        handle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (null!=msg){
                    if (msg.what==1){
                        callback.callBack();
                    }
                }
            }
        };
    }

    public void setList(List<Boolean> list) {
        this.list = list;
    }

    public void setCheckBoxF(CheckBox checkBoxF) {
        this.checkBoxF = checkBoxF;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.textViewNum.setText("X"+String.valueOf(cursor.getInt(cursor.getColumnIndex(Contants.NUM))));
        viewHolder.textViewName.setText(cursor.getString(cursor.getColumnIndex(Contants.NAME)));
        viewHolder.textViewPrice.setText("单价:"+String.valueOf(cursor.getDouble(cursor.getColumnIndex(Contants.PRICE))));
        viewHolder.textViewMoney.setText("总价:"+String.valueOf(cursor.getDouble(cursor.getColumnIndex(Contants.MONEY))));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(Contants.PURL))).into(viewHolder.imageView);
        viewHolder.checkBox.setChecked(list.get(cursor.getPosition()));
        viewHolder.checkBox.setTag(cursor.getPosition());
        viewHolder.num.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(Contants.NUM))));
    }

    class ViewHolder{
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewNum;
        TextView textViewMoney;
        CheckBox checkBox;
        Button sub;
        Button add;
        TextView num;
        View view;
        int count;
        double price;
        String name;

        public ViewHolder(View view) {

            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.car_image);
            textViewName = (TextView) view.findViewById(R.id.car_tv_name);
            textViewNum = (TextView) view.findViewById(R.id.car_tv_num);
            textViewPrice = (TextView) view.findViewById(R.id.car_tv_price);
            textViewMoney = (TextView) view.findViewById(R.id.car_money);
            checkBox = (CheckBox) view.findViewById(R.id.car_cb);
            sub = (Button) view.findViewById(R.id.car_sub);
            add = (Button) view.findViewById(R.id.car_add);
            num = (TextView) view.findViewById(R.id.car_tv_one);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    int position = (int) checkBox.getTag();
                   list.set(position,checkBox.isChecked());
                    int size = list.size() ;
                    for (int i = 0;i<size;i++){
                        if (!list.get(i)){
                            checkBoxF.setChecked(false);
                            return;
                        }
                    }
                   checkBoxF.setChecked(true);
                }
            });

            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   count = Integer.valueOf(num.getText().toString());
                    if (count==1){
                        count = 1;
                    }else {
                        count--;
                    }
                        price = Double.valueOf(textViewPrice.getText().toString().split(":")[1]);
                    name = textViewName.getText().toString();
                    init(count,price);
                    saveData(count,price);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = Integer.valueOf(num.getText().toString());
                    count++;
                    if (count==11){
                        Toast.makeText(context, "抱歉到达上限", Toast.LENGTH_SHORT).show();
                        count = 10;
                    }
                    name = textViewName.getText().toString();
                        price = Double.valueOf(textViewPrice.getText().toString().split(":")[1]);

                    init(count,price);
                    saveData(count,price);
                }
            });

            view.setTag(this);
        }

        public void init(int count,double price){
            num.setText(String.valueOf(count));
            textViewNum.setText("X"+String.valueOf(count));
            textViewPrice.setText("单价:"+price);
            textViewMoney.setText("总价:"+((int)(price*count*100))/100.00);
        }

        public void saveData(final int count, double price){
            collects.setName(name);
            collects.setMoney(((int)(count*price*100))/100.00);
            collects.setNum(count);

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    SaveCollects.getInstance(context,Contants.TB_NAME_CAR).loade(collects);
                    //更新列表
                    handle.sendEmptyMessage(1);
                }
            });
        }
    }
}
