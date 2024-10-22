package org.zjubs.pricecomwebbackend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zjubs.pricecomwebbackend.entity.Good;

import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDCrawlerUtil {

    private static final String JD_SEARCH_URL = "https://search.jd.com/Search?keyword=";
    private static final String thor = "A8248FF840DAA128CB32CB9DD32D50475F0766FF5DADED4CCD580A06C3FEC30BB07CA795456E1333F1599B80F906C0BDF2BE9BFBCF486B526F38D863BE62118D57BDD846C123665160C0C972C1AB4C7435AEF7471E5A276D56ACA791397EE40A8D105D3276349797A8FA0FA62EB28BE9F05F64A3ABFBBE2D88A669FF847EDEF5C7EF3AF6566F53F72984733F228FF0C07E6A6823938815D1AB75DB5A56328B6A";

    public static List<Good> searchAndInsert(String name) {
        String url = JD_SEARCH_URL + name;
        List<Good> goodList = new ArrayList<Good>();
        try {
            Map<String, String> cookies = new HashMap<String, String>();
            cookies.put("thor", thor);
            Document document = Jsoup.connect(url).cookies(cookies).get();
            Elements ul = document.getElementsByClass("gl-warp clearfix");
            Elements liList = ul.select("li");
            for (Element element : liList) {
                // 过滤内层标签
                if ("ps-item".equals(element.attr("class"))) {
                    continue;
                }
                String sku = element.attr("data-sku"); // 获取 data-sku 属性值
                String detailUrl = "https://item.jd.com/" + sku + ".html";
                String pict = element.getElementsByTag("img").first().attr("data-lazy-img");
                String priceText = element.getElementsByClass("p-price").first().text();
                String price = extractNumber(priceText);
                String shopName = element.getElementsByClass("p-shop").first().text();
                String description = element.getElementsByTag("em").last().text();
                Good good = new Good();
                good.setDetailUrl(detailUrl);
                good.setQueryName(name);
                good.setDescription(description);
                good.setImg(pict);
                good.setPrice(Double.valueOf(price));
                good.setShopName(shopName);
                goodList.add(good);
            }
            return goodList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getSingleGoodPrice (String url) {
        try {
//            Map<String, String> cookies = new HashMap<String, String>();
//            cookies.put("thor", thor);
//            Document document = Jsoup.connect(url).cookies(cookies).get();
//            System.out.println(document);
            // 设置 EdgeDriver 路径
            URL resource = JDCrawlerUtil.class.getClassLoader().getResource("msedgedriver.exe");
            if (resource == null) {
                throw new IllegalStateException("msedgedriver.exe not found in resources");
            }
            System.setProperty("webdriver.edge.driver", resource.getPath());
            // 配置 EdgeOptions
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=old"); // 无头模式
            // 初始化 WebDriver
            WebDriver driver = new EdgeDriver(options);
            // 添加 cookies
//            for (Map.Entry<String, String> entry : cookies.entrySet()) {
//                Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
//                driver.manage().addCookie(cookie);
//            }
            driver.get(url);
            // 使用 WebDriverWait 等待页面加载完成
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 等待10秒
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("p-price"))); // 等待价格元素加载
            // 获取页面源码
            String pageSource = driver.getPageSource();
            driver.quit();
            // 解析页面源码
            Document document = Jsoup.parse(pageSource);
//            System.out.println(document);
//            System.out.println("------------------------------");
            Elements priceElements = document.getElementsByClass("p-price");
            System.out.println(priceElements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
