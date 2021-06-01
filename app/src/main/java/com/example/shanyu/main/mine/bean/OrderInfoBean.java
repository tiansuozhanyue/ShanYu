package com.example.shanyu.main.mine.bean;

import java.io.Serializable;
import java.util.List;

public class OrderInfoBean implements Serializable {


    /**
     * id : 683
     * name : 陆上书店
     * title : 商品试卷
     * principal : 0.01
     * status : 5
     * coupon_id : 0
     * money : 0.00
     * created_at : 1622527176
     * addressid : 7
     * shop_id : 1
     * sum : 0.01
     * business : 09:40:19
     * rest : 23:30:12
     * address : 麓谷小镇
     * nickname : 虹猫
     * order : N2021060127176904504361
     * mobile : 18868026919
     * type : 1
     * goods_list : [{"goods_id":7,"title":"商品试卷","count":1,"price":"25.00","preevent":"0.01","covers":"uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg"}]
     */

    private int id;
    private String name;
    private String title;
    private String principal;
    private int status;
    private int coupon_id;
    private String money;
    private int created_at;
    private int addressid;
    private int shop_id;
    private String sum;
    private String business;
    private String rest;
    private String address;
    private String nickname;
    private String order;
    private String mobile;
    private int type;
    private List<GoodsListBean> goods_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean implements Serializable {
        /**
         * goods_id : 7
         * title : 商品试卷
         * count : 1
         * price : 25.00
         * preevent : 0.01
         * covers : uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg
         */

        private int goods_id;
        private String title;
        private int count;
        private String price;
        private String preevent;
        private String covers;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPreevent() {
            return preevent;
        }

        public void setPreevent(String preevent) {
            this.preevent = preevent;
        }

        public String getCovers() {
            return covers;
        }

        public void setCovers(String covers) {
            this.covers = covers;
        }
    }
}
