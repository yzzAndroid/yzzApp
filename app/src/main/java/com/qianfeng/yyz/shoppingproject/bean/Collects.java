package com.qianfeng.yyz.shoppingproject.bean;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class Collects {

    private double price;

    private int num;

    private double money;

    private String name;
    private String pUrl;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public Collects(double money, String name, int num, double price, String pUrl) {
        this.money = money;
        this.name = name;
        this.num = num;
        this.price = price;
        this.pUrl = pUrl;
    }

    public Collects() {
    }
}
