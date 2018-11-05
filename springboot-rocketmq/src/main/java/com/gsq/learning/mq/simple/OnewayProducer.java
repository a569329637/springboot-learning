package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单向传输
 * 应用：单向传输用于需要中等可靠性的情况，例如日志收集
 */
@Service
public class OnewayProducer {

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());

        producer.start();
        for (int i = 0; i < 3; ++ i) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }
}
