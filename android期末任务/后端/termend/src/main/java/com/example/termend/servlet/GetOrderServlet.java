package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.service.GetProductsService;
import com.example.termend.service.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/getorders")
public class GetOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("userid");
        //开始业务
        Result<ArrayList<OrderEntity>> result =null;
        int userid = Integer.parseInt(id);
        result = OrderService.getAllOrdersByUserId(userid);
        String resultJson = JSONUtil.toJsonStr(result);
        System.out.println(resultJson);
        //返回信息
        writer.print(resultJson);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
