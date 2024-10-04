package org.zjubs.pricecomwebbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.query.LoginRequest;
import org.zjubs.pricecomwebbackend.query.RespResult;
import org.zjubs.pricecomwebbackend.query.UserRegister;
import org.zjubs.pricecomwebbackend.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/query")
    public RespResult queryUserByUsername(@RequestParam String username, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        ApiResult apiResult = userService.queryUserByUsername(username);
        if (!apiResult.ok) {
            return RespResult.fail(apiResult.message);
        }
        return RespResult.success(apiResult.payload);
    }

    @PostMapping("/login")
    public RespResult login(@RequestBody LoginRequest loginRequest){
        ApiResult apiResult = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (apiResult.ok) {
            return RespResult.success(apiResult.payload);
        } else {
            return RespResult.fail(apiResult.message);
        }
    }

    @GetMapping("/getEmailCode")
    public RespResult getJustifyCode(@RequestParam String email) {
        ApiResult apiResult = userService.sendEmailJustifyCode(email);
        if (apiResult.ok) {
            return RespResult.success(apiResult.payload);
        } else {
            return RespResult.fail(apiResult.message);
        }
    }

    @PostMapping("/register")
    public RespResult userRegister(@RequestBody UserRegister userRegister) {
        ApiResult apiResult = userService.userRegister(userRegister.getUsername(), userRegister.getPassword(), userRegister.getEmail(), userRegister.getEmailCode(), userRegister.getLastEmailCode());
        if (apiResult.ok) {
            return RespResult.success();
        } else {
            return RespResult.fail(apiResult.message);
        }
    }
}
