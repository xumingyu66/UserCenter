package com.mingyu.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingyu.usercenter.common.Result;
import com.mingyu.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
* @author 25066
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-10-19 15:02:08
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账户
     * @param userPassword 用户密码
     * @param checkPassword 用户校验密码
     * @return 注册成功后返回用户ID
     */
    Result<Long> register(String userAccount, String userPassword, String checkPassword) throws NoSuchAlgorithmException;

    Result<User> login(String userAccount, String userPassword, HttpServletRequest request);

     Result<Boolean> logout(HttpServletRequest request);
}
