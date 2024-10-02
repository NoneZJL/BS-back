package org.zjubs.pricecomwebbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.query.RespResult;
import org.zjubs.pricecomwebbackend.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/query")
    public RespResult queryUserByUsername(@RequestParam String username) {
        ApiResult apiResult = userService.queryUserByUsername(username);
        if (!apiResult.ok) {
            return RespResult.fail(apiResult.message);
        }
        return RespResult.success(apiResult.payload);
    }

}
