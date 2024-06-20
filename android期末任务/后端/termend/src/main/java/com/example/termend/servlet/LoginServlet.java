package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        String phone =request.getParameter("phone");
        String password = request.getParameter("password");
        System.out.println("用户"+phone+"已登录");
        //开始登陆业务


        result = LoginService.service(phone,password);
        String resultJson = JSONUtil.toJsonStr(result);
        System.out.println("resultJson:"+resultJson);

        //返回信息
        out.print(resultJson);
        out.close();
    }
}
