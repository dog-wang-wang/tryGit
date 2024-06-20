package com.example.termend.dao;

import cn.hutool.db.Db;
import com.example.termend.entity.UserInfo;

import java.sql.*;

public class DbUserUtils {
    public static void insert_registerToUserMessage(String value1,String value2){
        Connection connection = DbUtils.getconnection();
        PreparedStatement preparedStatement =null;
        String sql ="insert into usermessage(phone,passwordmd5) values(?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,value1);
            preparedStatement.setString(2,value2);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(preparedStatement,connection);
        }
    }
    public static String selectUser(String phone) throws SQLException {
        Connection connection=DbUtils.getconnection();
        PreparedStatement preparedStatement =null;
        ResultSet set=null;
        String sql="select * from usermessage where phone=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,phone);
            set= preparedStatement.executeQuery();
            while (set.next()) {
                String sets = String.format("%d__%s__%s__%s__%s__%s__%s",set.getInt(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5),set.getString(6),set.getString(7));
                return sets;
            }
            return null;
        }finally {
            DbUtils.close(set,preparedStatement,connection);
        }
    }
    public static boolean selectUserExit(String phone){
        String set = null;
        try {
            set=selectUser(phone);
            if (set!=null){
                return true;//不等于null就是存在
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;//报错了也不能让程序继续运行，所以要返回true
        }
    }
    public static String update(String phone, String messageLabel, String message) throws SQLException {
        Connection connection = DbUtils.getconnection();
        String sql = String.format("update usermessage set %s =? where phone = ?",messageLabel);
        PreparedStatement statement = null;
        try {
            statement= connection.prepareStatement(sql);
            statement.setString(1,message);
            statement.setString(2,phone);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(statement,connection);
        }
        return selectUser(phone);
    }

}
