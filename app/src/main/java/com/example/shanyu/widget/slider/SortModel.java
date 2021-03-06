package com.example.shanyu.widget.slider;

import java.io.Serializable;

public class SortModel implements Serializable {

    private Integer key;
    private String name;
    private String letters;//显示拼音的首字母

    public SortModel() {
    }

    public SortModel(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}
