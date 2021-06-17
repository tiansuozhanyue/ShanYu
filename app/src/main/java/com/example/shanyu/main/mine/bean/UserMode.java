package com.example.shanyu.main.mine.bean;

public class UserMode {

    /**
     * id : 47
     * mobile_bind : 0
     * mobile : 18868026919
     * autograph : null
     * ismessage : 1
     * status : 1
     * avatar : 0
     * create_time : 1617984472
     */

    private Integer id;
    private Integer mobile_bind;
    private String mobile;
    private String autograph;
    private Integer ismessage;
    private Integer status;
    private String avatar;
    private String nickname;
    private Integer createTime;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMobileBind() {
        return mobile_bind;
    }

    public void setMobileBind(Integer mobileBind) {
        this.mobile_bind = mobileBind;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public Integer getIsmessage() {
        return ismessage;
    }

    public void setIsmessage(Integer ismessage) {
        this.ismessage = ismessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
