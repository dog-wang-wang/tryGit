package com.example.homework_goodsmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.homework_goodsmore.fragment.SortFragment;
import com.example.homework_goodsmore.utils.ViewPagerUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //viewpager2
    private ViewPager2 viewPager2;
    //tabLayout
    private TabLayout tabLayout;
    //tab文字集合
    private List<String> tabContent;
    //fragment集合
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        //获取视图控件
        initGet();
        //首先收拢所有的fragment存入一个list
        getFragmentList();
        //这里是获取一下tab的信息
        initTabMessageList();

        //然后设定ViewPager2
        ViewPagerUtils.setViewPager2(viewPager2,this,list);
        //这里设定ViewPager2与tabLayout的绑定
        ViewPagerUtils.setTabandViewpager(viewPager2,tabLayout,tabContent);


        //这里设置tabLayout的颜色
        tabLayout.setTabTextColors(ContextCompat.getColor(this,R.color.black),ContextCompat.getColor(this,R.color.red));
    }
    private void getFragmentList() {
        list = new ArrayList();
        list.add(new SortFragment("推荐"));
        list.add(new SortFragment("母婴"));
        list.add(new SortFragment("食品"));
    }

    private void initTabMessageList() {
        tabContent = new ArrayList<>();
        tabContent.add("推荐");
        tabContent.add("母婴");
        tabContent.add("食品");

    }

    private void initGet() {
        viewPager2 = findViewById(R.id.vp_content);
        tabLayout = findViewById(R.id.tb_nav);
    }
}