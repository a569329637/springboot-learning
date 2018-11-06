package com.gsq.learning.mq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/11/6
 */
public class TransactionConsumerListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(TransactionConsumerListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        logger.info("事务消息消费成功：msgs = {}", msgs);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
