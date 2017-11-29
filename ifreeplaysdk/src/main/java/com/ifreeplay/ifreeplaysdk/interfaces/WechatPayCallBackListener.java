package com.ifreeplay.ifreeplaysdk.interfaces;

/**
 * Created by lenovo on 2017/9/28.
 */

public interface WechatPayCallBackListener {
    void onFinish(String netWrongMsg);

    void onError(Exception e);
}
