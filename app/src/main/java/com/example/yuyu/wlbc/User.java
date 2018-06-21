package com.example.yuyu.wlbc;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String createDate;
    private String token;
    private String limitTime;
    private int power;
    private String type;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * @return the limitTime
     */
    public String getLimitTime() {
        return limitTime;
    }
    /**
     * @param limitTime the limitTime to set
     */
    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }
    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


}
