package com.example.shanyu.main.mine.bean;


import java.io.Serializable;
import java.util.List;

public class MyBooksMode implements Serializable {

    /**
     * shop_id : 1
     * name : 陆上书店
     * list : [{"id":240,"goods_id":2,"name":"陆上书店","shop_id":1,"isselected":0,"count":1,"preevent":"35.00","discount":"35.00","money":"115.00","price":"115.00","path":"uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg","title":"三国演义原著"}]
     */

    private Integer shopId;
    private String name;
    private List<ListDTO> list;

    public MyBooksMode(Integer shopId, String name, List<ListDTO> list) {
        this.shopId = shopId;
        this.name = name;
        this.list = list;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO implements Serializable {
        /**
         * id : 240
         * goods_id : 2
         * name : 陆上书店
         * shop_id : 1
         * isselected : 0
         * count : 1
         * preevent : 35.00
         * discount : 35.00
         * money : 115.00
         * price : 115.00
         * path : uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg
         * title : 三国演义原著
         */

        private Integer id;
        private Integer goodsId;
        private String name;
        private Integer shopId;
        private Integer isselected;
        private Integer count;
        private String preevent;
        private String discount;
        private String money;
        private String price;
        private String path;
        private String title;

        public ListDTO(Integer goodsId, Integer count, String preevent, String price, String path, String title) {
            this.goodsId = goodsId;
            this.count = count;
            this.preevent = preevent;
            this.price = price;
            this.path = path;
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(Integer goodsId) {
            this.goodsId = goodsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
        }

        public Integer getIsselected() {
            return isselected;
        }

        public void setIsselected(Integer isselected) {
            this.isselected = isselected;
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
