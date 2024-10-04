package org.zjubs.pricecomwebbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zjubs.pricecomwebbackend.entity.User;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    public User queryUserByUsername(String username);

    @Select("select * from user where username = #{username} and password = #{password}")
    public User queryUserByUsernameAndPassword(String username, String password);
}