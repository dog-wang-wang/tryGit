package com.example.homework_goodsmore.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework_goodsmore.R;
import com.example.homework_goodsmore.eneity.Product;

public class DetailsActivity extends AppCompatActivity {
    ActivityResultLauncher resultLauncher;
    private TextView tv_name;
    private TextView tv_price;
    private Button btn_buy;
    private ImageView img;
    private Product product;
    private TextView tv_buy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        product = (Product) getIntent().getSerializableExtra("productMessage");
        Log.i("message",product.getMessage());
        registerResult();

        initViews();
        initEvent();
        getViewsValues();
    }

    private void registerResult() {
    resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.i("message",product.getMessage());
            if (result.getData() != null&&result.getResultCode()==200) {
                tv_buy.setText(String.valueOf(result.getData().getIntExtra("number",0)));
            }
        }
    });
    }

    private void getViewsValues() {

        if(product!= null) {
            tv_name.setText(product.getName());
            tv_price.setText("￥"+product.getPrice());
            img.setImageResource(product.getImg());

        }

    }

    private void initEvent() {
        btn_buy.setOnClickListener(v->{
            Intent intent =  new Intent(DetailsActivity.this,ShoppingCarActivity.class);
            intent.putExtra("product",product);
            resultLauncher.launch(intent);
        });
    }

    private void initViews() {
        tv_name = findViewById(R.id.tv_detail_name);
        tv_price = findViewById(R.id.tv_detail_price);
        btn_buy = findViewById(R.id.btn_buy);
        img = findViewById(R.id.img_detail);
        tv_buy = findViewById(R.id.tv_buy);
    }
}