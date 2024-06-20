package com.example.termend.service;

import cn.hutool.crypto.digest.MD5;
import com.example.termend.dao.DbUserUtils;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ResultUtils;

public class RegisterService extends Service{
    public static Result service(String phone,String password){
        String resultMessage =null;
        String passwordmd5 = null;
        Result<UserInfo> result = null;
        UserInfo userInfo=null;
        //TODO:phone和password合理性验证
        resultMessage = phoneAndPassword(phone,password);
        if (resultMessage.equals("OK")){
            //TODO:一旦密码合规判断是否已经注册
            if (!DbUserUtils.selectUserExit(phone)) {
                //TODO:没有注册了就在这里加入数据
                passwordmd5=MD5.create().digestHex16(password);//创建出来passwordmd5
                DbUserUtils.insert_registerToUserMessage(phone,passwordmd5);//存入进去

                userInfo=new UserInfo();
                userInfo.setPassword(password);
                userInfo.setPhoneNumber(phone);
                //最后要把UserInfo返回
                result=ResultUtils.success(resultMessage,userInfo);
            }else {
                //TODO:注册了就在这里返回错误信息
                result=ResultUtils.error("该账号已注册");
            }
        }else {
            result=ResultUtils.error(resultMessage);
        }
        return result;
    }
}
