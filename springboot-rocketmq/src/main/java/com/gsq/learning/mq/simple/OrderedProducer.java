package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 顺序消息
 */
@Service
public class OrderedProducer {

    private Logger logger = LoggerFactory.getLogger(OrderedProducer.class);

    @Autowired
    private RocketMqProperties rocketMqProperties;

    public void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("order_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());

        producer.start();
        int orderId = 100;
        for (int i = 0; i < 100; ++ i) {
            Message msg = new Message(
                    "TopicOrderTest",
                    "TagA",
                    "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                /*
                支持顺序消费，但这个顺序，不是全局顺序，只是分区顺序，要全局顺序只能一个分区
                mqs是多个消息队列，每个队列就是一个分区，同一个消息队列保证FIFO
                select需要保证同一orderId返回的MessageQueue相同
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // arg 就是 orderId
                    Integer id = (Integer) arg;
                    int i = id % mqs.size();
                    return mqs.get(i);
                }
            }, orderId);
            logger.info("顺序消息发送成功：sendResult = {}", sendResult);
        }
        producer.shutdown();
    }

}
