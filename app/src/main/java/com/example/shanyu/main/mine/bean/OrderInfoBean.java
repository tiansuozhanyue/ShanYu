package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class OrderInfoBean implements Serializable {
    /**
     * id : 542
     * name : 陆上书店
     * title : 四大原著著小学套餐
     * count : 3
     * preevent : 45.50
     * price : 58.00
     * principal : 136.50
     * status : 5
     * coupon_id : 0
     * money : 0.00
     * created_at : 1621241400
     * addressid : 7
     * shop_id : 1
     * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     * sum : 136.50
     * business : 09:40:19
     * rest : 23:30:12
     * address : 麓谷小镇
     * nickname : 虹猫
     * order : N2021051741400678833297
     * mobile : 18868026919
     * goods_id : 1
     */

    private Integer id;
    private String name;
    private String title;
    private Integer count;
    private String preevent;
    private String price;
    private String principal;
    private Integer status;
    private Integer coupon_id;
    private String money;
    private Integer created_at;
    private Integer addressid;
    private Integer shop_id;
    private String covers;
    private String sum;
    private String business;
    private String rest;
    private String address;
    private String nickname;
    private String order;
    private String mobile;
    private Integer goods_id;

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

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }
}
