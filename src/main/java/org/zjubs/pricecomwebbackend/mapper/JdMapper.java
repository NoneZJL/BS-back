package org.zjubs.pricecomwebbackend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zjubs.pricecomwebbackend.entity.Good;

import java.util.List;

@Mapper
public interface JdMapper {

    @Insert("insert into jd_items (price, description, img, shop_name, query_name) value (#{price}, #{description}, #{img}, #{shopName}, #{queryName})")
    public void insetItems(Good good);

    @Select("select * from jd_items where query_name = #{name}")
    public List<Good> queryGoodsBySearchingName(String name);
}