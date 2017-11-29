package com.ifreeplay.ifreeplaysdk.utils.payUtils;

/**
 * Created by lenovo on 2017/9/28.
 */

public class UrlConstants {
    public static final String SERVER_IP = "http://192.168.0.114:8081";
    public static final String AUTHLOGIN = SERVER_IP+"/auth/login";
    public static final String CONFIRMORDER = SERVER_IP+"/order";
    public static final String GETORDERPARAMETERS = SERVER_IP+"/payment/wechat";
    public static final String CHECK_PAYPAL_RESULT = SERVER_IP+"/payment/paypal/orderVerify";
    public static final String CHECK_GOOGLE_RESULT = SERVER_IP+"/payment/google/orderVerify";
    public static final String FIND_PRODUCT_LIST = SERVER_IP+"/product/findByGameId";
}
