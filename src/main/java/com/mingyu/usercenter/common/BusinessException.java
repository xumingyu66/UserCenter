package com.mingyu.usercenter.common;

import lombok.Getter;

/**
 * 通过继承异常类我们可以为异常添加我们需要的字段。
 * 
 * @author xumingyu
 */
@Getter
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -5530131136647586346L;
    /**
     * 错误状态码
     */
    private final String code;
    /**
     * 异常的详细信息
     */
    private final String description;

    /**
     * 设置不同的构造函数能够更灵活快捷的设置字段属性
     * @param code
     */
    public BusinessException(ErrorCode code){
        super(code.getMsg());
        this.code = code.getCode();
        this.description = code.getDescription();
    }
    public BusinessException(ErrorCode code, String description){
        super(code.getMsg());
        this.code = code.getCode();
        this.description = description;
    }
    public BusinessException(String code, String msg, String description){
        super(msg);
        this.code = code;
        this.description = description;
    }
}
