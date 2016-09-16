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

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public Collects(double money, String name, int num, String path, double price, String pUrl) {
        this.money = money;
        this.name = name;
        this.num = num;
        this.path = path;
        this.price = price;
        this.pUrl = pUrl;
    }

    public Collects() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collects collects = (Collects) o;

        if (Double.compare(collects.price, price) != 0) return false;
        if (num != collects.num) return false;
        if (Double.compare(collects.money, money) != 0) return false;
        if (name != null ? !name.equals(collects.name) : collects.name != null) return false;
        if (pUrl != null ? !pUrl.equals(collects.pUrl) : collects.pUrl != null) return false;
        return path != null ? path.equals(collects.path) : collects.path == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + num;
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pUrl != null ? pUrl.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Collects{" +
                "money=" + money +
                ", price=" + price +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", pUrl='" + pUrl + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
