package org.zjubs.pricecomwebbackend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.zjubs.pricecomwebbackend.entity.Good;

@Mapper
public interface WphMapper {
    @Insert("insert into wph_items (price, description, img, shop_name, query_name) value (#{price}, #{description}, #{img}, #{shopName}, #{queryName})")
    public void insetItems(Good good);
}
