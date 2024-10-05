package org.zjubs.pricecomwebbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjubs.pricecomwebbackend.entity.User;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.utils.EmailUtil;
import org.zjubs.pricecomwebbackend.utils.EncryptSha256Util;
import org.zjubs.pricecomwebbackend.utils.JWTUtil;
import org.zjubs.pricecomwebbackend.utils.RandomUtil;

@Service
public class UserService {
    @Autowired
    private org.zjubs.pricecomwebbackend.mapper.UserMapper userMapper;
    @Autowired
    private EmailUtil emailUtils;

    public ApiResult queryUserByUsername(String username) {
        User user = userMapper.queryUserByUsername(username);
        return new ApiResult(true, user);
    }

    public ApiResult login(String username, String password) {
        try {
            String encodePassword = EncryptSha256Util.getSha256Str(password);
            User user = userMapper.queryUserByUsernameAndPassword(username, encodePassword);
            if (user != null) {
                String token = JWTUtil.getTokenByIdAndUsername(user.getId(), user.getUsername());
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
            String verificationCode = RandomUtil.generateVerificationCode();
            emailUtils.sendAuthCodeEmail(email, verificationCode);
            String sha256Str = EncryptSha256Util.getSha256Str(verificationCode);
            String tokenByEmailAndJustifyCode = JWTUtil.getTokenByEmailAndJustifyCode(email, sha256Str);
            return ApiResult.success(tokenByEmailAndJustifyCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    public ApiResult userRegister(String username, String password, String email, String emailCode, String lastEmailCode) {
        try {
            String newEmailCode = EncryptSha256Util.getSha256Str(emailCode);
            String justifyCodeByToken = JWTUtil.getJustifyCodeByToken(lastEmailCode);
            if (!newEmailCode.equals(justifyCodeByToken)) {
                return ApiResult.fail("验证码错误");
            }
            User user1 = userMapper.queryUserByUsername(username);
            if (user1 != null) {
                return ApiResult.fail("用户名已存在");
            }
            User user2 = userMapper.queryUserByEmail(email);
            if (user2 != null) {
                return ApiResult.fail("该邮箱已被注册");
            }
            String emailByToken = JWTUtil.getEmailByToken(lastEmailCode);
            if (!emailByToken.equals(email)) {
                return ApiResult.fail("请确保注册邮箱和获取验证码的邮箱是同一个邮箱");
            }
            String encodePassword = EncryptSha256Util.getSha256Str(password);
            userMapper.userRegister(username, encodePassword, email);
            return ApiResult.success();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }
}
