package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 可靠的异步传输
 * 应用：异步传输通常用于响应时间敏感的业务场景
 */
@Service
public class AsyncProducer {

    private Logger logger = LoggerFactory.getLogger(AsyncProducer.class);

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        // 异步模式下，发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(2);

        producer.start();
        for (int i = 0; i < 100; ++ i) {
            final int index = i;
            Message msg = new Message(
                    "TopicTest",
                    "TagA",
                    "OrderID188_" + i,
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("异步消息发送成功：index = {}, sendResult = {}", index, sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    logger.info("异步消息发送异常：index = {}, sendResult = {}", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 直接shutdown会报错
        Thread.sleep(10000);
        producer.shutdown();
    }
}
