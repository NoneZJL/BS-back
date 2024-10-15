package org.zjubs.pricecomwebbackend.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zjubs.pricecomwebbackend.entity.History;

import java.util.List;

@Mapper
public interface UsingMapper {
    @Select("select * from history where user_id = #{id} order by time desc")
    List<History> getAllHistoryByUserId(Integer id);

    @Select("select * from history where user_id = #{id} and content = #{content}")
    History queryHistoryByUserIdAndContent(Integer id, String content);

    @Delete("delete from history where user_id = #{id} and content = #{content}")
    void deleteHistoryByUserIdAndContent(Integer id, String content);

    @Insert("insert into history (user_id, content) VALUE (#{id}, #{content})")
    void insertQueryHistory(Integer id, String content);

    @Delete("delete from history where user_id = #{id}")
    void deleteAllHistoryByUserId(Integer id);
}
