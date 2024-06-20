package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.service.BuyProductService;
import com.example.termend.utils.ResultUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/buyProducts")
public class BuyProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Result<ArrayList<Product>>  result=null;
        String productId =request.getParameter("productId");
        String parmNum = request.getParameter("productNum");
        String sum_price = request.getParameter("productSumPrice");
        String order_id = request.getParameter("productOrderId");
        //开始业务
        System.out.println("开始购买"+"订单号为"+order_id);
        result  = BuyProductService.buy(Integer.valueOf(productId),Integer.valueOf(parmNum));
        if(result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)){
            BuyProductService.addKindProduct(Integer.parseInt(productId),order_id,Integer.parseInt(parmNum),Double.parseDouble(sum_price));
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
