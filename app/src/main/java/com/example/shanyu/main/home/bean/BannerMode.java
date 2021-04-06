package com.example.shanyu.main.home.bean;

import java.io.Serializable;
import java.util.List;


public class BannerMode implements Serializable {

    /**
     * banner : [{"id":1,"picture":"/uploads/images/20201121/9b270fe1b2b357416474419d093f0632.jpg","title":"知识问答"},{"id":4,"picture":"/uploads/images/20201121/761a0e5c0868fc995b8222d2d632453a.jpg","title":"沙龙活动"}]
     * category : {"1":{"id":1,"name":"史学","picture":null,"parent_id":0},"2":{"id":2,"name":"语文","picture":null,"parent_id":0},"4":{"id":4,"name":"数学","picture":null,"parent_id":0},"5":{"id":5,"name":"物理","picture":null,"parent_id":0},"6":{"id":6,"name":"化学","picture":null,"parent_id":0},"7":{"id":7,"name":"地理","picture":null,"parent_id":0},"8":{"id":8,"name":"小说","picture":null,"parent_id":0},"9":{"id":9,"name":"英语","picture":null,"parent_id":0},"10":{"id":10,"name":"政治","picture":null,"parent_id":0},"11":{"id":11,"name":"国外名著","picture":null,"parent_id":0},"12":{"id":12,"name":"计算机","picture":null,"parent_id":0},"13":{"id":13,"name":"补习资料","picture":null,"parent_id":0,"category_list":[{"id":14,"name":"英语","picture":null,"parent_id":13},{"id":15,"name":"数奥","picture":null,"parent_id":13}]}}
     * goods : [{"id":1,"uid":1,"title":"四大原著著小学套餐","shop_id":1,"category_id":1,"category":"史学","goods":"四大名著小学套餐版全4册","details":"<p><img src=\"http://sypds.com/uploads/images/20210323/110b8fd11c966248c79de9a645cbdb04.jpg\" style=\"max-width:100%\"><\/p>","pic":"131,132,133","cover":131,"price":"58.00","preevent":"45.50","count":10,"sold":1,"collection_num":3,"isrecommend":1,"status":1,"created_at":1617246389,"updated_at":1617269259,"path":"uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg","name":"陆上书店","decimal":"4.8000000000"},{"id":2,"uid":1,"title":"三国演义原著","shop_id":1,"category_id":1,"category":"史学","goods":"全套4册四大名著小学生版青少年无障碍阅读三国演义原著正版完整版水浒传五年级下册必读西游记白话文儿童红楼梦中国古典四大名著","details":"<p><img src=\"http://sypds.com/uploads/images/20210320/ee6896f27f2cdd2d2d11f823729f9481.jpg\" style=\"max-width:100%\"><img src=\"http://sypds.com/uploads/images/20210320/c28b7c881b88f07a885ea839fe94973f.jpg\" style=\"max-width:100%\"><\/p>","pic":"134,135","cover":134,"price":"115.00","preevent":"35.00","count":20,"sold":5,"collection_num":7,"isrecommend":1,"status":1,"created_at":1617269259,"updated_at":1617269259,"path":"uploads/images/20210401/b6491afe1ca68e0a7c31d4579caf528e.jpg","name":"陆上书店","decimal":"4.8000000000"}]
     */

    private List<BannerDTO> banner;
    private List<GoodsDTO> goods;

    public List<BannerDTO> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDTO> banner) {
        this.banner = banner;
    }

    public List<GoodsDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDTO> goods) {
        this.goods = goods;
    }


    public static class BannerDTO implements Serializable {
        /**
         * id : 1
         * picture : /uploads/images/20201121/9b270fe1b2b357416474419d093f0632.jpg
         * title : 知识问答
         */

        private Integer id;
        private String picture;
        private String title;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class GoodsDTO implements Serializable {
        /**
         * id : 1
         * uid : 1
         * title : 四大原著著小学套餐
         * shop_id : 1
         * category_id : 1
         * category : 史学
         * goods : 四大名著小学套餐版全4册
         * details : <p><img src="http://sypds.com/uploads/images/20210323/110b8fd11c966248c79de9a645cbdb04.jpg" style="max-width:100%"></p>
         * pic : 131,132,133
         * cover : 131
         * price : 58.00
         * preevent : 45.50
         * count : 10
         * sold : 1
         * collection_num : 3
         * isrecommend : 1
         * status : 1
         * created_at : 1617246389
         * updated_at : 1617269259
         * path : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
         * name : 陆上书店
         * decimal : 4.8000000000
         */

        private Integer id;
        private Integer uid;
        private String title;
        private Integer shopId;
        private Integer categoryId;
        private String category;
        private String goods;
        private String details;
        private String pic;
        private Integer cover;
        private String price;
        private String preevent;
        private Integer count;
        private Integer sold;
        private Integer collectionNum;
        private Integer isrecommend;
        private Integer status;
        private Integer createdAt;
        private Integer updatedAt;
        private String path;
        private String name;
        private String decimal;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getShopId() {
            return shopId;
        }

        public void setShopId(Integer shopId) {
            this.shopId = shopId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getGoods() {
            return goods;
        }

        public void setGoods(String goods) {
            this.goods = goods;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public Integer getCover() {
            return cover;
        }

        public void setCover(Integer cover) {
            this.cover = cover;
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

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getSold() {
            return sold;
        }

        public void setSold(Integer sold) {
            this.sold = sold;
        }

        public Integer getCollectionNum() {
            return collectionNum;
        }

        public void setCollectionNum(Integer collectionNum) {
            this.collectionNum = collectionNum;
        }

        public Integer getIsrecommend() {
            return isrecommend;
        }

        public void setIsrecommend(Integer isrecommend) {
            this.isrecommend = isrecommend;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Integer createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Integer updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDecimal() {
            return decimal;
        }

        public void setDecimal(String decimal) {
            this.decimal = decimal;
        }
    }
}
