package com.gsq.learning;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-10-09
 */
public class IpPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

        // 匹配一个
        Selectable ip = page.getHtml().xpath("//body/p/text()");
        System.out.println("ip = " + ip);
        page.putField("ip", ip);
        // 匹配所有
        List<String> all = page.getHtml().xpath("//body/p/text()").all();
        for (String url : all) {
            System.out.println("url = " + url);
        }


        // 只能获取 a 标签的内容，a 标签里面标签的内容无法获取
        Selectable desc = page.getHtml().xpath("//body/p/a/text()");
        System.out.println("desc = " + desc);
        page.putField("desc", desc);
        // 内容和上面的一样
        Selectable green = page.getHtml().css("a.green", "text");
        System.out.println("green = " + green);


        // 获取连接
        List<String> links = page.getHtml().css("a.red").links().all();
        for (String link : links) {
            System.out.println("link = " + link);
        }
        page.putField("links", links);

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        // http://www.data5u.com/
        //SimpleProxyProvider simpleProxyProvider = SimpleProxyProvider.from(new Proxy("182.92.242.11", 80));
        //httpClientDownloader.setProxyProvider(simpleProxyProvider);

        Spider.create(new IpPageProcessor())
                .setDownloader(httpClientDownloader)
                .addUrl("http://2000019.ip138.com/")
                .addPipeline(new MyConsolePipeline())
                .thread(1)
                .run();
    }
}
