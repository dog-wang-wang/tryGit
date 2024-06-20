package com.example.termend.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.termend.R;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.utils.ItemSizeUtils;
import com.example.termend.utils.ProductUtils;

import java.util.ArrayList;

public class ShoppingCarAdapter extends BaseAdapter {
    private ArrayList<PerProductOrder> list;
    private int layout;
    private Context context;
    private Button btn_add;
    private Button btn_drop;
    private TextView num;
    private ImageView imageView;
    private TextView name;

    public ShoppingCarAdapter(ArrayList<PerProductOrder> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
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
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        imageView = convertView.findViewById(R.id.iv_car_item_img);
        name = convertView.findViewById(R.id.tv_car_item_name);
        num = convertView.findViewById(R.id.tv_car_item_num);
        btn_add = convertView.findViewById(R.id.btn_car_item_add);
        btn_drop = convertView.findViewById(R.id.btn_car_item_drop);
        PerProductOrder perProductOrder = list.get(position);
        imageView.setImageBitmap(perProductOrder.getProduct().getBitmap());
        name.setText(perProductOrder.getProduct().getName());
        num.setText(String.valueOf(perProductOrder.getNum()));
        initEvents(position);
        return convertView;
    }
    private void initEvents(int position) {
        btn_add.setOnClickListener(v -> {
            PerProductOrder perProductOrder = ProductUtils.shoppingCar.get(position);
            Integer number = perProductOrder.getNum();
            //点中以后需要
            //让购买数加一
            number=number+1;
            //再把对应这个商品的数量在购物车集合中加一
            perProductOrder.setNum(number);
            //然后设置显示效果在购物车文本框中
            perProductOrder.setProductSumPrice(perProductOrder.getNum()*perProductOrder.getProduct().getPrice());
            notifyDataSetChanged();
        });
        btn_drop.setOnClickListener(v->{
            PerProductOrder perProductOrder = ProductUtils.shoppingCar.get(position);
            Integer number = perProductOrder.getNum();
            number=number-1;
            if(number!=0) {
                perProductOrder.setNum(number);
                perProductOrder.setProductSumPrice(perProductOrder.getNum()*perProductOrder.getProduct().getPrice());
                num.setText(String.valueOf(ProductUtils.shoppingCar.get(position).getNum()));
                notifyDataSetChanged();
            }else {
                //删除数目，且删除productlist中的条目
                ProductUtils.shoppingCar.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
