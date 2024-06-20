package com.example.termend.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
@WebServlet("/showProductPic")
public class ShowPicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String picAddress = request.getParameter("picAddress");
        System.out.println(picAddress);
        InputStream is = new FileInputStream(picAddress);
        OutputStream os = response.getOutputStream();
        // 一边读一边写
        int len = -1;
        byte[] bytes = new byte[512];
        while((len = is.read(bytes)) != -1) {
            System.out.print(bytes);
            os.write(bytes, 0, len);
            os.flush();
        }
        is.close();
        os.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
