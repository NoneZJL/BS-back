package org.zjubs.pricecomwebbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zjubs.pricecomwebbackend.entity.User;
import org.zjubs.pricecomwebbackend.query.ApiResult;

@Service
public class UserService {
    @Autowired
    org.zjubs.pricecomwebbackend.mapper.UserMapper userMapper;

    public ApiResult queryUserByUsername(String username) {
        User user = userMapper.queryUserByUsername(username);
        return new ApiResult(true, user);
    }
}
