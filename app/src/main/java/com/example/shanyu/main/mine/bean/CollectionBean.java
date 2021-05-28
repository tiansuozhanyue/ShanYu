package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class CollectionBean implements Serializable {

    /**
     * id : 1
     * price : 58.00
     * title : 四大原著著小学套餐
     * collection_num : 1
     * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     */

    private Integer id;
    private String price;
    private String title;
    private Integer collection_num;
    private String covers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCollectionNum() {
        return collection_num;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collection_num = collectionNum;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

}
