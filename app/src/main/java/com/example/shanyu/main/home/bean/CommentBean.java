package com.example.shanyu.main.home.bean;

import java.io.Serializable;

public class CommentBean implements Serializable {

    /**
     * id : 50
     * goods_id : 1
     * goods_uid : 1
     * order_id : 1
     * order_uid : 34
     * avatar : https://thirdwx.qlogo.cn/mmopen/vi_32/wiaNSwWd8UBV3Bq9Ttcbia8tibOuYDhDxAspBHUQur92IEdVNhCOt4qFzPiba5oy9rDzibjnOQsIhlCBKrE1074S6FQ/132
     * order_nickname : 社优拼
     * evaluate : 离家很方便
     * reply : null
     * review : 已推荐同学下次还会再来
     * slide : https://sypds.com/uploads/images/20210402/714a8137ac6c4b525cace84a15e83b7e.jpg
     * decimal001 : 5.0000000000
     * decimal002 : 5.0000000000
     * decimal003 : 5.0000000000
     * created_at : 1617342231
     * updated_at : 1617342231
     */

    private Integer id;
    private Integer goods_id;
    private Integer goods_uid;
    private Integer order_id;
    private Integer order_uid;
    private String avatar;
    private String order_nickname;
    private String evaluate;
    private String reply;
    private String review;
    private String slide;
    private String decimal001;
    private String decimal002;
    private String decimal003;
    private Integer created_at;
    private Integer updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getGoods_uid() {
        return goods_uid;
    }

    public void setGoods_uid(Integer goods_uid) {
        this.goods_uid = goods_uid;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getOrder_uid() {
        return order_uid;
    }

    public void setOrder_uid(Integer order_uid) {
        this.order_uid = order_uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOrder_nickname() {
        return order_nickname;
    }

    public void setOrder_nickname(String order_nickname) {
        this.order_nickname = order_nickname;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public String getDecimal001() {
        return decimal001;
    }

    public void setDecimal001(String decimal001) {
        this.decimal001 = decimal001;
    }

    public String getDecimal002() {
        return decimal002;
    }

    public void setDecimal002(String decimal002) {
        this.decimal002 = decimal002;
    }

    public String getDecimal003() {
        return decimal003;
    }

    public void setDecimal003(String decimal003) {
        this.decimal003 = decimal003;
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
}
