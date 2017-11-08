package com.okulservis.web.rest.vm;

import com.okulservis.domain.User;
import com.okulservis.web.rest.UserJWTController;

import java.util.Optional;

public class ResponseLogin {
    private Integer code;
    private User user;
    private UserJWTController.JWTToken token;

    public ResponseLogin(Integer code, User user, UserJWTController.JWTToken token) {
        this.code = code;
        this.user = user;
        this.token = token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserJWTController.JWTToken getToken() {
        return token;
    }

    public void setToken(UserJWTController.JWTToken token) {
        this.token = token;
    }
}
