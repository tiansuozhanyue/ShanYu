package com.example.shanyu.main.mine.bean;

public class PositionMode {

    /**
     * key : 110000
     * value : 北京
     */

    private Integer key;
    private String value;
    private String letters;//显示拼音的首字母

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
