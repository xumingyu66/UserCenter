package com.mingyu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingyu.usercenter.Mapper.UserMapper;
import com.mingyu.usercenter.common.BusinessException;
import com.mingyu.usercenter.common.ErrorCode;
import com.mingyu.usercenter.common.Result;
import com.mingyu.usercenter.common.ResultUtil;
import com.mingyu.usercenter.constant.UserConstant;
import com.mingyu.usercenter.model.domain.User;
import com.mingyu.usercenter.service.UserService;
import org.apache.catalina.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author 25066
 * @description 针对表【user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-10-19 15:02:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Result<Long> register(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            //todo 封装一个异常类，告诉前端是哪里出错
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"用户名或密码为空");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码为空");
        }
        if (userAccount.length() < 4) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"用户名长度小于4");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度小于4");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"密码长度小于8");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度小于8");
        }
        //对账户名进行正则表达式的校验
        String pattern = "^[a-zA-Z0-9_]*$";
        if (!userAccount.matches(pattern)) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"用户名不能包含特殊字符");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能包含特殊字符");
        }
        if (!userPassword.equals(checkPassword)) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"两次密码输入不一致");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        }
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //密码加密
        byte[] hashBytes = digest.digest((userPassword + "xumingyu").getBytes());
        String saltPassword = Base64.getEncoder().encodeToString(hashBytes);
        //通过查询数据库判断是否用户名重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        int result = userMapper.selectCount(queryWrapper);
        if (result > 0) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"用户名重复");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名重复");
        }
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(saltPassword);
        if (!this.save(user)) {
//            return ResultUtil.error(ErrorCode.PARAMS_ERROR,"注册用户失败");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册用户失败");
        }
        Long id = user.getId();
        return ResultUtil.success(id);
    }

    public Result<User> login(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            //todo 封装一个返回结果的类将结果返回给前端
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不能小于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能小于8位");
        }
        //对账户名进行正则表达式的校验
        String pattern = "^[a-zA-Z0-9_]*$";
        if (!userAccount.matches(pattern)) {
            return null;
        }
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //密码加密
        byte[] hashBytes = digest.digest((userPassword + "xumingyu").getBytes());
        String saltPassword = Base64.getEncoder().encodeToString(hashBytes);
        //根据账户和密码查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", saltPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码有误");
        }
        //在session对象中保存用户登录状态
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.SESSION_USER, saftUser(user));
        //去除敏感信息
        User user1 = saftUser(user);
        return ResultUtil.success(user1);
    }

    public Result<Boolean> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN,"客户端与服务器已经断开连接");
        }
        session.removeAttribute(UserConstant.SESSION_USER);
        return ResultUtil.success(true);
    }

    public User saftUser(User user) {
        //去除敏感信息
        User handleUser = new User();
        handleUser.setId(user.getId());
        handleUser.setUserName(user.getUserName());
        handleUser.setUserAccount(user.getUserAccount());
        handleUser.setAvatarUrl(user.getAvatarUrl());
        handleUser.setGender(user.getGender());
        handleUser.setPhone(user.getPhone());
        handleUser.setEmail(user.getEmail());
        handleUser.setStatus(user.getStatus());
        handleUser.setCreateTime(user.getCreateTime());
        handleUser.setUpdateTime(user.getUpdateTime());
        handleUser.setRole(user.getRole());
        return handleUser;
    }
}




