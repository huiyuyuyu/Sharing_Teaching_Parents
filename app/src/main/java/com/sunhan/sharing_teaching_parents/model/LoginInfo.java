package com.sunhan.sharing_teaching_parents.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 孙汉 on 2019-07-26/10/24
 */
public class LoginInfo implements Serializable {
    @SerializedName("account")
    private String account;
    @SerializedName("token")
    private String token;
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
