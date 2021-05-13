package com.example.shanyu.base;

public class EventBean {

    public static final int PAY_FAILE = 0;
    public static final int PAY_SUCESSS = 1;
    public static final int PAY_CANCLE = 2;

    public int flag;

    public EventBean(int flag) {
        this.flag = flag;
    }
}
