package org.zjubs.pricecomwebbackend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zjubs.pricecomwebbackend.entity.Good;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WphCrawlerUtil {
    private static final String BASE_URL = "https://category.vip.com/suggest.php?keyword=";

    public static List<Good> search(String name) {
        String url = BASE_URL + name;
        List<Good> goodList = new ArrayList<Good>();
        try {
//            Document document = Jsoup.connect(url).get();
//            System.out.println(document);
            URL resource = SnCrawlerUtil.class.getClassLoader().getResource("msedgedriver.exe");
            if (resource == null) {
                throw new IllegalStateException("msedgedriver.exe not found in resources");
            }
            // 设置 EdgeDriver 路径
            System.setProperty("webdriver.edge.driver", resource.getPath());
            // 创建 EdgeOptions 对象
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=old"); // 无头模式
            // 创建 WebDriver 对象
            WebDriver driver = new EdgeDriver(options);
            // 打开页面
            driver.get(url);
            // 等待页面加载完成
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".c-goods-item__img img.lazy")));
            // 获取页面源代码
            String pageSource = driver.getPageSource();
            // 关闭 WebDriver
            driver.quit();
            Document document = Jsoup.parse(pageSource);
            Elements goodsItems = document.select(".c-goods-item");
            // 遍历每个商品项
            int i = 0;
            for (Element goodsItem : goodsItems) {
                // 提取商品名称
                String productName = goodsItem.select(".c-goods-item__name").text();
                // 提取特卖价
                String salePrice = goodsItem.select(".c-goods-item__sale-price").text();
//                if (salePrice == null || salePrice.isEmpty()) {
//                    continue;
//                }
                String price = extractNumber(salePrice);
                // 提取市场价
                // String marketPrice = goodsItem.select(".c-goods-item__market-price").text();
                // 提取折扣
                // String discount = goodsItem.select(".c-goods-item__discount").text();
                // 提取图片路径
                String imageUrl = goodsItem.select(".c-goods-item__img img.lazy").attr("data-original");
                // 提取商品链接
                String productLink = goodsItem.select("a").attr("href");
                // 输出商品信息
                Good good = new Good();
                good.setQueryName(name);
                good.setDescription(productName);
                good.setImg(imageUrl);
                good.setDetailUrl(productLink);
                if (!price.isEmpty()) {
                    good.setPrice(Double.valueOf(price));
                } else {
                    good.setPrice(0.0); // 设置默认价格
                }
                goodList.add(good);
                i++;
                if (i == 30) {
                    break;
                }
            }
            return goodList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static String extractNumber(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.replaceAll("[^0-9.]", "");
    }
}
