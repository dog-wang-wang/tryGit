package com.example.termend.service;

import com.example.termend.dao.DbProductUtils;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.Result;
import com.example.termend.utils.ResultUtils;

import java.sql.Date;
import java.util.ArrayList;

public class OrderService {
    public static Result<Long> addOrder(String orderId,int userId){
        Long dateLong = Long.valueOf(orderId);
        Date date = new Date(dateLong);
        boolean addResult= DbProductUtils.addOrderItem(orderId,userId,date);
        //添加成功就直接显示成功订单的编号传递回去
        if (addResult) {
            return ResultUtils.success("成功添加订单",dateLong);
        }else {
            return ResultUtils.error("添加订单在数据库操作部分有误");
        }
    }

    public static Result<Long> uploadPrice(String orderId, Double priceDouble) {
        Long dateLong = Long.valueOf(orderId);
        boolean upLoadResult=DbProductUtils.upLoadOrderPrice(orderId,priceDouble);
        if (upLoadResult) {
            return ResultUtils.success("成功添加订单价格",dateLong);
        }else {
            return ResultUtils.error("添加订单价格在数据库操作部分有误");
        }
    }
    public static Result<ArrayList<OrderEntity>> getAllOrdersByUserId(int userId){
        ArrayList<OrderEntity> orderEntities = DbProductUtils.getOrders(userId);
        if (orderEntities.isEmpty()){
            return ResultUtils.error("该用户没有订单");
        }else {
            return ResultUtils.success("用户订单获取成功",orderEntities);
        }
    }
    public static Result<Boolean> deleteOrderById(String orderId){
        if (DbProductUtils.deleteOrderItem(orderId)){
            return ResultUtils.success("用户订单删除成功",true);
        }else {
            return ResultUtils.error("订单删除失败");
        }
    }
}
