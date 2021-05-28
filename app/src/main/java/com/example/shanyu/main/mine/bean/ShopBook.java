package com.example.shanyu.main.mine.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopBook implements Serializable {

    private String name;
    private Integer shopId;
    private List<MyBooksMode> modes = new ArrayList<>();

    public ShopBook(String name, Integer shopId, MyBooksMode mode) {
        this.name = name;
        this.shopId = shopId;
        this.modes.add(mode);
    }

    public void add(MyBooksMode mode) {
        this.modes.add(mode);
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

    public List<MyBooksMode> getModes() {
        return modes;
    }

    public void setModes(List<MyBooksMode> modes) {
        this.modes = modes;
    }
}
