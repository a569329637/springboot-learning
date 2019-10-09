package com.gsq.learning;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-10-09
 */
public class TxPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private final static String JSONP_PREFIX = "jQuery19105435122194473212_1570587525698(";

    @Override
    public void process(Page page) {

        Selectable url = page.getUrl();
        page.putField("url", url);

        String rawText = page.getRawText();
        page.putField("rawText", rawText);

        // https://my.oschina.net/anxiaole/blog/782026
        String json = rawText.substring(JSONP_PREFIX.length(), rawText.length() - 2);
        page.putField("json", json);

        JsonPathSelector jsonPathSelector = new JsonPathSelector("$.data.vecVidInfo[*].vid");
        List<String> vids = jsonPathSelector.selectList(json);
        page.putField("vids", vids);

//        Json json = page.getJson();
//        System.out.println("json = " + json);
//        Html html = page.getHtml();
//        System.out.println("html = " + html);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "http://access.video.qq.com/pc_client/GetUserVidListPage?vappid=50662744&vsecret=64b037e091deae75d3840dbc5d565c58abe9ea733743bbaf&callback=jQuery19105435122194473212_1570587525698&iSortType=0&page_index=2&hasMore=true&stUserId=3403535934&page_size=10&_=1570587525702";

        Spider.create(new TxPageProcessor())
                .setDownloader(new HttpClientDownloader())
                .addUrl(url)
                .addPipeline(new MyConsolePipeline())
                .thread(1)
                .run();
    }
}
