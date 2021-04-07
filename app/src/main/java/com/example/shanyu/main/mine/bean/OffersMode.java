package com.example.shanyu.main.mine.bean;

public class OffersMode {

    /**
     * id : 1
     * uid : 3
     * money : 10
     * explain : 满15元可用
     * rule : 限客户端使用
     * business : 2021.04.01
     * rest : 2021.04.30
     * create_time : 1617246389
     * update_time : null
     */

    private Integer id;
    private Integer uid;
    private Integer money;
    private String explain;
    private String rule;
    private String business;
    private String rest;
    private Integer createTime;
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }
}
