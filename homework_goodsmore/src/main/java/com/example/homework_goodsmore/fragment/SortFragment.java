package com.example.homework_goodsmore.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.homework_goodsmore.R;
import com.example.homework_goodsmore.adapter.ProductAdapter;
import com.example.homework_goodsmore.eneity.Product;

import java.util.List;

public class SortFragment extends Fragment {
    private String sortText;//根据分类不同来设置不同的数据，写太多fragment太费劲了

    public SortFragment(String sortText) {
        this.sortText = sortText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("message","Create");

        View view = inflater.inflate(R.layout.sort_fragment,null);
        //获取gridView
        getAndSetGridView(view,sortText);
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.i("message","destroy");
        super.onDestroyView();
    }

    private void getAndSetGridView(View view,String kind) {
        GridView gridView = view.findViewById(R.id.gv_sort);
        List<Product> list =null;
        //获取数据gridview中存储的数据并绑定gridView对应的适配器
        if (kind.equals("推荐")){
            list= Product.getRecommendProducts();
        }else if (kind.equals("食品")) {
            list=Product.getFoodProduct();
        }else if(kind.equals("母婴")){
            list= Product.getBabyProduct();
        }
        ProductAdapter recommendAdapter = new ProductAdapter(list,R.layout.product_item,this.getContext());
        gridView.setAdapter(recommendAdapter);
    }
}
