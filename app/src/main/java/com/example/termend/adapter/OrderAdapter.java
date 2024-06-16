package com.example.termend.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.termend.R;
import com.example.termend.activity.OrderDetailsActivity;
import com.example.termend.entity.OrderEntity;
import com.example.termend.fragment.OrderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private ArrayList<OrderEntity> list;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public OrderAdapter(Context context, int layoutId, ArrayList<OrderEntity> list) {
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(layoutId,null);
        }
        TextView tv_time=convertView.findViewById(R.id.tv_order_time);
        TextView tv_sumPrice = convertView.findViewById(R.id.tv_order_sumPrice);
        initEvent(convertView,position);
        OrderEntity orderEntity = list.get(position);
        tv_sumPrice.setText(String.format("￥%.2f", orderEntity.getSumPrice()));
        tv_time.setText(simpleDateFormat.format(orderEntity.getDate()));
        return convertView;
    }

    private void initEvent(View convertView, int position) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("termEnd","点击了下标为"+position+"的订单，开始跳转进入其详情页面");
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderPosition",position);
                context.startActivity(intent);
            }
        });
    }
}
