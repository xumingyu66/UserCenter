package com.mingyu.usercenter.common;

/**
 * 该类用于封装创建通用返回对象的操作，简化繁琐的操作。
 *
 * @author xumingyu
 */
public class ResultUtil {
    /**
     * 返回成功信息
     * @param data 拿到成功的数据
     * @return 返回一个通用的结果对象
     * @param <T> 泛型
     */
    public static <T> Result<T> success(T data){
        return new Result<>("0",data,"ok","");
    }

    /**
     * 返回错误的信息
     * @param errorCode 错误状态码
     * @param description 错误信息的详细描述
     * @return 返回一个错误的结果对象
     */
    public static  Result error(ErrorCode errorCode, String description){
        return new Result(errorCode,description);
    }
}
