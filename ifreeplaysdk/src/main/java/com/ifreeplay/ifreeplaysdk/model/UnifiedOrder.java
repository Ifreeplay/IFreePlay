package com.ifreeplay.ifreeplaysdk.model;


public class UnifiedOrder {

    /**
     * code : 0
     * message : Success
     * data : {"appId":"wx5c8698af4ea9d013","timeStamp":"1511768532391","packageValue":"Sign=WXPay","nonceStr":"aRiARlq6IKEAu9Is","prepayId":"wx201711271542129893f4a48a0437642086","partnerId":"1488162712","sign":"5DB1B1A1CA66DD95F0C568B5DC40060F"}
     */

    private int code;
    private String message;
    private UnifiedOrder data;
    private String appId;
    private String timeStamp;
    private String packageValue;
    private String nonceStr;
    private String prepayId;
    private String partnerId;
    private String sign;
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UnifiedOrder getData() {
        return data;
    }

    public void setData(UnifiedOrder data) {
        this.data = data;
    }
}
