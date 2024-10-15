package org.zjubs.pricecomwebbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjubs.pricecomwebbackend.entity.History;
import org.zjubs.pricecomwebbackend.mapper.UsingMapper;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.utils.JWTUtil;

import java.util.List;

@Service
public class UsingService {
    @Autowired
    private UsingMapper usingMapper;

    public ApiResult getAllHistoryByUserId(String token) {
        try {
            try {
                JWTUtil.verify(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ApiResult.notLogin();
            }
            Integer id = JWTUtil.getIdByToken(token);
            List<History> history = usingMapper.getAllHistoryByUserId(id);
            return ApiResult.success(history);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    public ApiResult updateHistoryByUserIdAndContent(String token, String content) {
        try {
            try {
                JWTUtil.verify(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ApiResult.notLogin();
            }
            Integer id = JWTUtil.getIdByToken(token);
            History history = usingMapper.queryHistoryByUserIdAndContent(id, content);
            if (history != null) {
                usingMapper.deleteHistoryByUserIdAndContent(id, content);
            }
            usingMapper.insertQueryHistory(id, content);
            return ApiResult.success();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    public ApiResult deleteSingleHistoryByUserIdAndContent(String token, String content) {
        try {
            try {
                JWTUtil.verify(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ApiResult.notLogin();
            }
            Integer id = JWTUtil.getIdByToken(token);
            usingMapper.deleteHistoryByUserIdAndContent(id, content);
            return ApiResult.success();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    public ApiResult deleteAllHistoryByUserId(String token) {
        try {
            try {
                JWTUtil.verify(token);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ApiResult.notLogin();
            }
            Integer id = JWTUtil.getIdByToken(token);
            usingMapper.deleteAllHistoryByUserId(id);
            return ApiResult.success();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }
}
