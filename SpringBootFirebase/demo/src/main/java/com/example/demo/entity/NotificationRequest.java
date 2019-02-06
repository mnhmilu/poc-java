package com.example.demo.entity;

import java.io.Serializable;

public class NotificationRequest implements Serializable {

    private String deviceToken;
    private String message;
    private String message2;


    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
