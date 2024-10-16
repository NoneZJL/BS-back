package org.zjubs.pricecomwebbackend.CrawerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zjubs.pricecomwebbackend.entity.Good;
import org.zjubs.pricecomwebbackend.query.ApiResult;
import org.zjubs.pricecomwebbackend.service.JdService;
import org.zjubs.pricecomwebbackend.utils.JDCrawlerUtil;

import java.util.List;

@SpringBootTest
public class JsoupTest {
    @Autowired
    private JdService jdService;

    String url = "https://search.jd.com/Search?keyword=";

    @Test
    public void testGetUrl() {
        ApiResult apiResult = jdService.searchAndInsert("餐巾纸");
        if (apiResult.ok) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
    }
}
