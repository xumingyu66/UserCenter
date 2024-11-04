package com.mingyu.usercenter.model.requset;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于接收前端json格式传递过来的数据
 *
 * @author mingyu
 */
@Data
public class RequestRegister implements Serializable {

    private static final long serialVersionUID = 2145420559632650517L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
