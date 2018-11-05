package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 可靠的同步传输
 * 应用：可靠的同步传输广泛应用于重要通知消息，短信通知，短信营销系统等
 */
@Service
public class SyncProducer {

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());

        producer.start();
        for (int i = 0; i < 3; ++ i) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

}
