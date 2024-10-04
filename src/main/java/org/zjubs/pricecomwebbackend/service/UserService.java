package org.zjubs.pricecomwebbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zjubs.pricecomwebbackend.entity.User;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.utils.EmailUtils;
import org.zjubs.pricecomwebbackend.utils.JWTUtils;
import org.zjubs.pricecomwebbackend.utils.RandomUtils;

@Service
public class UserService {
    @Autowired
    private org.zjubs.pricecomwebbackend.mapper.UserMapper userMapper;
    @Autowired
    private EmailUtils emailUtils;

    public ApiResult queryUserByUsername(String username) {
        User user = userMapper.queryUserByUsername(username);
        return new ApiResult(true, user);
    }

    public ApiResult login(String username, String password) {
        try {
            User user = userMapper.queryUserByUsernameAndPassword(username, password);
            if (user != null) {
                String token = JWTUtils.getTokenByIdAndUsername(user.getId(), user.getUsername());
                return ApiResult.success(token);
            } else {
                return new ApiResult(false, "用户名或密码不正确");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    public ApiResult sendEmailJustifyCode(String email) {
        try {
            String verificationCode = RandomUtils.generateVerificationCode();
            emailUtils.sendAuthCodeEmail(email, verificationCode);
            return ApiResult.success(verificationCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }
}
