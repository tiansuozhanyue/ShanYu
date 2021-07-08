package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class LogiBean implements Serializable {

    /**
     * time : 2021-07-08 00:21:35
     * ftime : 2021-07-08 00:21:35
     * context : 您的订单已被收件员揽收,【深圳龙岗区坪地汽车站经营分部】库存中
     * location :
     */

    private String time;
    private String ftime;
    private String context;
    private String location;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
