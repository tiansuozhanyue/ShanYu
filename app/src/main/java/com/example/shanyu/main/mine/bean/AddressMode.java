package com.example.shanyu.main.mine.bean;

import java.io.Serializable;

public class AddressMode implements Serializable {

    /**
     * id : 1
     * name : 唐先生
     * phone : 18770237494
     * address : 泰豪科技广场
     * isselected : 1
     * create_time : 1617503668
     * are : 360000,360100,360111
     * areaname : 江西省,南昌市,青山湖区
     */

    private Integer id;
    private String name;
    private String phone;
    private String address;
    private Integer isselected;
    private Integer createTime;
    private String are;
    private String areaname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsselected() {
        return isselected;
    }

    public void setIsselected(Integer isselected) {
        this.isselected = isselected;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getAre() {
        return are;
    }

    public void setAre(String are) {
        this.are = are;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }
}
