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
    private static final String thor = "A8248FF840DAA128CB32CB9DD32D50475F0766FF5DADED4CCD580A06C3FEC30B370ECE2926CEEE8926FE64FE76CDF2664334EBEBC32707B47A0DB71C372D0EE7620BD394915626B3D305DB807B3FD47452026FD7BEF48B8D433C28C49567EE8137AA8A83B964F8E48BDB011C46F2E5DA739DF2142D5AF74D98C3BD04146A2A9B6D1AC9B225A2248555790406A1D978011E7721998A84AD0FC3AE3AD6AEBF4D77";

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
