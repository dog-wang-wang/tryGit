package com.example.termend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.termend.R;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;

import java.util.ArrayList;

public class OrderDetailAdapter extends BaseAdapter {
    private Context context;
    private Integer layoutId;
    private ArrayList<PerProductOrder> list;
    public OrderDetailAdapter(Context context, Integer layoutId, ArrayList<PerProductOrder> list) {
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
        TextView tv_name = convertView.findViewById(R.id.tv_order_detail_item_name);
        TextView tv_num = convertView.findViewById(R.id.tv_order_detail_item_num);
        TextView tv_price = convertView.findViewById(R.id.tv_order_detail_item_price);
        TextView tv_sum_price = convertView.findViewById(R.id.tv_order_detail_item_sum_price);
        PerProductOrder perProductOrder = list.get(position);
        tv_price.setText(String.format("单价：￥%.2f", perProductOrder.getProduct().getPrice()));
        tv_name.setText(perProductOrder.getProduct().getName());
        tv_num.setText(String.format("%d个", perProductOrder.getNum()));
        tv_sum_price.setText(String.format("总价：￥%.2f", perProductOrder.getProductSumPrice()));
        return convertView;
    }
}
