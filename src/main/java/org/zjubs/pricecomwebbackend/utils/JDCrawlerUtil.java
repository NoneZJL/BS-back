package org.zjubs.pricecomwebbackend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JDCrawlerUtil {

    private static final String JD_SEARCH_URL = "https://search.jd.com/Search?keyword=";

    public static void search(String name) {
        try {
            // 生成搜索URL
            String productName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            String searchUrl = JD_SEARCH_URL + productName;
            // 使用Jsoup获取HTML文档
            Document doc = Jsoup.connect(searchUrl).get();
            // 获取商品列表
            Elements productList = doc.select("li.gl-item");
            for (Element product : productList) {
                // 获取商品名称
                String title = product.select("div.p-name a em").text();
                // 获取商品价格
                String price = product.select("div.p-price strong i").text();
                // 获取商品链接
                String productUrl = product.select("div.p-name a").attr("href");
                // 打印商品信息
                System.out.println("商品名称: " + title);
                System.out.println("商品价格: " + price);
                System.out.println("商品链接: " + productUrl);
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
