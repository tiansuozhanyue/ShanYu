package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class OrderStatueMode implements Serializable {

    /**
     * id : 301
     * name : 陆上书店
     * title : 四大原著著小学套餐
     * count : 2
     * preevent : 45.50
     * price : 58.00
     * principal : 91.00
     * status : 5
     * path : uploads/images/20210401/fead1b4e1d5c9ab5279c57f939c24648.jpg
     */

    private int id;
    private String name;
    private String title;
    private int count;
    private String preevent;
    private String price;
    private String principal;
    private int status;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
