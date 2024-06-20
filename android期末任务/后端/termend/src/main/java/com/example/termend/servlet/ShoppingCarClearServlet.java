package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Result;
import com.example.termend.service.ShoppingCarService;
import com.example.termend.utils.ResultUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/clearshoppingcar")
public class ShoppingCarClearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Result<ArrayList<PerProductOrder>> result =null;
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userid");
        if (!ShoppingCarService.deleteAllItemByUserId(Integer.parseInt(userId))) {//没山成功就一直删，成功为止
            ShoppingCarService.deleteAllItemByUserId(Integer.parseInt(userId));
        }
        out.print(JSONUtil.toJsonStr(ResultUtils.success("清空完成",true)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
