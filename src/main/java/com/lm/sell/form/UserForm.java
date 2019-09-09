package com.lm.sell.form;

import org.aspectj.bridge.IMessage;

import javax.validation.constraints.NotEmpty;

public class UserForm {

    @NotEmpty(message = "姓名必填")
    private String username;
    @NotEmpty(message = "密码必填")
    private String password;

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
}
