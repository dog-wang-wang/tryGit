package com.example.termend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.termend.R;
import com.example.termend.activity.ProductDetailsActivity;
import com.example.termend.adapter.ProductAdapter;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.utils.ProductUtils;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    public static ArrayList<Product> productsFromDatabase =null;
    private ProductAdapter adapter;
    private Handler handler;
    private GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,null);
        gridView = view.findViewById(R.id.gv_product);
        //在所有操作之前先去数据库拿数据。每次结束生命周期重新进来之后都要刷新所以要去拿数据
        initProducts();
        //获取到数据以后要进行是处理
        initHandler();
//        configGridView(gridView);
        return view;
    }

    private void configGridView(GridView gridView) {
        if(productsFromDatabase!=null) {
            adapter = new ProductAdapter(productsFromDatabase, R.layout.item_product, this.getContext());
            gridView.setAdapter(adapter);
        }else {
            Toast.makeText(this.getContext(),"获取商品列表失败",Toast.LENGTH_LONG).show();
            Log.i("termEnd","获取商品列表失败");
        }
    }
    private void initHandler() {
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==1){
                    productsFromDatabase = (ArrayList<Product>)msg.obj;
                    //在获取到数据之后进行gridView的配置
                    configGridView(gridView);
                }
            }
        };
    }
    private void initProducts() {
        new Thread(new Runnable() {
            @Override
            public void run() {//该方法获取基础的信息
                Log.i("termEnd","开始获取数据库商品列表");
                Result<ArrayList<Product>> result = ProductUtils.getProductsBasicMessage();
                handler.sendMessage(handler.obtainMessage(1,result.getData()));
            }
        }).start();
    }

}
