package com.example.homework_goodsmore.eneity;



import com.example.homework_goodsmore.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Product implements Serializable {
    public static List<Product> shoppingCar = new ArrayList<>();
    public static HashMap<Integer,Integer> shoppGoodsNumber = new HashMap();
    private int img;
    private String name;
    private String message;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Product(int id ,int img, String name, String message, double price) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.message = message;
        this.price = price;
    }
    public static List<Product> getRecommendProducts(){
        List<Product> productList = new ArrayList();
        productList.add(new Product(1, R.drawable.huawei,"华为","大家好，我是渣渣辉", 13799));
        productList.add(new Product( 2,R.drawable.vivo,"vivo","爆率真的很高，还送VIP", 6299));
        productList.add(new Product( 3,R.drawable.honor,"荣耀","名声在外，有好有坏", 6399));
        productList.add(new Product( 4,R.drawable.iphone,"iphone","以前是以前，现在是变态", 9999));
        productList.add(new Product( 5,R.drawable.xiaomi,"小米su7","退货包运费，一刀九九九", 1999));
        productList.add(new Product(6, R.drawable.oppo,"oppp","菜就多练", 2222));
        productList.add(new Product(7,R.drawable.babytwo,"真~奶粉2","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(8,R.drawable.babythree,"真~奶粉3","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(9,R.drawable.babyfour,"真~奶粉4","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(10,R.drawable.babyfive,"真~奶粉5","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(11,R.drawable.babysix,"真~奶粉6","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(12,R.drawable.babyseven,"真~奶粉7","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        return  productList;
    }
    public static List<Product> getBabyProduct(){
        List<Product> productList = new ArrayList();
        productList.add(new Product(13, R.drawable.babyone,"真~奶粉1","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(14, R.drawable.babytwo,"真~奶粉2","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(15, R.drawable.babythree,"真~奶粉3","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(16,R.drawable.babyfour,"真~奶粉4","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(17,R.drawable.babyfive,"真~奶粉5","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(18,R.drawable.babysix,"真~奶粉6","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(19,R.drawable.babyseven,"真~奶粉7","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(20,R.drawable.babyeight,"真~奶粉8","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        productList.add(new Product(21,R.drawable.babynine,"真~奶粉9","厂家倒闭，清仓大促销，十块钱交个朋友",10));
        productList.add(new Product(22,R.drawable.babyten,"真~奶粉10","厂家倒闭，清仓大促销，九块九块钱交个朋友",9.9));
        return  productList;

    };
    public static List<Product> getFoodProduct(){
        List<Product> productList = new ArrayList();
        productList.add(new Product(23,R.drawable.foodone,"水果1","零食大促销，全场每斤1块",1));
        productList.add(new Product(24,R.drawable.foodtwo,"水果2","零食大促销，全场每斤1块",1));
        productList.add(new Product(25,R.drawable.foodthree,"水果3","零食大促销，全场每斤1块",1));
        productList.add(new Product(26,R.drawable.foodfour,"水果4","零食大促销，全场每斤1块",1));
        productList.add(new Product(27,R.drawable.foodfive,"水果5","零食大促销，全场每斤1块",1));
        productList.add(new Product(28,R.drawable.foodsix,"水果6","零食大促销，全场每斤1块",1));
        productList.add(new Product(29,R.drawable.foodseven,"水果7","零食大促销，全场每斤1块",1));
        productList.add(new Product(30,R.drawable.foodeight,"水果8","零食大促销，全场每斤1块",1));
        productList.add(new Product(31,R.drawable.foodnine,"水果9","零食大促销，全场每斤1块",1));
        productList.add(new Product(32,R.drawable.foodten,"水果10","零食大促销，全场每斤1块",1));
        return  productList;
    };
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
