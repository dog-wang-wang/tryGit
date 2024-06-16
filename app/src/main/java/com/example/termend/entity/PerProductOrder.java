package com.example.termend.entity;

import java.io.Serializable;

//这个类应用于订单当中和购物车当中，代表一种商品的数量，价格，以及商品本身的信息
public class PerProductOrder implements Serializable {
    private Product product;
    private int num;
    private double productSumPrice;
    public PerProductOrder() { }
    public PerProductOrder(Product product, int num) {
        this.product = product;
        this.num = num;
        this.productSumPrice = num*product.getPrice();
    }

    public double getProductSumPrice() {
        return productSumPrice;
    }

    public void setProductSumPrice(double productSumPrice) {
        this.productSumPrice = productSumPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
