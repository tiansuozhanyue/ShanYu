package com.example.shanyu.main.home.bean;

import com.example.shanyu.main.mine.bean.OffersMode;

import java.io.Serializable;
import java.util.List;

public class OrderOfferBean implements Serializable {

    private List<OffersMode> effective;
    private List<OffersMode> invalid;

    public List<OffersMode> getEffective() {
        return effective;
    }

    public void setEffective(List<OffersMode> effective) {
        this.effective = effective;
    }

    public List<OffersMode> getInvalid() {
        return invalid;
    }

    public void setInvalid(List<OffersMode> invalid) {
        this.invalid = invalid;
    }
}
