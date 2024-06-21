package com.example.homework_goodsmore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework_goodsmore.activity.DetailsActivity;
import com.example.homework_goodsmore.R;
import com.example.homework_goodsmore.eneity.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    List<Product> data ;
    Integer layoutId;
    Context context;

    public ProductAdapter(List<Product> data, Integer layoutId, Context context) {
        this.data = data;
        this.layoutId = layoutId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(layoutId,null);
        }
        ImageView imageView = convertView.findViewById(R.id.img_product);
        TextView name = convertView.findViewById(R.id.tv_product_name);
        TextView message =convertView.findViewById(R.id.tv_product_message);
        TextView price = convertView.findViewById(R.id.tv_product_price);
        imageView.setImageResource(data.get(position).getImg());
        name.setText(data.get(position).getName());
        message.setText(data.get(position).getMessage());
        price.setText("￥"+data.get(position).getPrice());
        convertView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("productMessage",data.get(position));
            context.startActivity(intent);
        });
        return convertView;
    }
}
