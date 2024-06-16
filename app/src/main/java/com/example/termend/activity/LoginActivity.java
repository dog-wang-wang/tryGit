package com.example.termend.activity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termend.R;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.ResultUtils;
import com.example.termend.utils.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
public class LoginActivity extends AppCompatActivity {
    private EditText edt_login_phone;
    private EditText edt_login_password;
    private TextView tv_goto_register;
    private Button btn_login;
    ActivityResultLauncher launcher = null;
    private String phone;
    private String password;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initHandler();
        initResultLauncher();
        initViews();
        initEvents();

    }
    private void initHandler() {
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what==1){
                    Result<UserInfo> result = (Result<UserInfo>) msg.obj;
                    Log.i("termEnd","已经把登录结果交给了主线程");
                    Log.i("termEnd","登陆状态码："+result.getStatusCode());
                    if (result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)) {
                        //返回值正确
                        Log.i("termEnd","登陆成功");
                        Intent intent = new Intent(LoginActivity.this,MineActivity.class);
                        intent.putExtra("userInfo",result.getData());
                        //把用户信息添加过去，按理说我觉得该保存在本地，但是没学过
                        ResultUtils.userInfo = result.getData();
                        startActivity(intent);
                        finish();
                    }else {
                        Log.i("termEnd","登陆失败");
                        //返回值有误
                        Toast.makeText(LoginActivity.this,result.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }
    //TODO:在这里初始化结果注册器并重写回调方法
    private void initResultLauncher() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                UserInfo userInfo = (UserInfo) intent.getSerializableExtra("userInfo");
                edt_login_phone.setText(userInfo.getPhoneNumber());
                edt_login_password.setText(userInfo.getPassword());
                phone=edt_login_phone.getText().toString();
                password=edt_login_password.getText().toString();
            }
        });
    }

    //TODO:初始化控件
    private void initViews() {
        edt_login_phone = findViewById(R.id.edt_login_phone);
        edt_login_password = findViewById(R.id.edt_login_password);
        tv_goto_register = findViewById(R.id.tv_goto_register);
        btn_login = findViewById(R.id.btn_login);
    }

    //TODO:初始化事件
    private void initEvents() {
        //TODO:在这里给跳入注册界面绑定事件
        tv_goto_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            launcher.launch(intent);
        });
        //TODO:在这里给登录按钮绑定事件
        btn_login.setOnClickListener(v->{
            Log.i("termEnd","hk");
            System.out.println();
            phone=edt_login_phone.getText().toString();
            password=edt_login_password.getText().toString();
            if(edt_login_phone!=null&&edt_login_password!=null&&(!edt_login_phone.equals(""))&&(!edt_login_password.equals(""))) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();
            }else {
                Toast.makeText(LoginActivity.this,"账号或密码为空",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
        HttpURLConnection connection = null;
        InputStream inputStream =null;
        Log.i("termEnd",phone+"   "+password);
        try {
            URL url = new URL(URLUtil.PATH+"login?phone="+phone+"&password="+password);
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod(URLUtil.REQUEST_METHOD_POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            inputStream = connection.getInputStream();
            Log.i("termEnd",phone+"   "+password);
            //获取服务端响应
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(inputStream);
            Result<UserInfo> result = ResultUtils.resultJsonToUserResult(resultJson);





            //这里添加功能//如果登陆成功就直接获取购物车和订单信息
            if(result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)){
                ProductUtils.orderList = ProductUtils.getOrders(result.getData().getUserid());
                ProductUtils.shoppingCar = ProductUtils.getShoppingCar(result.getData().getUserid());
                //给shoppingCar的数据进行填充图片
                for (PerProductOrder perProductOrder : ProductUtils.shoppingCar) {
                    perProductOrder.getProduct().setBitmap(ProductUtils.getPic(perProductOrder.getProduct().getPicAddress()));
                }
            }





            //交付给主线程
            handler.sendMessage(handler.obtainMessage(1,result));
            Log.i("termEnd",String.valueOf(result.getStatusCode()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}