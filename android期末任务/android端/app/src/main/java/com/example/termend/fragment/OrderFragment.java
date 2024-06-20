package com.example.termend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.termend.R;
import com.example.termend.activity.OrderDetailsActivity;
import com.example.termend.activity.ProductDetailsActivity;
import com.example.termend.adapter.OrderAdapter;
import com.example.termend.entity.OrderEntity;
import com.example.termend.utils.ProductUtils;

public class OrderFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("termEnd","详情create");
        View view=inflater.inflate(R.layout.fragment_order,null);
        //初始化并设定适配器与数据原
        initAndConfigListView(view);
        return view ;
    }
    private void initAndConfigListView(View view) {
        listView = view.findViewById(R.id.lv_order);
        OrderAdapter orderAdapter = new OrderAdapter(this.getContext(),R.layout.item_order, ProductUtils.orderList);
        listView.setAdapter(orderAdapter);
    }
}
