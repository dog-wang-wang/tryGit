package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Result;
import com.example.termend.service.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteorder")
public class DeleteOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String orderId = request.getParameter("orderid");
        Result<Boolean> result = OrderService.deleteOrderById(orderId);
        String resultJson = JSONUtil.toJsonStr(result);
        writer.print(resultJson);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
