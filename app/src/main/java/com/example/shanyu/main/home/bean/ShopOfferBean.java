package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class ShopOfferBean implements Serializable {

    /**
     * id : 3
     * explain : 满35元可用
     * rule : 限三合书店使用
     */

    private Integer id;
    private String explain;
    private String rule;
    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
