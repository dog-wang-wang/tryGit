package com.example.termend.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.termend.R;
import com.example.termend.activity.MineActivity;

public class ItemSizeUtils {
    public static Integer DEVICE_HEIGHT;
    public static Integer DEVICE_WIDTH;
    private static final int itemLayoutId = R.layout.item_product;
    public static ViewGroup.LayoutParams setPicSize(Context context){
        View view = LayoutInflater.from(context).inflate(itemLayoutId,null);
        ViewGroup.LayoutParams layoutParams = view.findViewById(R.id.iv_item_product_pic).getLayoutParams();
        layoutParams.height= ItemSizeUtils.DEVICE_HEIGHT/4;
        return layoutParams;
    }
    public static ViewGroup.LayoutParams setLinearLayoutSize(Context context){
        View view = LayoutInflater.from(context).inflate(itemLayoutId,null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,ItemSizeUtils.DEVICE_HEIGHT/3);
        return layoutParams;
    }
    public static void initScreenData(WindowManager window) {
        Display defaultDisplay = window.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        DEVICE_HEIGHT= displayMetrics.heightPixels;
        DEVICE_WIDTH= displayMetrics.widthPixels;
    }
}
