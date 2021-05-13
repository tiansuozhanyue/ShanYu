package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class WxPayBean implements Serializable {

    /**
     * appid : wx0a44f6b97bddac61
     * partnerid : 1605539700
     * order_id : 444
     * money : 2
     * prepayid : wx2618390059214724c5b943aa830ceb0000
     * package : Sign=WXPay
     * noncestr : XfbJGHVmLKoTlyvfeJLnHhXsrnkZTYmZ
     * timestamp : 1619433540
     * sign : 47DAF773A5C76FD4AD70F9340C082DE3
     * out_trade_no : N2021042633540414427453
     */

    private String appid;
    private String partnerid;
    private Integer order_id;
    private Integer money;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String sign;
    private String outTradeNo;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer orderId) {
        this.order_id = orderId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
