package com.mingyu.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mingyu.usercenter.common.BusinessException;
import com.mingyu.usercenter.common.ErrorCode;
import com.mingyu.usercenter.common.Result;
import com.mingyu.usercenter.common.ResultUtil;
import com.mingyu.usercenter.constant.UserConstant;
import com.mingyu.usercenter.model.domain.User;
import com.mingyu.usercenter.model.requset.RequestLogin;
import com.mingyu.usercenter.model.requset.RequestRegister;
import com.mingyu.usercenter.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口
 * @author mingyu
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    UserServiceImpl userService;

    @PostMapping("/register")
    public Result<Long> register(@RequestBody RequestRegister registerInfo){
        String userAccount = registerInfo.getUserAccount();
        String userPassword = registerInfo.getUserPassword();
        String checkPassword = registerInfo.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        return userService.register(userAccount,userPassword,checkPassword);
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody RequestLogin loginInfo, HttpServletRequest request){
        String userAccount = loginInfo.getUserAccount();
        String userPassword = loginInfo.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        return userService.login(userAccount,userPassword,request);
    }
    @PostMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(userService.logout(request).getData());
    }

    @GetMapping("/select")
    public Result<List<User>> selectByAccount(String userAccount,HttpServletRequest request){
        if (isNotAdmin(request).getData()){
            throw new BusinessException(ErrorCode.NO_AUTHOR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userAccount",userAccount);
        return ResultUtil.success(userService.list(queryWrapper));
    }
    @GetMapping("/selectAll")
    public Result<List<User>> selectAll(){
        List<User> saftUsers = new ArrayList<>();
        for (User item:
                userService.list()) {
            saftUsers.add(userService.saftUser(item));
        }
        return ResultUtil.success(saftUsers);
    }
    @GetMapping("/current")
    public Result<User> currentUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(UserConstant.SESSION_USER);
        if(user == null){
            throw new BusinessException(ErrorCode.NO_LOGIN,"用户未登录无法获取当前用户");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",user.getId());
        User u = userService.getOne(queryWrapper);
        return ResultUtil.success(userService.saftUser(u));
    }
    @PostMapping("/delete")
    public Result<Boolean> deleteById(@RequestBody long id,HttpServletRequest request){
        if (isNotAdmin(request).getData()){
            throw new BusinessException(ErrorCode.NO_AUTHOR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return ResultUtil.success(userService.remove(queryWrapper));
    }

    /**
     * 判断是否具有管理员权限
     * @param request 获取前端传来的数据
     * @return 默认权限
     */
    private Result<Boolean> isNotAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(UserConstant.SESSION_USER);
        return ResultUtil.success(user != null && user.getRole() == UserConstant.ROLE_DEFAULT);
    }
}
