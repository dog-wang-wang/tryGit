package com.example.termend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.termend.R;
import com.example.termend.adapter.OrderDetailAdapter;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.utils.ProductUtils;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView tv_sumPrice;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        //初始化控件
        initViews();
        //通过订单号在ProductUtils.orderList中找到对应的orderentity
        int position =getIntent().getIntExtra("orderPosition",-1);
        OrderEntity orderEntity =  ProductUtils.orderList.get(position);
        Log.i("termEnd",position+""+orderEntity.getSumPrice());
        ArrayList<PerProductOrder> list =orderEntity.getProductMessagelist();
        if (position!=-1) {
            Log.i("termEnd","开始和订单详情适配器匹配");
            OrderDetailAdapter detailAdapter = new OrderDetailAdapter(this,R.layout.item_order_product_detail,list);
            listView.setAdapter(detailAdapter);
        }
        double sumPrice = 0;
        for (PerProductOrder perProductOrder : list) {
            sumPrice+=perProductOrder.getProductSumPrice();
        }
        tv_sumPrice.setText(String.format("￥%.2f", sumPrice));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //关联资源
        getMenuInflater().inflate(R.menu.menu_shopping_car,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override//这个方法是对菜单选项被选中的设置
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent =  new Intent(this,ShoppingCarActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    private void initViews() {
        listView = findViewById(R.id.lv_order_detail);
        tv_sumPrice = findViewById(R.id.tv_order_detail_sum_price);
    }
}