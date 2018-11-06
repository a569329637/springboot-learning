package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 可靠的同步传输
 * 应用：可靠的同步传输广泛应用于重要通知消息，短信通知，短信营销系统等
 */
@Service
public class SyncProducer {

    private Logger logger = LoggerFactory.getLogger(SyncProducer.class);

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        // 同步模式下，发送失败重试次数
        producer.setRetryTimesWhenSendFailed(2);
        // 每个主题默认创建的队列数
        producer.setDefaultTopicQueueNums(4);
        // 发送消息超时
        producer.setSendMsgTimeout(3000);

        producer.start();
        for (int i = 0; i < 3; ++ i) {
            Message msg = new Message(
                    "TopicTest",
                    "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 设置消息延时10s投递，messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            // msg.setDelayTimeLevel(3);
            SendResult sendResult = producer.send(msg);
            logger.info("同步消息发送成功：sendResult = {}", sendResult);
        }
        //batchSend(producer);
        producer.shutdown();
    }

    /**
     * 批量发送消息可提高单次发送消息的性能
     */
    public void batchSend(DefaultMQProducer producer) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("TopicTest", "TagA", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID003", "Hello world 2".getBytes()));
        try {
            // 批量发送
            // 相同批次的消息应具有：相同的主题，相同的等待消息处理成功但是不支持定时处理
            // 一个批量的消息的总大小不要错过1MB
            SendResult sendResult = producer.send(messages);
            logger.info("批量消息发送成功：sendResult = {}", sendResult);
        } catch (MQClientException | RemotingException | InterruptedException | MQBrokerException e) {
            e.printStackTrace();
        }
    }
}
