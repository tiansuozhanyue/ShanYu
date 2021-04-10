package com.example.shanyu.base;

import android.net.Uri;

import java.io.File;

/**
 * Author ： MemoThree
 * Time   ： 2019/1/9
 * Desc   ：
 */
public interface GetPhotoCallBack {

    void selectPhotoCallback(Uri photoOutputUri, File  file);

}
