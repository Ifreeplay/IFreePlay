package com.ifreeplay.ifreeplaysdk.model;

/**
 * Created by lenovo on 2017/9/28.
 */

public class ResponseOrder {

    /**
     * code : 0
     * message : Success
     * data : {"id":1,"orderNumber":114182283535780,"gameId":2,"productId":5,"productName":"房卡0","playerId":1,"status":"OPEN","price":30,"totalPrice":30,"dealPrice":30,"createDateTime":1506589475594,"spbillCreateIp":"192.168.0.102","currencyTypes":"CNY"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * orderNumber : 114182283535780
         * gameId : 2
         * productId : 5
         * productName : 房卡0
         * playerId : 1
         * status : OPEN
         * price : 30
         * totalPrice : 30
         * dealPrice : 30
         * createDateTime : 1506589475594
         * spbillCreateIp : 192.168.0.102
         * currencyTypes : CNY
         */

        private int id;
        private String orderNumber;
        private int gameId;
        private int productId;
        private String productName;
        private int playerId;
        private String status;
        private int price;
        private int totalPrice;
        private int dealPrice;
        private long createDateTime;
        private String spbillCreateIp;
        private String currencyTypes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getPlayerId() {
            return playerId;
        }

        public void setPlayerId(int playerId) {
            this.playerId = playerId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getDealPrice() {
            return dealPrice;
        }

        public void setDealPrice(int dealPrice) {
            this.dealPrice = dealPrice;
        }

        public long getCreateDateTime() {
            return createDateTime;
        }

        public void setCreateDateTime(long createDateTime) {
            this.createDateTime = createDateTime;
        }

        public String getSpbillCreateIp() {
            return spbillCreateIp;
        }

        public void setSpbillCreateIp(String spbillCreateIp) {
            this.spbillCreateIp = spbillCreateIp;
        }

        public String getCurrencyTypes() {
            return currencyTypes;
        }

        public void setCurrencyTypes(String currencyTypes) {
            this.currencyTypes = currencyTypes;
        }
    }
}
