package com.example.termend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termend.R;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ResultUtils;
import com.example.termend.utils.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_register_phone;
    private EditText edt_register_password;
    private Button btn_register;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initEvents();
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Result<UserInfo> result = (Result<UserInfo>) msg.obj;
                //TODO:根据返回的result状态码来决定处理result的方式
                if (result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)) {//状态正常那么result中会含有data
                    UserInfo userInfo = result.getData();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("userInfo",userInfo);
                    Log.i("term","开始跳转");
                    setResult(ResultUtils.RESULT_SUCCESS,intent);
                    finish();
                }else {//状态不正常就只含有message
                    Toast.makeText(RegisterActivity.this,result.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    //TODO:初始化控件
    private void initViews() {
        edt_register_phone = findViewById(R.id.edt_register_phone);
        edt_register_password = findViewById(R.id.edt_register_password);
        btn_register = findViewById(R.id.btn_register);
    }

    //TODO:初始化事件
    private void initEvents() {
        btn_register.setOnClickListener(v -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    register();
                }
            }).start();
        });
    }

    //TODO:开始注册
    private void register() {
        HttpURLConnection urlConnection=null;
        InputStream inputStream = null;
        String phone = edt_register_phone.getText().toString();
        String password =edt_register_password.getText().toString();
        Log.i("termEnd",password);
        try {
            //TODO:发起url请求并且获取connection来设置请求方式，然后获取result
            URL url = new URL(URLUtil.PATH+"register?phone="+phone+"&password="+password);
            urlConnection =(HttpURLConnection)url.openConnection();
            Log.i("termEnd",url.getQuery());
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod(URLUtil.REQUEST_METHOD_POST);

            //打开输入流,输入流收到的结果是一个Result对象
            Log.i("termEnd","准备开始获取url返回的输入流");
            inputStream = urlConnection.getInputStream();

            //先从url的输入流中获取结果字符串再获取结果
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(inputStream);
            Log.i("termEnd",resultJson);
            Result<UserInfo> result = ResultUtils.resultJsonToUserResult(resultJson);
            //把结果交给主线程
            Message message = handler.obtainMessage(1,result);
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            Log.i("termEnd","url请求失败");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("termEnd","Connection请求失败");
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.i("termEnd","关闭url输入流失败");
                    e.printStackTrace();
                }
            }
        }
    }


}