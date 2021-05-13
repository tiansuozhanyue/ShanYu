package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class OffersMode implements Serializable {


    /**
     * id : 5
     * uid : 47
     * shop_id : 3
     * money : 25
     * maximum : 35
     * explain : 满35元可用
     * status : 0
     * rule : 限三合书店使用
     * business : 1618580784
     * rest : 1619790384
     * create_time : 1617239167
     * update_time : null
     */

    private Integer id;
    private Integer uid;
    private Integer shop_id;
    private Integer money;
    private Integer maximum;
    private String explain;
    private Integer status;
    private String rule;
    private String business;
    private String rest;
    private Integer create_time;
    private Object updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public Integer getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Integer createTime) {
        this.create_time = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
