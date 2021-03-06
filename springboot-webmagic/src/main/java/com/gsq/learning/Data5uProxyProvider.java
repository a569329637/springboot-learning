package com.gsq.learning;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

/**
 * @author guishangquan
 * @date 2019-10-09
 */
public class Data5uProxyProvider implements ProxyProvider {

    private Logger logger = LoggerFactory.getLogger(Data5uProxyProvider.class);

    private String apiUrl;

    private Long previousGetTime;

    private int ttl;

    private Proxy proxy;

    public Data5uProxyProvider(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public static Data5uProxyProvider from(String apiUrl) {
        return new Data5uProxyProvider(apiUrl);
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public Proxy getProxy(Task task) {
        long currentTime = System.currentTimeMillis();
        if (proxy != null && previousGetTime != null && (currentTime - previousGetTime < ttl - 10)) {
            return proxy;
        }
        // TODO
        //String asString = Request.Get(apiUrl).execute().returnContent().asString(Charset.forName("UTF-8"));
        String asString = "sdjfklsdjfklsdj";
        Data5uReturnContent data5uReturnContent = decodeReturnText(asString);
        if (data5uReturnContent != null) {
            previousGetTime = currentTime;
            ttl = data5uReturnContent.getTtl();
            proxy = new Proxy(data5uReturnContent.getIp(), data5uReturnContent.getPort());
            return proxy;
        }

        return null;
    }

    private Data5uReturnContent decodeReturnText(String returnText) {
        if (StringUtils.isNotBlank(returnText)) {
            String[] proxyTtl = StringUtils.split(returnText, ",");
            String proxyText = proxyTtl[0].trim();
            int ttl = Integer.parseInt(proxyTtl[1].trim());

            String[] ipPort = StringUtils.split(proxyText, ":");
            String ip = ipPort[0].trim();
            int port = Integer.parseInt(ipPort[1].trim());

            return new Data5uReturnContent(ip, port, ttl);
        }
        return null;
    }

    class Data5uReturnContent {

        public Data5uReturnContent() {
        }

        public Data5uReturnContent(String ip, int port, int ttl) {
            this.ip = ip;
            this.port = port;
            this.ttl = ttl;
        }

        private String ip;

        private int port;

        private int ttl;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }
    }
}
