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

@WebServlet("/getshoppingcar")
public class ShoppingCarGetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Result<ArrayList<PerProductOrder>> result =null;
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userid");
        result = ShoppingCarService.getShoppingCarByUserId(Integer.parseInt(userId));
//        if (result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)) {//如果获取成功了才删原来的购物车信息，因为之后在购物车关闭的时候会重新上传购物车信息
//            if (!ShoppingCarService.deleteAllItemByUserId(Integer.parseInt(userId))) {//没山成功就一直删，成功为止
//                ShoppingCarService.deleteAllItemByUserId(Integer.parseInt(userId));
//            }
//        }
        out.print(JSONUtil.toJsonStr(result));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
