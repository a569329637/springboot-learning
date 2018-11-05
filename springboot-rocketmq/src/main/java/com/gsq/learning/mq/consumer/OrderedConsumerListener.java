package com.gsq.learning.mq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class OrderedConsumerListener implements MessageListenerOrderly {

    private Logger logger = LoggerFactory.getLogger(OrderedConsumerListener.class);

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        logger.info("order receive msgs[0] = {}", msgs);
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
