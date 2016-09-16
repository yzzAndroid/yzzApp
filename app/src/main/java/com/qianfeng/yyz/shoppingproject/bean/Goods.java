package com.qianfeng.yyz.shoppingproject.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class Goods {

    private String father;


    private List<SonGoods> list;


    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public List<SonGoods> getList() {
        return list;
    }

    public void setList(List<SonGoods> list) {
        this.list = list;
    }
}
