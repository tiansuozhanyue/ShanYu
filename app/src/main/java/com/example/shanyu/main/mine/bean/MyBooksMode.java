package com.example.shanyu.main.mine.bean;


import java.io.Serializable;
import java.util.List;

public class MyBooksMode implements Serializable {

    /**
     * shop_id : 1
     * name : 陆上书店
     * list : [{"id":240,"goods_id":2,"name":"陆上书店","shop_id":1,"isselected":0,"count":1,"preevent":"35.00","discount":"35.00","money":"115.00","price":"115.00","path":"uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg","title":"三国演义原著"}]
     */

    private Integer shop_id;
    private String name;
    private List<ListDTO> list;

    public MyBooksMode(Integer shopId, String name, List<ListDTO> list) {
        this.shop_id = shopId;
        this.name = name;
        this.list = list;
    }

    public Integer getShopId() {
        return shop_id;
    }

    public void setShopId(Integer shopId) {
        this.shop_id = shopId;
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
        private Integer goods_id;
        private String name;
        private Integer shop_id;
        private Integer isselected;
        private Integer count;
        private String preevent;
        private String discount;
        private String money;
        private String price;
        private String covers;
        private String title;

        public ListDTO(Integer goodsId, Integer count, String preevent, String price, String path, String title) {
            this.goods_id = goodsId;
            this.count = count;
            this.preevent = preevent;
            this.price = price;
            this.covers = path;
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGoodsId() {
            return goods_id;
        }

        public void setGoodsId(Integer goodsId) {
            this.goods_id = goodsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getShopId() {
            return shop_id;
        }

        public void setShopId(Integer shopId) {
            this.shop_id = shopId;
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

        public String getCovers() {
            return covers;
        }

        public void setCovers(String covers) {
            this.covers = covers;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
