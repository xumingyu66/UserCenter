package com.mingyu.usercenter.common;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 这是一个全局的异常处理器
 * 捕获代码中的所有异常，集中处理
 *
 * @author xumingyu
 */

@RestControllerAdvice
public class GlobalExceptionHandle{

    @ExceptionHandler({BusinessException.class})
    public Result BusinessException(BusinessException e){
        //捕获到该异常以后封装成结果对象返回给前端。
        return new Result(e.getCode(),null,e.getMessage(),e.getDescription());
    }

    @ExceptionHandler({RuntimeException.class})
    public Result SystemException(RuntimeException e){
        return new Result(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
