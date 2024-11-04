package com.mingyu.usercenter.model.requset;

import lombok.Data;

import java.io.Serializable;
@Data
public class RequestLogin implements Serializable {

    private static final long serialVersionUID = -4758464853236309386L;

    private String userAccount;

    private String userPassword;
}
