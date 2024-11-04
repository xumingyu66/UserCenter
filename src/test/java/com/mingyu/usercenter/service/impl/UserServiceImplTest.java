package com.mingyu.usercenter.service.impl;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    UserServiceImpl userService;
    @Test
    public void testPassword() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hashBytes = digest.digest(("我爱你" + "xumingyu").getBytes());
        String SaltPassword = Base64.getEncoder().encodeToString(hashBytes);
        System.out.println("这是加密后的密码：" + SaltPassword);
    }

}