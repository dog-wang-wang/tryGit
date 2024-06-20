package com.example.termend.utils;

import android.util.Log;

import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResultUtils {
    private static String phone;
    private static String password;
    public static UserInfo userInfo;
    public static final Integer RESULT_SUCCESS = 200;
    public static final Integer RESULT_ERROR = 400;

    //TODO:这里的目的是通过url获取到的输入流中获取resultJson
    public static String getResultJsonFromURLInputStream(InputStream is){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String resultJson =null;
        try {
            resultJson = bufferedReader.readLine();
        } catch (IOException e) {
            Log.i("termEnd","读取url返回的result失败");
            e.printStackTrace();
        }
        Log.i("termEnd","准备开始返回resultJson");
        return resultJson;
    }
    //TODO:这里是把resultJson处理成Result对象，用于登录
    public static Result<UserInfo> resultJsonToUserResult(String resultJson){
        Gson gson = new Gson();
        Log.i("termEnd","准备开始把resultJson转化成T是UserInfo的Result");
        return gson.fromJson(resultJson,new TypeToken<Result<UserInfo>>(){}.getType());
    }
    //TODO:这里是把resultJson处理成Result对象，返回的其实是个product集合，用于查询商品
    public static Result<ArrayList<Product>> resultJsonToProductArrayListResult(String resultJson){
        Gson gson = new Gson();
        Log.i("termEnd","准备开始把resultJson转化成T是ProductList的Result");
        return gson.fromJson(resultJson,new TypeToken<Result<ArrayList<Product>>>(){}.getType());
    }
    //TODO：这里把resultJson处理成为Long的Result
    public static Result<Long> resultJsonToLongResult(String resultJson){
        Gson gson = new Gson();
        Log.i("termEnd","准备开始把resultJson转化成T是Long的Result");
        return gson.fromJson(resultJson,new TypeToken<Result<Long>>(){}.getType());
    }
}
