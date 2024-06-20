package com.example.termend.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.termend.MainActivity;
import com.example.termend.R;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.fragment.MineFragment;
import com.example.termend.fragment.OrderFragment;
import com.example.termend.fragment.ProductFragment;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.ResultUtils;
import com.example.termend.utils.URLUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView img;
    private TextView tv_name;
    private TextView tv_price;
    private TextView tv_num;
    private Button btn_into_car;
    private Product product;
    private Handler handler;
    private TextView tv_inventory;
    private Button buy;
    private Button add;
    private Button drop;
    private int buy_num=1;
    private Bitmap detailBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //从后端获取商品信息，包括图片，并且填充图片
        initProduct();
        //初始换组件
        initViews();
        //初始化handler,这里面使用获取到的数据完成对基本信息的填充
        initHandler();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //关联资源
        getMenuInflater().inflate(R.menu.menu_shopping_car,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override//这个方法是对菜单选项被选中的设置
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent =  new Intent(ProductDetailsActivity.this,ShoppingCarActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        buy_num=1;
        tv_num.setText(String.valueOf(buy_num));
        super.onPause();
    }
    private void initViews() {
        img = findViewById(R.id.img_detail);
        tv_name = findViewById(R.id.tv_detail_name);
        tv_price = findViewById(R.id.tv_detail_price);
        tv_inventory = findViewById(R.id.tv_detail_inventory);
        tv_num = findViewById(R.id.tv_details_num);
        btn_into_car = findViewById(R.id.btn_details_into_car);
        buy = findViewById(R.id.btn_details_buy);
        add = findViewById(R.id.btn_details_add);
        drop = findViewById(R.id.btn_details_drop);
        buy_num=1;
        tv_num.setText(String.valueOf(buy_num));
    }
    private void initHandler() {
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                    Result<ArrayList<Product>> result = (Result<ArrayList<Product>>) msg.obj;
                    product = result.getData().get(0);
                    configViews();
                    initEvent();
            }
        };
    }
    private void initProduct() {
        getBaseMessage();
        getTroubleMessage();
    }
    private void initEvent() {
        btn_into_car.setOnClickListener(v->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //既然点了就要添加进入购物车
                    //先确认是否有图片
                    if (detailBitmap==null){
                        //在这里需要去后端拿一下图片
                        detailBitmap = ProductUtils.getPic(getIntent().getStringExtra("productPicAddress"));
                    }
                    if(detailBitmap!=null) {
                        product.setBitmap(detailBitmap);
                    }
                    PerProductOrder perProductOrder = null;
                    if(buy_num>0) {
                        perProductOrder= new PerProductOrder(product, buy_num);
                        Log.i("termEnd","选择了加入购物车的数量");
                    }else {
                        perProductOrder=new PerProductOrder(product,1);
                        Log.i("termEnd","没有选择加入购物车的数量，自动为1");

                    }
                    //首先查看原先有没有这个商品，如果有就直接增加相应的数量，如果没有这个商品就添加进去
                    boolean tag = false;
                    for (PerProductOrder perProductInShoppingCar : ProductUtils.shoppingCar) {
                        if (perProductInShoppingCar.getProduct().getId()==product.getId()) {//说明原来有
                            Log.i("termEnd","原来有这类商品");
                            //首先完成操作
                            Log.i("termEnd","后来"+perProductInShoppingCar.getNum()+":"+perProductInShoppingCar.getProductSumPrice());
                            perProductInShoppingCar.setNum(perProductInShoppingCar.getNum()+perProductOrder.getNum());
                            perProductInShoppingCar.setProductSumPrice(perProductInShoppingCar.getProduct().getPrice()*perProductInShoppingCar.getNum());
                            Log.i("termEnd","后来"+perProductInShoppingCar.getNum()+":"+perProductInShoppingCar.getProductSumPrice());
                            tag= true;
                            break;
                        }
                    }//循环结束后判断刚才是否更改了购物车
                    if (!tag) {
                        Log.i("termEnd","原来没有这类商品");
                        ProductUtils.shoppingCar.add(perProductOrder);
                    }
                    Intent intent =  new Intent(ProductDetailsActivity.this,ShoppingCarActivity.class);
                    startActivity(intent);
                }
            }).start();
        });
        buy.setOnClickListener(v->{
            //先检查一遍库存，在后端界面修改的时候还会检查一遍的,是为了防止在浏览商品细节的时候别人也买了商品导致库存不足
            if(product.getInventory()>=buy_num) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认要进行支付吗");
                builder.setCancelable(false);
                //TODO:这里是要跳转到支付页面的，但是没有要求做，就只给后端发请求了,然后后端会返回回来result对象，其中包裹着更新后的product信息
                builder.setPositiveButton("确认支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int num = buy_num;//之所以在定义一遍是防止在生成订单的过程当中再按按钮使得buynum发生变化
                        if(num>0) {//购买的前提是有数量，数量必须大于0才买呢，否则不处罚购买效果
                            Log.i("termEnd","开始更新数据库库存并获取返回的最新的该商品的数据并且生成订单对象然后跳转到主界面");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //生成订单商品信息
                                        //先构建出来一个存放所有种类商品购买信息的集合
                                    ArrayList<PerProductOrder> allKindsOfProduct = new ArrayList<>();
                                        //构建出来一个存放一类商品购买信息的实体
                                    PerProductOrder perProductInOrder = new PerProductOrder(product,num);
                                        //把实体添加进去所有种类当中
                                    allKindsOfProduct.add(perProductInOrder);
                                        //生成订单：先再后端生成订单然后后端订单一类商品一类商品进行添加购买记录然后后端更新订单总价
                                    OrderEntity order = ProductUtils.newOrder(allKindsOfProduct,ProductDetailsActivity.this);
                                        //在订单集合中加入刚刚生成的订单
                                    ProductUtils.orderList.add(order);

                                    //下面的操作就是直接跳转页面到主界面了
                                    Intent intent = new Intent(ProductDetailsActivity.this, MineActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).start();
                        }else {
                            Log.i("termEnd","购买数量为0");
                            Toast.makeText(ProductDetailsActivity.this, "购买数量为0", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("取消支付", null);
                builder.create().show();
            }else {
                Toast.makeText(this,"商品库存不足",Toast.LENGTH_LONG).show();
            }
        });
        add.setOnClickListener(v->{
            buy_num+=1;
            tv_num.setText(String.valueOf(buy_num));
        });
        drop.setOnClickListener(v->{
            if(buy_num>1) {
                buy_num -= 1;
            }
            tv_num.setText(String.valueOf(buy_num));
        });
    }



    private void configViews() {
        tv_name.setText(product.getName());
        tv_price.setText("￥"+product.getPrice());
        tv_inventory.setText("剩余商品:   "+product.getInventory());
    }
    private void getBaseMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("termEnd","开始获取商品基础信息");
                Result<ArrayList<Product>> result = ProductUtils.getProductsBasicMessage(getIntent().getIntExtra("productId",0));
                handler.sendMessage(handler.obtainMessage(1,result));
            }
        }).start();
    }
    private void getTroubleMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("termEnd","开始获取商品图片信息");
                Bitmap bitmap = ProductUtils.getPic(getIntent().getStringExtra("productPicAddress"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);
                        detailBitmap = bitmap;
                        Log.i("termEnd","商品图片信息获取完成");
                    }
                });
            }
        }).start();
    }
}