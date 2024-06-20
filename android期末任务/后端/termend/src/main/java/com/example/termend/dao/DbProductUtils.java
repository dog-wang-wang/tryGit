package com.example.termend.dao;

import cn.hutool.json.JSONUtil;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.example.termend.utils.ResultUtils;
import com.mysql.cj.jdbc.NClob;

import java.sql.*;
import java.util.ArrayList;

public class DbProductUtils {
    public static ArrayList<Product> selectAllProduct(){
        Connection connection = DbUtils.getconnection();
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        ArrayList<Product> list = new ArrayList<>();
        String sql ="select * from product";
        try {
            preparedStatement = connection.prepareStatement(sql);
            set = preparedStatement.executeQuery();
            while(set.next()){
                Product product  = new Product();
                product.setId(set.getInt(1));
                product.setName(set.getString(2));
                product.setPicAddress(set.getString(3));
                product.setPrice(set.getDouble(4));
                product.setInventory(set.getInt(5));
                list.add(product);
                //System.out.println(product.getId()+" "+product.getName()+" "+product.getPrice()+" "+product.getInventory());
            }
        } catch (SQLException e) {
            e.printStackTrace();//如果报错就会么有数据
            return null;
        }finally {
            DbUtils.close(set,preparedStatement,connection);
        }
        return list;
    }

    public static ArrayList<Product> selectProductById(int productId) {
        Connection connection = DbUtils.getconnection();
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        ArrayList<Product> list = new ArrayList<>();
        String sql ="select * from product where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,productId);
            set = preparedStatement.executeQuery();
            while(set.next()){
                Product product  = new Product();
                product.setId(set.getInt(1));
                product.setName(set.getString(2));
                product.setPicAddress(set.getString(3));
                product.setPrice(set.getDouble(4));
                product.setInventory(set.getInt(5));
                list.add(product);
                System.out.println(product.getId()+" "+product.getName()+" "+product.getPrice()+" "+product.getInventory());
            }
        } catch (SQLException e) {
            e.printStackTrace();//如果报错就会么有数据
            return null;
        }finally {
            DbUtils.close(set,preparedStatement,connection);
        }
        return list;
    }
    public static int updateInventory(int productId,int parm) {
        int status = ResultUtils.RESULT_ERROR;
        Connection connection = DbUtils.getconnection();
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        ArrayList<Product> list = new ArrayList<>();
        String sql ="update product set inventory = ? where id = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,parm);
            preparedStatement.setInt(2,productId);
            preparedStatement.execute();
            status = ResultUtils.RESULT_SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();//如果报错就会么有数据
            status =ResultUtils.RESULT_ERROR;
        }finally {
            DbUtils.close(set,preparedStatement,connection);
        }
        return status;
    }
    public static boolean addOrderItem(String orderId, int userId,Date date){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "insert into orders(order_id,users_id,create_time) values(?,?,?)";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setString(1,orderId);
            preparedStatement.setInt(2,userId);
            preparedStatement.setDate(3,date);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }
    public static boolean deleteOrderItem(String orderId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "delete from orders where order_id=?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setString(1,orderId);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }
    public static boolean addPerProduct(int productId ,String orderId, int buyNum,double sumprice){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "insert into buy_kind_product(product_id,order_id,buy_num,sum_price) values(?,?,?,?)";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setInt(1,productId);
            preparedStatement.setString(2,orderId);
            preparedStatement.setInt(3,buyNum);
            preparedStatement.setDouble(4,sumprice);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }

    public static boolean upLoadOrderPrice(String orderId, Double priceDouble) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "update orders set sum_price = ? where order_id = ?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setDouble(1,priceDouble);
            preparedStatement.setString(2,orderId);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }
    public static ArrayList<OrderEntity> getOrders(int userId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<OrderEntity> orders = new ArrayList<>();
        String sql= "select * from orders where users_id = ?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String orderId = resultSet.getString(1);
                OrderEntity orderEntity = new OrderEntity(getOrder(orderId));
                orderEntity.setId(orderId);
                orders.add(orderEntity);
                System.out.println("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(resultSet,preparedStatement,connection);
        }
        System.out.println("用户"+userId+"订单列表返回成功："+JSONUtil.toJsonStr(orders));
        return orders;
    }
    public static ArrayList<PerProductOrder> getOrder(String orderId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<PerProductOrder> perProductOrders = new ArrayList<>();
        String sql= "select * from buy_kind_product as b join product as p on b.product_id=p.id where b.order_id = ?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setString(1,orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(5));
                product.setName(resultSet.getString(6));
                product.setPicAddress(resultSet.getString(7));
                product.setPrice(resultSet.getDouble(8));
                product.setInventory(resultSet.getInt(9));
                PerProductOrder perProductOrder = new PerProductOrder(product,resultSet.getInt(3));
                perProductOrders.add(perProductOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.close(resultSet,preparedStatement,connection);
        }
        System.out.println("订单"+orderId+"商品类列表返回成功："+JSONUtil.toJsonStr(perProductOrders));
        return perProductOrders;
    }

    public static boolean uploadShoppingCarItem(int userId,int productId,int num,double price){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "insert into shopping_car values (?,?,?,?)";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.setInt(3,num);
            preparedStatement.setDouble(4,price);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }

    public static boolean deleteFromShoppingCarById(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql= "delete from shopping_car where userid=?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(preparedStatement,connection);
        }
    }
    public static ArrayList<PerProductOrder> selectAllFromShoppingCarById(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        ArrayList<PerProductOrder> shoppingCar= null;
        String sql= "select * from shopping_car as s join product as p on s.productid=p.id where userid=?";
        connection = DbUtils.getconnection();
        try {
            preparedStatement=  connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            set=preparedStatement.executeQuery();
            shoppingCar = new ArrayList<>();
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt(5));
                product.setName(set.getString(6));
                product.setPicAddress(set.getString(7));
                product.setPrice(set.getDouble(8));
                product.setInventory(set.getInt(9));
                PerProductOrder perProductOrder = new PerProductOrder(product,set.getInt(3));
                shoppingCar.add(perProductOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            DbUtils.close(set,preparedStatement,connection);
        }
        return shoppingCar;
    }
}
