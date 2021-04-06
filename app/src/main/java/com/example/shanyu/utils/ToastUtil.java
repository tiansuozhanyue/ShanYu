package com.example.shanyu.utils;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shanyu.MyApplication;
import com.example.shanyu.R;

/**
 * Author ： MemoThree
 * Time   ： 2019/1/3
 * Desc   ： Toast 工具
 */
public class ToastUtil {

    public static void shortToast(String msg) {
        Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String msg) {
        Toast.makeText(MyApplication.context, msg, Toast.LENGTH_LONG).show();
    }

    //短消息提示在中间
    public  static void  shortToastMid(String  msg){
        Toast toast = Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //长消息提示在中间
    public  static  void  longToastMid(String msg){
        Toast toast = Toast.makeText(MyApplication.context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

    //带图片的提示
    public  static  void  shortToastMidWithImg(String msg){
        Toast toast = Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(MyApplication.context);
        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }


    //长时间提示消息
    public  static  void  longToastMidWithImg(String msg){
        Toast toast = Toast.makeText(MyApplication.context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(MyApplication.context);
        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }


}
