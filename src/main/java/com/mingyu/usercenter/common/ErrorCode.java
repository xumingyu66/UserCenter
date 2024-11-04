package com.mingyu.usercenter.common;

import lombok.Getter;

/**
 * 定义一组错误的状态码
 *
 * @author xumingyu
 */
@Getter
public enum ErrorCode {
    PARAMS_ERROR("40000","参数错误",""),
    PARAMS_NULL("400001","参数为空",""),
    NO_LOGIN("40100","未登录",""),
    NO_AUTHOR("40101","没有管理员权限",""),
    SYSTEM_ERROR("50000","系统异常","");
    /**
     * 错误状态码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String msg;
    /**
     * 错误详细信息
     */
    private final String description;
    ErrorCode(String code,String msg,String description){
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

}
