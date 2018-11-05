package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 可靠的异步传输
 * 应用：异步传输通常用于响应时间敏感的业务场景
 */
@Service
public class AsyncProducer {

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        // 发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);

        producer.start();
        for (int i = 0; i < 100; ++ i) {
            final int index = i;
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188_" + i,
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 直接shutdown会报错
        Thread.sleep(10000);
        producer.shutdown();
    }
}
