package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class OrderBookBean implements Serializable {

    /**
     * goods_uid : 1
     * id : 537
     * name : 陆上书店
     * title : 四大原著著小学套餐
     * count : 1
     * preevent : 45.50
     * price : 58.00
     * principal : 45.50
     * status : 5
     * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     * pay_order : N2021051731456319999607
     * order : N2021051731456320006457
     * goods_id : 1
     * shop_id : 1
     */

    private Integer goods_uid;
    private Integer id;
    private String name;
    private String title;
    private Integer count;
    private String preevent;
    private String price;
    private String principal;
    private Integer status;
    private String covers;
    private String payOrder;
    private String order;
    private Integer goods_id;
    private Integer shop_id;

    public Integer getGoodsUid() {
        return goods_uid;
    }

    public void setGoodsUid(Integer goodsUid) {
        this.goods_uid = goodsUid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPreevent() {
        return preevent;
    }

    public void setPreevent(String preevent) {
        this.preevent = preevent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getGoodsId() {
        return goods_id;
    }

    public void setGoodsId(Integer goodsId) {
        this.goods_id = goodsId;
    }

    public Integer getShopId() {
        return shop_id;
    }

    public void setShopId(Integer shopId) {
        this.shop_id = shopId;
    }
}
