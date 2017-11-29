package com.ifreeplay.ifreeplaysdk.model;



public class CreateOrder {
    public enum CurrencyTypes {
        CNY, // 人民币
        USD, // 美元
        HKD, // 港币
        JPY, // 日元
        GBP, // 英镑
        EUR // 欧元
    }
    private long playerId;
    private long productId;
    private CurrencyTypes currencyTypes;
    private String spbillCreateIp;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public CurrencyTypes getCurrencyTypes() {
        return currencyTypes;
    }

    public void setCurrencyTypes(CurrencyTypes currencyTypes) {
        this.currencyTypes = currencyTypes;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
}
