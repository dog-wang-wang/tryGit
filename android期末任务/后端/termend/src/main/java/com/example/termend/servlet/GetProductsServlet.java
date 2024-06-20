package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.service.GetProductsService;
import com.example.termend.utils.ResultUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
@WebServlet("/getProducts")
public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String id = request.getParameter("productId");
        //开始业务
        Result<ArrayList<Product>> result =null;
        if (id==null) {
            System.out.println("查询全部");
             result= GetProductsService.getAllProductService();
        }else if (id!=null) {
            System.out.println("按id查询");
            result = GetProductsService.getProductById(Integer.valueOf(id));
        }
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
