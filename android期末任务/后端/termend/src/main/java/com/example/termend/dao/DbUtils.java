package com.example.termend.dao;

import com.example.termend.ConfigString;

import java.sql.*;

public class DbUtils {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getconnection(){
        try {
            return DriverManager.getConnection(ConfigString.databaseAddress, ConfigString.databaseUser, ConfigString.databasePassword);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void close(ResultSet re, PreparedStatement preparedStatement, Connection connection){
        try {
            if(re!=null){re.close();}
            if (preparedStatement!=null){preparedStatement.close();}
            if (connection!=null){connection.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close(PreparedStatement preparedStatement,Connection connection){
        try {
            if (preparedStatement!=null){preparedStatement.close();}
            if (connection!=null){connection.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
