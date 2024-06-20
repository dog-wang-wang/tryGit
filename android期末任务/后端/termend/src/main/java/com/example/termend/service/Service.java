package com.example.termend.service;

import com.example.termend.entity.Result;

public abstract class  Service {
    //TODO:这个是验证账号密码注册是否合理
    protected static String phoneAndPassword(String phone, String password) {
        if(phone==null||phone.equals("")){
            return "手机号为空";
        } else if (password==null||password.equals("")) {
            return "密码为空";
        }else {
            if (phone.length()!=11||phone.matches("")){
                System.out.println(phone);
                System.out.println(phone.length());
                return "手机号长度有误";
            }
            return "OK";
        }
    }
}
