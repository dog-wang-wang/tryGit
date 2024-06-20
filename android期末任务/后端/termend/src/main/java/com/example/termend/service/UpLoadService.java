package com.example.termend.service;

import cn.hutool.crypto.digest.MD5;
import com.example.termend.dao.DbUserUtils;
import com.example.termend.dao.DbUtils;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ResultUtils;

import java.io.*;
import java.sql.SQLException;

public class UpLoadService {
    public static void upLoad(InputStream is, String path) throws IOException {
        OutputStream os = new FileOutputStream(path);
        // 一边读取一边写文件
        int len = -1;
        byte[] bytes = new byte[512];
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
            os.flush();
        }
        is.close();
        os.close();
    }
    public static Result<UserInfo>  updataUserMessageDao(String phone, String messageLabel, String message){
        Result<UserInfo> result =null;
        UserInfo userInfo = null;
        String set =null;
        try {
            set=DbUserUtils.update(phone,messageLabel,message);
            String sets[] = set.split("__");
            userInfo = new UserInfo();
            userInfo.setPhoneNumber(sets[1]);
            userInfo.setUserid(Integer.valueOf(sets[0]));
            userInfo.setIconAddress(sets[4]);
            userInfo.setEmail(sets[3]);
            userInfo.setData(sets[5]);
            userInfo.setName(sets[6]);
        } catch (SQLException e) {
                e.printStackTrace();
                result=ResultUtils.error("数据库查询有问题");
        }
        return result = ResultUtils.success("OK", userInfo);
    }
}
