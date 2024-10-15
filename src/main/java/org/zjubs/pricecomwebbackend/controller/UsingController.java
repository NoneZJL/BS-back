package org.zjubs.pricecomwebbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.query.RespResult;
import org.zjubs.pricecomwebbackend.service.UsingService;
import org.zjubs.pricecomwebbackend.utils.JWTUtil;

@RestController
public class UsingController {
    @Autowired
    private UsingService usingService;

    @GetMapping("/getAllHistory")
    public RespResult getAllHistoryByUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ApiResult apiResult = usingService.getAllHistoryByUserId(token);
        if (apiResult.ok) {
            return RespResult.success(apiResult.payload);
        } else {
            if (JWTUtil.checkNotLogin(apiResult)) {
                return RespResult.notLogin();
            }
            return RespResult.fail(apiResult.message);
        }
    }

    @GetMapping("/queryGood")
    public RespResult queryGood(@RequestParam String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ApiResult apiResult = usingService.updateHistoryByUserIdAndContent(token, name);
        if (apiResult.ok) {
            return RespResult.success();
        } else {
            if (JWTUtil.checkNotLogin(apiResult)) {
                return RespResult.notLogin();
            }
            return RespResult.fail(apiResult.message);
        }
    }

    @GetMapping("/deleteSingleHistory")
    public RespResult deleteSingleHistory(@RequestParam String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ApiResult apiResult = usingService.deleteSingleHistoryByUserIdAndContent(token, name);
        if (apiResult.ok) {
            return RespResult.success();
        } else {
            if (JWTUtil.checkNotLogin(apiResult)) {
                return RespResult.notLogin();
            }
            return RespResult.fail(apiResult.message);
        }
    }

    @GetMapping("/deleteAllHistory")
    public RespResult deleteAllHistory(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ApiResult apiResult = usingService.deleteAllHistoryByUserId(token);
        if (apiResult.ok) {
            return RespResult.success();
        } else {
            if (JWTUtil.checkNotLogin(apiResult)) {
                return RespResult.notLogin();
            }
            return RespResult.fail(apiResult.message);
        }
    }
}
