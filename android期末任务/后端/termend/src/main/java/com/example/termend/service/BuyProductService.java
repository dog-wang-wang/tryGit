package com.example.termend.service;

import com.example.termend.dao.DbProductUtils;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.utils.ResultUtils;

import java.util.ArrayList;

public class BuyProductService {
    public static Result<ArrayList<Product>> buy(int productId,int parm){
        ArrayList<Product>  products = DbProductUtils.selectProductById(productId);
        Result result = null;
        int newNum = products.get(0).getInventory()-parm;
        if(newNum>=0) {
            products.get(0).setInventory(newNum);
            int status = DbProductUtils.updateInventory(productId, newNum);
            if (status == ResultUtils.RESULT_SUCCESS) {//
                result = ResultUtils.success(ResultUtils.RESULT_SUCCESS, "数据更新成功", products);
            } else {
                result = ResultUtils.error("获取数据时数据库请求失败");
            }
        }else{
            result=ResultUtils.error("库存不足");
        }
        return result;
    }
    public static void addKindProduct(int productId,String orderId,int buyNum,double sumPrice){
        DbProductUtils.addPerProduct(productId,orderId,buyNum,sumPrice);
    }
}
