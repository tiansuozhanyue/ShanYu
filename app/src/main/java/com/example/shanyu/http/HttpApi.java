package com.example.shanyu.http;


public class HttpApi {


    //    public static String WxPayAppId = "wx20b72ab25f7f8e8e";
    public static String WX_APPID = "wx0a44f6b97bddac61";
    public static String WX_SECRET = "3a585930147811a58d4970b19efa177b";

    public static String HOST = "https://shushanyu.com/";

    public static String REGIST = HOST + "index.php/user/publics/signup";
    public static String SIGNOUT = HOST + "index.php/user//publics/signout";
    public static String RESET = HOST + "index.php/user/publics/reset";
    public static String SEND = HOST + "index.php/user/publics/send";
    public static String LOGIN = HOST + "index.php/user/publics/signin";
    public static String VERIFICATION = HOST + "index.php/user/publics/verification";
    public static String LOGIN_WX = HOST + "index.php/user/publics/wlogin";
    public static String BANNER = HOST + "index.php/index";
    public static String BOOKS = HOST + "index.php/index/index/isrecommend";
    public static String ACTION = HOST + "index.php/index/activity";
    public static String CATEGORY = HOST + "index.php/index/category";
    public static String CATEGORYLIST = HOST + "index.php/index/category/list";
    public static String ADDRESS = HOST + "index.php/index/address";
    public static String FOOTS = HOST + "index.php/index/user/history";
    public static String OFFERS = HOST + "index.php/index/user/coupon";
    public static String OFFERS_ORDER = HOST + "index.php/index/user/couponchoice";
    public static String OFFERS_ADD = HOST + "index.php/index/user/addcoupon";
    public static String OFFERS_SHOP = HOST + "index.php/index/shop/coupon";
    public static String SET = HOST + "index.php/index/user/set";
    public static String COUNT = HOST + "index.php/index/user/count";
    public static String PROPOSAL = HOST + "index.php/index/user/proposal";
    public static String UPLOAD = HOST + "index.php/index/evaluate/upload";
    public static String POSITION = HOST + "index.php/index/shop/position";
    public static String ADD = HOST + "index.php/index/address/add";
    public static String EDIT = HOST + "index.php/index/address/edit";
    public static String DELL = HOST + "index.php/index/address/isdell";
    public static String SELECTED = HOST + "index.php/index/address/isselected";
    public static String CARTLIST = HOST + "index.php/index/cart/list";
    public static String CARTDELET = HOST + "index.php/index/cart/isdell";
    public static String CARTSELECT = HOST + "index.php/index/cart/isselected";
    public static String CARTSUM = HOST + "index.php/index/cart/is_sum";
    public static String ADDCART = HOST + "index.php/index/cart/add";
    public static String EVALUATE = HOST + "index.php/index/goods/evaluate";
    public static String BOOKINFO = HOST + "index.php/index/goods/detail";
    public static String ORDER_ADD = HOST + "index.php/index/order/add";
    public static String PAY_W = HOST + "index.php/index/wechat_pay/is_now";
    public static String PAY_Z = HOST + "index.php/index/Alibpay/is_now";
    public static String PAY_BACK_W = HOST + "index.php/index/wechat_pay/refund";
    public static String PAY_BACK_Z = HOST + "index.php/index/Alibpay/refund";
    public static String SHOP_STATUE = HOST + "index.php/index/shop";
    public static String SHOP_ADD = HOST + "index.php/index/shop/add";
    public static String ORDER_LIST = HOST + "index.php/index/order/list";
    public static String COLLECTION = HOST + "index.php/index/collection/collection";
    public static String COLLECTIONS = HOST + "index.php/index/collection";
    public static String CANCLE_COLLECTION = HOST + "index.php/index/collection/is_collection";
    public static String SHARE = HOST + "index.php/index/share";
    public static String ISMESSAGE = HOST + "index.php/index/user/ismessage";
    public static String SEARCH = HOST + "index.php/index/goods/search";
    public static String SHOPBOOKLIST = HOST + "index.php/index/goods/list";
    public static String SETORDERSTATUE = HOST + "index.php/index/order/isstatus";
    public static String SETORDERDETAIL = HOST + "index.php/index/order/detail";
    public static String ADDEVALUATE = HOST + "index.php/index/evaluate/add";
    public static String PRIVATE = HOST + "oue.html";

    public static String getWxInfo1 = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String getWxInfo2 = "https://api.weixin.qq.com/sns/userinfo";

}
