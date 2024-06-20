package com.example.termend.service;

import cn.hutool.db.Db;
import com.example.termend.dao.DbProductUtils;
import com.example.termend.dao.DbUtils;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Result;
import com.example.termend.utils.ResultUtils;

import java.util.ArrayList;

public class ShoppingCarService {

    public static Result<Boolean> uploadItem(int userId, int productId, int num, double sumPrice) {
        boolean success= DbProductUtils.uploadShoppingCarItem(userId,productId,num,sumPrice);
        if (success) {
            return ResultUtils.success("成功添加购物车条目",success);
        }else {
            return ResultUtils.error("更新购物车条目失败");
        }
    }
    public static boolean deleteAllItemByUserId(int userId){
        return DbProductUtils.deleteFromShoppingCarById(userId);
    }
    public static Result<ArrayList<PerProductOrder>> getShoppingCarByUserId(int userId){
        ArrayList<PerProductOrder> shoppingCar =  DbProductUtils.selectAllFromShoppingCarById(userId);
        if(shoppingCar==null|| shoppingCar.isEmpty()){
            return ResultUtils.error("获取失败或购物车没有商品");
        }else {
            return ResultUtils.success("成功",shoppingCar);
        }
    }
}
