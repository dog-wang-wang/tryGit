package com.example.termend.servlet;
import cn.hutool.json.JSONUtil;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.service.UpLoadService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
@WebServlet("/alter")
public class AlterMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        Result<UserInfo> result = null;
        PrintWriter printWriter = response.getWriter();
        //获取参数
        String phone = request.getParameter("phone");
        String messageLabel = request.getParameter("label");
        String message = request.getParameter("para");

        //处理业务

        result = UpLoadService.updataUserMessageDao(phone,messageLabel,message);

        //返回结果
        String resultJson = JSONUtil.toJsonStr(result);
        System.out.println("resultJson:"+resultJson);
        printWriter.write(resultJson);
        printWriter.close();
    }
}
