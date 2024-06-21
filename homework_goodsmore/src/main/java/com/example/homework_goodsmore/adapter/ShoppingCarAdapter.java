package com.example.homework_goodsmore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework_goodsmore.R;
import com.example.homework_goodsmore.eneity.Product;

import java.util.List;
import java.util.zip.Inflater;

public class ShoppingCarAdapter extends BaseAdapter {
    private List<Product> products;
    private Integer layoutId;
    private Context context;
    private ImageView imageView;
    private TextView name;
    private TextView num;
    private Button btn_add;
    private Button btn_drop;
    public ShoppingCarAdapter(List<Product> products, Integer layoutId, Context context) {
        this.products = products;
        this.layoutId = layoutId;
        this.context = context;
    }
    @Override
    public int getCount() {
        return products.size();
    }
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(layoutId,null);
        }
        imageView = convertView.findViewById(R.id.img_car);
        name = convertView.findViewById(R.id.tv_car_name);
        num = convertView.findViewById(R.id.tv_num);
        btn_add = convertView.findViewById(R.id.btn_add);
        btn_drop = convertView.findViewById(R.id.btn_drop);
        imageView.setImageResource(products.get(position).getImg());
        name.setText(products.get(position).getName());
        num.setText(String.valueOf(Product.shoppGoodsNumber.get(products.get(position).getId())));
        initEvents(position);
        return convertView;
    }

    private void initEvents(int position) {
        btn_add.setOnClickListener(v -> {
            Integer number = Product.shoppGoodsNumber.get(products.get(position).getId());
            //点中以后需要
            //让购买数加一
            number=number+1;
            //再把对应这个商品的数量在购物车集合中加一
            Product.shoppGoodsNumber.put(products.get(position).getId(),number);
            //然后设置显示效果在购物车文本框中
            num.setText(String.valueOf(Product.shoppGoodsNumber.get(products.get(position).getId())));
            this.notifyDataSetChanged();
        });
        btn_drop.setOnClickListener(v->{
            Integer number = Product.shoppGoodsNumber.get(products.get(position).getId());
            number=number-1;
            if(number!=0) {
                Product.shoppGoodsNumber.put(products.get(position).getId(), number);
                num.setText(String.valueOf(Product.shoppGoodsNumber.get(products.get(position).getId())));
                this.notifyDataSetChanged();
            }else {
                //删除数目，且删除productlist中的条目
                Product.shoppGoodsNumber.remove(products.get(position).getId());
                Product.shoppingCar.remove(products.get(position));
                this.notifyDataSetChanged();
            }
        });
    }
}
