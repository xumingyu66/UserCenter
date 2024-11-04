package com.mingyu.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用的返回对象，将返回的数据格式统一
 * @param <T>
 *
 * @author xumingyu
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6606121987649714562L;

    private String code;

    private T data;

    private String msg;

    private String description;

    Result(String code, T data, String msg, String description){
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description = description;
    }

    Result(String code, T data){
        this(code, data, "","");
    }
    Result(ErrorCode errorCode, String msg,String description){
        this(errorCode.getCode(),null,msg,description);
    }

    /**
     * 用于创建错误的返回对象
     * @param errorCode
     */
    Result(ErrorCode errorCode, String description){
        this(errorCode.getCode(),null,errorCode.getMsg(),description);
    }
}
