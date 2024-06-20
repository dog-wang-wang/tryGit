package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.service.ShoppingCarService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/uploadshoppingcar")
public class ShoppingCarItemUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Result<Boolean> result =null;
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userid");
        String productId = request.getParameter("productid");
        String num = request.getParameter("num");
        String sumPrice = request.getParameter("sumprice");
        result = ShoppingCarService.uploadItem(Integer.parseInt(userId),Integer.parseInt(productId),Integer.parseInt(num),Double.parseDouble(sumPrice));
        out.print(JSONUtil.toJsonStr(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
