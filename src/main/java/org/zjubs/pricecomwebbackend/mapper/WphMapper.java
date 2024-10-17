package org.zjubs.pricecomwebbackend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.zjubs.pricecomwebbackend.entity.Good;

@Mapper
public interface WphMapper {
    @Insert("insert into wph_items (price, description, img, shop_name, query_name, detail_url) value (#{price}, #{description}, #{img}, #{shopName}, #{queryName}, #{detailUrl})")
    public void insetItems(Good good);
}
