package com.example.termend.servlet;

import cn.hutool.json.JSONUtil;
import com.example.termend.ConfigString;
import com.example.termend.entity.Result;
import com.example.termend.service.UpLoadService;
import com.example.termend.utils.ResultUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/shangchuan")
public class UpLoadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        // 获取上传上来的图片
        InputStream is = request.getInputStream();
        // 获取携带的参数
        String phone = request.getParameter("phone");
        // 获取文件路径
        System.out.println(phone);
        String path = ConfigString.userIconAddress + phone + ".jpeg";
        // 获取本地文件输出流
        UpLoadService.upLoad(is,path);
        UpLoadService.updataUserMessageDao(phone,"icon",path);
        // 更新文本文件中的内容
        PrintWriter out = response.getWriter();
        Result<String> result = ResultUtils.success(ResultUtils.RESULT_SUCCESS,"头像上传成功", path);
        String resultJson = JSONUtil.toJsonStr(result);
        System.out.println(resultJson);
        out.write(resultJson);
        out.close();
    }
}
