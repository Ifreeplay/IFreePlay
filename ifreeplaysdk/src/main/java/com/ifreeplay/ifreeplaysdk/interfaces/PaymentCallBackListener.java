package com.ifreeplay.ifreeplaysdk.interfaces;

/**
 * Created by lenovo on 2017/9/28.
 */

public interface PaymentCallBackListener {
    void onFinish(String response);

    void onError(Exception e);
}
