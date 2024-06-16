package com.example.termend.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.termend.activity.MineActivity;
import com.example.termend.activity.ShoppingCarActivity;
import com.example.termend.entity.OrderEntity;
import com.example.termend.entity.PerProductOrder;
import com.example.termend.entity.Product;
import com.example.termend.entity.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class ProductUtils {
    public static ArrayList<OrderEntity> orderList = new ArrayList<>();
    public static ArrayList<PerProductOrder> shoppingCar = new ArrayList<>();
    //TODO：该方法用于登录之后获取所有商品的信息
    public static Result<ArrayList<Product>> getProductsBasicMessage() {
        URL url =null;
        InputStream is =null;
        Result<ArrayList<Product>> result = null;
        try {
            //发出get请求
            url= new URL(URLUtil.PATH+"getProducts");
            is = url.openStream();
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Log.i("termEnd",resultJson);
            result= ResultUtils.resultJsonToProductArrayListResult(resultJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //TODO：该方法用于进入详情页面之后第一次加载商品基础信息，也就是按id查找数据
    public static Result<ArrayList<Product>> getProductsBasicMessage(int id) {
        URL url =null;
        InputStream is =null;
        Result<ArrayList<Product>> result = null;
        try {
            //发出get请求
            url= new URL(URLUtil.PATH+"getProducts?productId="+id);
            is = url.openStream();
            Log.i("termEnd",url.getPath());
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Log.i("termEnd",resultJson);
            result= ResultUtils.resultJsonToProductArrayListResult(resultJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //TODO:该方法根据地址去后端拿取图片
    public static Bitmap getPic(String address) {
        URL url = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            url=new URL(URLUtil.PATH+"showProductPic?"+"picAddress="+address);
            Log.i("termEnd","获取地址为"+address+"的图片");
            is = url.openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }


    //TODO:此处传入进来的是购买的所有种类的商品的集合，以及上下文显示toast，这里只是完成购买并且生成一个order订单
    public static OrderEntity newOrder(ArrayList<PerProductOrder> allKindsOfProduct, Context context) {
        OrderEntity order =null;
        Date date = new Date();
        //根据时间生成订单编号
        long order_idLong = date.getTime();
        //开始去后端生成订单

        Result<Long> result = orderToJava(order_idLong);
        if (result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)) {
        //为了防止某些商品库存不足还要在这里重新定义一个商品集合
            ArrayList<PerProductOrder> successKindsOfProduct = new ArrayList<>();
            //遍历每一类商品并且添加到 订单生成成功集合中
            for (PerProductOrder perKindOfProduct : allKindsOfProduct) {
                //buy函数的作用就是提交要购买的某类商品的id,数量，该类产品价格，所属的id
                Result<ArrayList<Product>> resultFromBuyServlet = ProductUtils.buyProduct(perKindOfProduct.getProduct().getId(), perKindOfProduct.getNum(), perKindOfProduct.getProductSumPrice(), order_idLong);
                Log.i("termend", "添加" + perKindOfProduct.getProduct().getName() + ":" + perKindOfProduct.getNum() + "个,价格为：" + perKindOfProduct.getProductSumPrice());
                //要判定这一类商品是否添加成功，如果成功就记录下来进入成功商品队列
                if (resultFromBuyServlet.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)) {
                    successKindsOfProduct.add(perKindOfProduct);
                }
            }
            //如果刚才进行的购买操作当中所有的商品都购买失败就直接删除掉订单
            ProductUtils.deleteOrder(order_idLong);
            if(!successKindsOfProduct.isEmpty()) {
                order = new OrderEntity(successKindsOfProduct, date);
                order.setId(String.valueOf(result.getData()));
                uploadOrderSumPrice(order_idLong, order.getSumPrice());
                Log.i("termend", "订单时间：" + order.getDate() + "订单总价" + order.getSumPrice() + "订单编号：" + order.getId());
            }
        }
        return order;
    }
    //TODO:这个方法是在订单创建后如果商品全部售罄无法购买那么订单就会是空订单所以要删除
    private static void deleteOrder(long orderIdLong) {
        Log.i("termEnd","开始删除订单");
        InputStream is = null;
        String path = URLUtil.PATH + "deleteorder?orderid="+orderIdLong;
        Result<Long> result = null;
        try {
            URL url = new URL(path);
            is = url.openStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("termEnd","获取url出问题");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("termEnd","获取url返回流出问题");
        }finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //TODO:这个方法的作用是提交一个订单到后端但是
    public static Result<Long> orderToJava(Long orderid){
        InputStream is = null;
        String path = URLUtil.PATH + "addorder?orderid="+orderid+"&userid="+ResultUtils.userInfo.getUserid();
        Result<Long> result = null;
        try {
            URL url = new URL(path);
            is = url.openStream();
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            result = ResultUtils.resultJsonToLongResult(resultJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("termEnd","获取url出问题");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("termEnd","获取url返回流出问题");
        }finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }
    //TODO:这个是在完成订单生成和购买之后提交的总价信息
    public static void uploadOrderSumPrice(long orderid,double price){
        String path = URLUtil.PATH + "addorder?orderid="+orderid+"&price="+price;
        InputStream is = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //TODO:这个的作用是购买一类商品：功能是去后端数据库修改库存
    public static Result<ArrayList<Product>> buyProduct(int id, int buyNum,double sumPrice,Long order_id){
        URL url =null;
        InputStream is =null;
        Result<ArrayList<Product>> result = null;
        try {
            //发出get请求
            url= new URL(URLUtil.PATH+"buyProducts?productId="+id+"&productNum="+buyNum+"&productSumPrice="+sumPrice+"&productOrderId="+order_id);
            is = url.openStream();
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Log.i("termEnd",resultJson);
            result= ResultUtils.resultJsonToProductArrayListResult(resultJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    //TODO：这个是上传头像的
    public static void uploadAvatar(Uri uri, String phone, Context context) throws IOException{
        String path = URLUtil.PATH + "shangchuan?phone="+phone;
        InputStream is = null;
        OutputStream os = null;
        InputStream resultIs =null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // 通过Uri获取图片的流(输入流)
            is = context.getContentResolver().openInputStream(uri);
            // 获取输入流,一边读一边写
            os = conn.getOutputStream();
            Log.i("termEnd",uri.toString());
            int len = -1;
            byte[] bytes = new byte[512];
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
            conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                is.close();
            }
            if (os!=null) {
                os.close();
            }
        }
    }
    //TODO:这个是获取订单列表的
    public static ArrayList<OrderEntity> getOrders(int userid) {
        String path = URLUtil.PATH + "getorders?userid="+userid;
        InputStream is = null;
        String resultJson = null;
        ArrayList<OrderEntity> orderEntities = new ArrayList<>();
        try {
            URL url = new URL(path);
            is = url.openStream();
            resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Gson gson = new Gson();
            Result<ArrayList<OrderEntity>> result = gson.fromJson(resultJson,new TypeToken<Result<ArrayList<OrderEntity>>>(){});
            if(result.getStatusCode()==200) {
                for (OrderEntity datum : result.getData()) {
                    datum.setDate(new Date(Long.parseLong(datum.getId())));
                }
                orderEntities=result.getData();
            }else {
                Log.i("termEnd",result.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return orderEntities;
    }

    public static Result uploadShoppingCarItem(Integer userid, int id, int num,double sumPrice) {
        String path = URLUtil.PATH + "uploadshoppingcar?userid="+userid+"&productid="+id+"&num="+num+"&sumprice="+sumPrice;
        InputStream is = null;
        String resultJson = null;
        Result result=null;
        try {
            URL url = new URL(path);
            is = url.openStream();
            resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Gson gson = new Gson();
            result = gson.fromJson(resultJson,new TypeToken<Result>(){});
            if(result.getStatusCode().equals(ResultUtils.RESULT_ERROR)) {
                Log.i("termEnd","用户购物车中商品id为"+id+"的商品上传失败");
            }else {
                Log.i("termEnd","用户购物车中商品id为"+id+"的商品上传成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static Result<Boolean> clearShoppingCar(Integer userid) {
        String path = URLUtil.PATH + "clearshoppingcar?userid="+userid;
        InputStream is = null;
        String resultJson = null;
        Result<Boolean> result = null;
        try {
            URL url = new URL(path);
            is = url.openStream();
            resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Gson gson = new Gson();
            Log.i("termEnd",resultJson);
            result= gson.fromJson(resultJson,new TypeToken<Result<Boolean>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static ArrayList<PerProductOrder> getShoppingCar(Integer userid) {
        String path = URLUtil.PATH + "getshoppingcar?userid="+userid;
        InputStream is = null;
        String resultJson = null;
        ArrayList<PerProductOrder> shoppingCar=new ArrayList<>();
        try {
            URL url = new URL(path);
            is = url.openStream();
            resultJson = ResultUtils.getResultJsonFromURLInputStream(is);
            Gson gson = new Gson();
            Log.i("termEnd",resultJson);
            Result<ArrayList<PerProductOrder>> result = gson.fromJson(resultJson,new TypeToken<Result<ArrayList<PerProductOrder>>>(){});
            if(result.getStatusCode().equals(ResultUtils.RESULT_SUCCESS)){
                shoppingCar = result.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            shoppingCar =new ArrayList<>();
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return shoppingCar;
    }
}
