package com.example.shanyu.http;

/**
 * Author ： MemoThree
 * Time   ： 2019/1/3
 * Desc   ： 回调结果
 */
public interface HttpResultInterface<T> {

    void onFailure(String errorMsg);

    void onSuccess(T t);


}
