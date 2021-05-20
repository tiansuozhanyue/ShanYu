package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class ShareBean implements Serializable {

    /**
     * id : 1
     * title : 四大原著著小学套餐
     * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     */

    private String title;
    private String goods;
    private String covers;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
