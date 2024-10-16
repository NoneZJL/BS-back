package org.zjubs.pricecomwebbackend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.zjubs.pricecomwebbackend.entity.Good;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDCrawlerUtil {

    private static final String JD_SEARCH_URL = "https://search.jd.com/Search?keyword=";
    private static final String thor = "9BE2381B3C9F2A1D41C64A3F5A8584640F9F10C2F05C1FE36F26A033AA45FAE73712AF3404EA8E52ADF70C4B15D8C29B34593981E4FD5314751225BC0A5D3ABC25812F2223397F6C6F31E777005B96399D2EDFB6631B17CEC2181383C9788E534253347B4277F0C2E3637BA1E0C885040982711A04928BF5484021F9B25C8374E2565531440A542840F4DD92EBC648C2448B0F5AC4C407E1F4A01AC4FE7E584B";

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
//                System.out.println("------------------");
                // 过滤内层标签
                if ("ps-item".equals(element.attr("class"))) {
                    continue;
                }
                String pict = element.getElementsByTag("img").first().attr("data-lazy-img");
                String priceText = element.getElementsByClass("p-price").first().text();
                String price = extractNumber(priceText);
                String shopName = element.getElementsByClass("p-shop").first().text();
                String description = element.getElementsByTag("em").last().text();
//                System.out.println(element);
//                String pict = Objects.requireNonNull(element.getElementsByTag("img").first()).attr("data-lazy-img");
//                String priceText = Objects.requireNonNull(element.getElementsByClass("p-price").first()).text();
//                String price = extractNumber(priceText);
//                String shopName = Objects.requireNonNull(element.getElementsByClass("p-shop").first()).text();
//                String description = Objects.requireNonNull(element.getElementsByTag("em").last()).text();
//                System.out.println("img = " + pict);
//                System.out.println("price = " + price);
//                System.out.println("shopName = " + shopName);
//                System.out.println("name = " + description);
                Good good = new Good();
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

    private static String extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
