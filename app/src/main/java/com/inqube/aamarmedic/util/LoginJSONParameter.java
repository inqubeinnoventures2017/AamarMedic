package com.inqube.aamarmedic.util;

class LoginJSONParameter {
    private String username;
    private String password;
    private String fcm_token;

    public LoginJSONParameter(String username, String password, String fcm_token)
    {
        this.username = username;
        this.password = password;
        this.fcm_token =fcm_token;
    }
}
