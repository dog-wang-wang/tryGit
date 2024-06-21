package com.example.homework_goodsmore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework_goodsmore.R;
import com.example.homework_goodsmore.adapter.ShoppingCarAdapter;
import com.example.homework_goodsmore.eneity.Product;
import com.example.homework_goodsmore.utils.ViewPagerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ShoppingCarActivity extends AppCompatActivity {
    private Product product;
    private ListView listView;
    private Button btn_buy;
    private TextView tv_sumPrice;
    private double sumPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("message","create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        product = (Product) getIntent().getSerializableExtra("product");
        if(product!=null) {
            Integer numByID = Product.shoppGoodsNumber.get(product.getId());//通过id获取到对应该id的商品在购物车原来的数量，没有就是null了
            if(numByID!=null){//如果原来就有这个商品
                numByID=numByID+1;
                Product.shoppGoodsNumber.put(product.getId(),numByID);
            }else {//如果原来没有这个商品
                Product.shoppGoodsNumber.put(product.getId(),1);
                Product.shoppingCar.add(product);
            }
        }
        initViews();
        setSumPrice();
        initAdapterAndListView();
    }

    //TODO:这里面的注释是我为啥写这个方法，正好原因和上周学的生命周期相关，我就保留了下来然后把下行注释里说的这个方式给舍弃了
    //虽然可以把上面product那部分放到detailActivity里面也可以解决这个问题，但是我后来尝试了下面这个方法就实在懒得改回去了，而且这个点和生命周期恰好相关，当然留着了，让我自己长个记性
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //在实际操作过程中我手机无意中横屏发现数据会变更，通过log发现原因在于横竖屏会导致页面销毁重新创建，所以在manifest里添加了语句使得横竖屏切换时不销毁，而是调用这个方法
        Log.i("message","con");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        Log.i("message","destroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i("message","start");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("message","restart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("message","resume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("message","pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("message","stop");
        super.onStop();
    }

    private void initAdapterAndListView() {
        ShoppingCarAdapter adapter = new ShoppingCarAdapter(Product.shoppingCar,R.layout.shopping_car_item,this);
        listView.setAdapter(adapter);
        //当listView里的数据发生变化
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                setSumPrice();
                super.onChanged();
            }
        });
    }
    private void initViews() {
        listView = findViewById(R.id.lv_shopping_car);
        btn_buy = findViewById(R.id.btn_buy_car);
        tv_sumPrice = findViewById(R.id.tv_car_sum_price);
    }
    public void setSumPrice(){
        //定义每个商品单价和每个商品购买数量
        double perPrice = 0;
        int perNumber = 0;
        sumPrice=0;
        //遍历购物车的商品
        for (Product product1 : Product.shoppingCar) {
            //获取每个商品的单价
            perPrice=product1.getPrice();
            perNumber=Product.shoppGoodsNumber.get(product1.getId());
            //通过商品id获取到商品数量
            sumPrice+=perPrice*perNumber;
            Log.i("message","商品id"+product1.getId()+"商品数"+perNumber+"商品单价："+perPrice+"当前总价格"+sumPrice);
        }
        tv_sumPrice.setText(String.format("%.2f",sumPrice));
    }
}



