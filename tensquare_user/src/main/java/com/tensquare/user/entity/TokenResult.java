package com.tensquare.user.entity;

public class TokenResult {

    String LoginName;

    String token;

    public TokenResult(String loginName, String token) {
        LoginName = loginName;
        this.token = token;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
