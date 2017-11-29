package com.ifreeplay.ifreeplaysdk.model;

import java.io.Serializable;

/**
 * Created by gaowe on 2017/7/6.
 */

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String firstname;
    private String lastname;
    private String userId;
    private String userName;
    private String middleName;
    private String birthday;
    private String headPortraitUrl;
    private String gender;
    private String wxOpenId;
    private String wxUnionId;

    public String getWxOpenId(){return wxOpenId;}
    public void setWxOpenId(String wxOpenId){this.wxOpenId=wxOpenId;}
    public String getWxUnionId(){return wxUnionId;}
    public void setWxUnionId(String wxUnionId){this.wxUnionId=wxUnionId;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }
    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }
}
