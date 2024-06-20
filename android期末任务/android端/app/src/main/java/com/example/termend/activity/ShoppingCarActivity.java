package com.example.termend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termend.R;
import com.example.termend.adapter.ShoppingCarAdapter;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Result;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.ResultUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCarActivity extends AppCompatActivity {

    private Button btn_car_buy;
    private TextView sum_price;
    private ListView listView;
    private double sumPriceInt;
    private Handler handler;


    @Override
    protected void onPause() {
        Log.i("termEnd","购物车界面pause");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //开始上传已更改后的购物车信息
                if (ProductUtils.clearShoppingCar(ResultUtils.userInfo.getUserid())!=null) {
                    for (PerProductOrder perProductOrder : ProductUtils.shoppingCar) {
                        Result result = ProductUtils.uploadShoppingCarItem(ResultUtils.userInfo.getUserid(), perProductOrder.getProduct().getId(), perProductOrder.getNum(), perProductOrder.getProductSumPrice());
                        if (result != null) {//能够上传分为结果上传成功与失败
                            if (result.getStatusCode().equals(ResultUtils.RESULT_ERROR)) {
                                Toast.makeText(ShoppingCarActivity.this, perProductOrder.getProduct().getName() + "上传失败", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ShoppingCarActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }else {
                    Toast.makeText(ShoppingCarActivity.this, "请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }).start();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        for (PerProductOrder perProductOrder : ProductUtils.shoppingCar) {
            Log.i("termEnd",perProductOrder.getNum()+"个"+perProductOrder.getProduct().getName());
        }
//        初始化控件
        initViews();
        //设定listView
        configListView();
        //给底部总价设置数据
        setSumPrice();
        //这里是初始化购买按钮
        initEvent();
    }
    private void initEvent() {
        btn_car_buy.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //需要检查库存但是实在是懒得写了
            builder.setTitle("确认要进行支付吗");
            builder.setCancelable(false);
            builder.setPositiveButton("确认支付", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO:在这里加上购买操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //直接把购物车传进订单生成方法当中
                            OrderEntity order = ProductUtils.newOrder(ProductUtils.shoppingCar,ShoppingCarActivity.this);
                            if(order!=null) {
                                //订单列表当中添加订单
                                ProductUtils.orderList.add(order);
                                ProductUtils.clearShoppingCar(ResultUtils.userInfo.getUserid());
                                //换个新购物车
                                ProductUtils.shoppingCar = new ArrayList<>();
                                //下面的操作就是直接跳转页面到主界面了
                                Intent intent = new Intent(ShoppingCarActivity.this, MineActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ShoppingCarActivity.this,"购物车中所有的商品都已售罄或库存不足以支持您的购买量",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
            builder.setNegativeButton("取消支付",null);
            builder.create().show();
        });
    }
    public void setSumPrice(){
        //定义每个商品单价和每个商品购买数量
        sumPriceInt = 0;
        //遍历购物车的商品
        for (PerProductOrder perProductOrder : ProductUtils.shoppingCar) {
            sumPriceInt +=perProductOrder.getProductSumPrice();
        }
        sum_price.setText(String.format("%.2f", sumPriceInt));
    }
    private void configListView() {
        ShoppingCarAdapter adapter = new ShoppingCarAdapter(ProductUtils.shoppingCar,R.layout.item_shopping_car,this);
        listView.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                setSumPrice();
            }
        });
    }

    private void initViews() {
        listView = findViewById(R.id.lv_shopping_car);
        sum_price = findViewById(R.id.tv_car_sum_price);
        btn_car_buy = findViewById(R.id.btn_car_buy);
    }
}