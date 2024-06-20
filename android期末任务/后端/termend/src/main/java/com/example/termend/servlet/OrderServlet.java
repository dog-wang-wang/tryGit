package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.service.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/addorder")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        Result<Long> result = null;
        PrintWriter out = response.getWriter();
        String orderId =request.getParameter("orderid");
        String userId = request.getParameter("userid");
        Integer userIdInt = Integer.parseInt(userId);
        System.out.println("订单"+orderId+"由用户"+userId+"创建");
        //订单编号就是订单时间的long形式，所以生成一个时间
        result= OrderService.addOrder(orderId,userIdInt);
        System.out.println("订单添加情况："+JSONUtil.toJsonStr(result));
        out.print(JSONUtil.toJsonStr(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        Result<Long> result = null;
        PrintWriter out = response.getWriter();
        String orderId =request.getParameter("orderid");
        String price = request.getParameter("price");
        Double priceDouble = Double.parseDouble(price);

        System.out.println("订单编号"+orderId+"总价为"+price);
        //订单编号就是订单时间的long形式，所以生成一个时间
        result= OrderService.uploadPrice(orderId,priceDouble);
        System.out.println("订单添加情况："+JSONUtil.toJsonStr(result));
        out.print(JSONUtil.toJsonStr(result));
    }
}
