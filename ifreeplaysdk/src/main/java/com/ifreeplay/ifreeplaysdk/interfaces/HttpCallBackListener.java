package com.ifreeplay.ifreeplaysdk.interfaces;

public interface HttpCallBackListener {

    void onFinish(String response);

    void onError(Exception e);

}
