package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class BookMode implements Serializable {

    /**
     * id : 1
     * uid : 1
     * title : 四大原著著小学套餐
     * shop_id : 1
     * category_id : 1
     * category : 史学
     * goods : 四大名著小学套餐版全4册
     * details :  < 			/p>
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
    private Integer createdAt;
    private Integer updatedAt;
    private String covers;
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
        return shop_id;
    }

    public void setShopId(Integer shopId) {
        this.shop_id = shopId;
    }

    public Integer getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Integer categoryId) {
        this.category_id = categoryId;
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

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
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
