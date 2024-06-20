package com.example.termend.entity;

import com.example.termend.activity.ProductDetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class OrderEntity implements Serializable {
    private ArrayList<PerProductOrder> productMessagelist;
    private double sumPrice=0;
    private Date date;
    private String id ;

    public OrderEntity() {

    }
    public OrderEntity(ArrayList<PerProductOrder> productMessagelist) {
        for (PerProductOrder perProductOrder : productMessagelist) {
            this.sumPrice+=perProductOrder.getProductSumPrice();
        }
    }

    public OrderEntity(ArrayList<PerProductOrder> productMessagelist, Date date) {
        this.productMessagelist = productMessagelist;
        this.date = date;
        for (PerProductOrder perProductOrder : productMessagelist) {
            this.sumPrice+=perProductOrder.getProductSumPrice();
        }
    }

    public ArrayList<PerProductOrder> getProductMessagelist() {
        return productMessagelist;
    }

    public void setProductMessagelist(ArrayList<PerProductOrder> productMessagelist) {
        this.productMessagelist = productMessagelist;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
