package com.ifreeplay.ifreeplaysdk.model;

/**
 * Created by lenovo on 2017/11/27.
 */

public class ViewPlayer {

    /**
     * code : 0
     * message : Success
     * data : {"playerId":1,"gameId":2,"name":"Gw","facebookId":null,"lineId":null,"wechatId":"121212121","headPortraitUrl":null,"token":"eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MX0.Ymrr7QAPDPUXQdFHcPRXaPdXa8OC2NSGiIY7zhTR8gRKnHM-EeLVF__GqxeZZ-wVVeWEh0C6xmJZtRRknImakw"}
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
         * playerId : 1
         * gameId : 2
         * name : Gw
         * facebookId : null
         * lineId : null
         * wechatId : 121212121
         * headPortraitUrl : null
         * token : eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MX0.Ymrr7QAPDPUXQdFHcPRXaPdXa8OC2NSGiIY7zhTR8gRKnHM-EeLVF__GqxeZZ-wVVeWEh0C6xmJZtRRknImakw
         */

        private int playerId;
        private int gameId;
        private String name;
        private Object facebookId;
        private Object lineId;
        private String wechatId;
        private Object headPortraitUrl;
        private String token;

        public int getPlayerId() {
            return playerId;
        }

        public void setPlayerId(int playerId) {
            this.playerId = playerId;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(Object facebookId) {
            this.facebookId = facebookId;
        }

        public Object getLineId() {
            return lineId;
        }

        public void setLineId(Object lineId) {
            this.lineId = lineId;
        }

        public String getWechatId() {
            return wechatId;
        }

        public void setWechatId(String wechatId) {
            this.wechatId = wechatId;
        }

        public Object getHeadPortraitUrl() {
            return headPortraitUrl;
        }

        public void setHeadPortraitUrl(Object headPortraitUrl) {
            this.headPortraitUrl = headPortraitUrl;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
