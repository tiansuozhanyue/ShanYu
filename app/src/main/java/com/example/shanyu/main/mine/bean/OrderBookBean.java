package com.example.shanyu.main.mine.bean;

import java.io.Serializable;
import java.util.List;

public class OrderBookBean implements Serializable {

    /**
     * goods_uid : 1
     * id : 650
     * name : 陆上书店
     * title : 三国演义原著
     * count : 3
     * preevent : 80.50
     * price : 173.00
     * principal : 150.50
     * status : 5
     * covers : uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg
     * pay_order : N2021053141368911778672
     * order : N2021053141368920307471
     * goods_id : 2,1
     * shop_id : 1
     * type : 1
     * goods_list : [{"id":1,"title":"四大原著著小学套餐","price":"58.00","preevent":"45.50","covers":"uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg"},{"id":2,"title":"三国演义原著","price":"115.00","preevent":"35.00","covers":"uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg"}]
     */

    private int goods_uid;
    private int id;
    private String name;
    private String title;
    private int count;
    private String preevent;
    private String price;
    private String principal;
    private int status;
    private String covers;
    private String pay_order;
    private String order;
    private String goods_id;
    private int shop_id;
    private int type;
    private List<GoodsListBean> goods_list;
    private String numbers;
    private String logistics;
    private String loginame;

    public String getLoginame() {
        return loginame;
    }

    public void setLoginame(String loginame) {
        this.loginame = loginame;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public int getGoods_uid() {
        return goods_uid;
    }

    public void setGoods_uid(int goods_uid) {
        this.goods_uid = goods_uid;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getPay_order() {
        return pay_order;
    }

    public void setPay_order(String pay_order) {
        this.pay_order = pay_order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
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
         * id : 1
         * title : 四大原著著小学套餐
         * price : 58.00
         * preevent : 45.50
         * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
         */

        private int id;
        private int count;
        private String title;
        private String price;
        private String preevent;
        private String covers;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
