package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class ShareBean implements Serializable {

    /**
     * id : 1
     * title : 四大原著著小学套餐
     * covers : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     */

    private Integer id;
    private String title;
    private String covers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }
}
