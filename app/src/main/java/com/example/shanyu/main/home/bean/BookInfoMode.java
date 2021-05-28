package com.example.shanyu.main.home.bean;

import java.io.Serializable;
import java.util.List;

public class BookInfoMode {

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
     * name : 陆上书店
     * decimal : 4.8000000000
     * path_list : [{"id":131,"path":"uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg"},{"id":132,"path":"uploads/images/20210401/c01acd0e6d4fbc576f0e92d2f7cf7098.jpg"},{"id":133,"path":"uploads/images/20210401/2143b0163ee0a8b4f71cd727ff74be96.jpg"}]
     */

    private Integer id;
    private Integer uid;
    private String title;
    private Integer shop_id;
    private Integer category_id;
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
    private Integer created_at;
    private Integer updated_at;
    private String name;
    private Integer collection;
    private String covers;
    private String decimal;
    private List<PathListDTO> path_list;

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

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

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }

    public Integer getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Integer updated_at) {
        this.updated_at = updated_at;
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
        return created_at;
    }

    public void setCreatedAt(Integer createdAt) {
        this.created_at = createdAt;
    }

    public Integer getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updated_at = updatedAt;
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

    public List<PathListDTO> getPathList() {
        return path_list;
    }

    public void setPathList(List<PathListDTO> pathList) {
        this.path_list = pathList;
    }

    public static class PathListDTO implements Serializable {
        /**
         * id : 131
         * path : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
         */

        private Integer id;
        private String path;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
