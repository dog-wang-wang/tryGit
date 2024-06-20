package com.example.termend.service;

import cn.hutool.crypto.digest.MD5;
import com.example.termend.dao.DbUserUtils;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ResultUtils;

import java.sql.SQLException;

public class LoginService extends Service{
    public static Result<UserInfo> service(String phone ,String password){
        Result<UserInfo> result = new Result<>();
        String resultMessage=phoneAndPassword(phone,password);
        UserInfo userInfo=null;
        String set =null;
        if(resultMessage.equals("OK")){
            System.out.println("ok");
            //说明合规
            //然后检查是否存在手机号和密码是否正确
            try {
                set= DbUserUtils.selectUser(phone);
                if(set!=null){
                    String sets[] = set.split("__");
                    if(sets[2].equals(MD5.create().digestHex16(password))) {
                        userInfo = new UserInfo();
                        userInfo.setPhoneNumber(sets[1]);
                        userInfo.setUserid(Integer.valueOf(sets[0]));
                        userInfo.setIconAddress(sets[4]);
                        userInfo.setEmail(sets[3]);
                        userInfo.setData(sets[5]);
                        userInfo.setName(sets[6]);
                        return result = ResultUtils.success("OK", userInfo);
                    }
                    return result=ResultUtils.error("账号或密码有误");
                }
                    return result=ResultUtils.error("账号或密码有误");
            } catch (SQLException e) {
                e.printStackTrace();
                result=ResultUtils.error("数据库查询有问题");
            }
        }else {
            System.out.println("nook");
            result=ResultUtils.error(resultMessage);
        }
        return result;
    }
}
