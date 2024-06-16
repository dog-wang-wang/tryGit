package com.example.termend.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.termend.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class ViewPagerUtils {
    //对一个viewPager2设置适配器以及内容,需要传入的参数是一个viewpager2，一个显示该viewpager2的页面，还有viewpager2的fragment集合
    public static void setViewPager2(ViewPager2 viewPager2, FragmentActivity fragmentActivity, List<Fragment> fragmentList){
        //把这个fragmentList传递给适配器让每个fragment和适配器绑定
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentActivity, fragmentList);
        //绑定viewpager和适配器，就可以传递进去了
        viewPager2.setAdapter(fragmentAdapter);
    }


    //给viewPager2关联TabLayout,并且给内容，需要传入的参数是viewpager，要绑定的tabLayout，还有tablayout的相关属性，第一个重载是只有文本
    public static void setTabandViewpager(ViewPager2 viewPager2,TabLayout tabLayout,List<String> tabTextList){
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTextList.get(position));
            }
        });
        tabLayoutMediator.attach();
    }
    //给viewPager2关联TabLayout,并且给内容，需要传入的参数是viewpager，要绑定的tabLayout，还有tablayout的相关属性，这第二个个重载是有文本和icon
    public static void setTabandViewpager(ViewPager2 viewPager2,TabLayout tabLayout,List<String> tabTextList,List<Integer> tabLayoutIcon){
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTextList.get(position));
                tab.setIcon(tabLayoutIcon.get(position));
            }
        });
        tabLayoutMediator.attach();
    }
}
