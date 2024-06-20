package com.example.termend.servlet;

import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.service.RegisterService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import cn.hutool.json.JSONUtil;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Result<UserInfo> result =null;
        PrintWriter out = response.getWriter();

        //TODO:获取参数
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        System.out.println(phone+password);
        //TODO:开始register服务
        result=RegisterService.service(phone,password);
        System.out.println(JSONUtil.toJsonStr(result));
        //TODO:返回result
        out.print(JSONUtil.toJsonStr(result));
        out.close();
    }
}
