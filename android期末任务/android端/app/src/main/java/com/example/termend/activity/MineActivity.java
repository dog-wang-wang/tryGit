package com.example.termend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.termend.MainActivity;
import com.example.termend.R;
import com.example.termend.adapter.FragmentAdapter;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.fragment.MineFragment;
import com.example.termend.fragment.OrderFragment;
import com.example.termend.fragment.ProductFragment;
import com.example.termend.utils.ItemSizeUtils;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.ResultUtils;
import com.example.termend.utils.URLUtil;
import com.example.termend.utils.ViewPagerUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MineActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<String> tabString = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        WindowManager window = (WindowManager) MineActivity.this.getSystemService(Context.WINDOW_SERVICE);

        //这里初始化一下item里面的一些尺寸设定
        ItemSizeUtils.initScreenData(window);
        //在这里初始化控件
        initViews();

        //在这里实现tabLayout每个选项文字的集合
        initTabString();

        //在这里给viewpager2绑定fragment集合
        initViewPagers();

        //在这里绑定viewpager2和tabLayout
        viewPager2AndTabLayOut();

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
    private void initTabString() {
        tabString.add("商品");
        tabString.add("订单");
        tabString.add("我的");
    }

    private void viewPager2AndTabLayOut() {
        ViewPagerUtils.setTabandViewpager(viewPager2,tabLayout,tabString);
    }

    private void initViewPagers() {
        List<Fragment> list = new ArrayList<>();
        list.add(new ProductFragment());
        list.add(new OrderFragment());
        list.add(new MineFragment());
        ViewPagerUtils.setViewPager2(viewPager2,this,list);
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tab_main);
        viewPager2 = findViewById(R.id.vp_main);
    }
}