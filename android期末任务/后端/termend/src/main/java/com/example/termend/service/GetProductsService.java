package com.example.termend.service;

import com.example.termend.dao.DbProductUtils;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.utils.ResultUtils;

import java.util.ArrayList;

public class GetProductsService {
    public static Result<ArrayList<Product>> getAllProductService(){
        Result<ArrayList<Product>> result =null;
        ArrayList<Product> products = DbProductUtils.selectAllProduct();
        if (products!=null){//
            result = ResultUtils.success(ResultUtils.RESULT_SUCCESS,"数据获取成功",products);
        }else {
            result = ResultUtils.error("获取数据时数据库请求失败");
        }
        return result;
    }

    public static Result<ArrayList<Product>> getProductById(int productId) {
        Result<ArrayList<Product>> result =null;
        ArrayList<Product> products = DbProductUtils.selectProductById(productId);
        if (products!=null){//
            result = ResultUtils.success(ResultUtils.RESULT_SUCCESS,"数据获取成功",products);
        }else {
            result = ResultUtils.error("获取数据时数据库请求失败");
        }
        return result;
    }
}
