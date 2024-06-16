package com.example.termend.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.termend.R;
import com.example.termend.activity.ProductDetailsActivity;
import com.example.termend.entity.Product;
import com.example.termend.fragment.ProductFragment;
import com.example.termend.utils.ItemSizeUtils;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.URLUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> list;
    private int layout;
    private Context context;

    public ProductAdapter(ArrayList<Product> list, int layout, Context context) {
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
            //为了看着好看一点，我动态设置了一下linearlayout和imageView的大小让每个item是三分之一屏幕高度，图片是四分之一
            convertView.findViewById(R.id.ll_item_product).setLayoutParams(ItemSizeUtils.setLinearLayoutSize(context));
        }
        ImageView imageView_item_product= convertView.findViewById(R.id.iv_item_product_pic);
        TextView tv_name= convertView.findViewById(R.id.tv_item_product_name);
        TextView tv_price=convertView.findViewById(R.id.tv_item_product_price);
        initEvent(convertView,position);
        Handler handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                imageView_item_product.setImageBitmap(list.get((Integer)msg.obj).getBitmap());
            }
        };
        imageView_item_product.setLayoutParams(ItemSizeUtils.setPicSize(context));
        if (list.get(position).getBitmap()==null) {
            View finalConvertView = convertView;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("termEnd", "开始加载下标为" + position + "的图片");
                    setBitmap(handler, position);
                }
            }).start();
        }else {
            Log.i("termEnd",position+"不为空");
            imageView_item_product.setImageBitmap(list.get(position).getBitmap());
        }
        tv_name.setText(list.get(position).getName());
        tv_price.setText("￥"+list.get(position).getPrice());
        return convertView;
    }

    private void setBitmap(Handler handler, int position) {
        Bitmap bitmap = ProductUtils.getPic(list.get(position).getPicAddress());
        list.get(position).setBitmap(bitmap);
        handler.sendMessage(handler.obtainMessage(1,position));
    }

    private void initEvent(View view,int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("termEnd","点击了下标为"+position+"的商品，开始跳转进入其详情页面");
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productId",ProductFragment.productsFromDatabase.get(position).getId());
                intent.putExtra("productPicAddress",ProductFragment.productsFromDatabase.get(position).getPicAddress());
                context.startActivity(intent);
            }
        });
    }
}
