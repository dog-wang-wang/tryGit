package com.example.termend.entity;

import java.io.Serializable;

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
