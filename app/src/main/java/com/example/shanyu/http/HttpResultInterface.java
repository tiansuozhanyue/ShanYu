package com.example.shanyu.http;

/**
 * Author ： MemoThree
 * Time   ： 2019/1/3
 * Desc   ： 回调结果
 */
public interface HttpResultInterface {

    void onFailure(String errorMsg);

    void onSuccess(String t);


}
