package com.gsq.learning.mq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class SimpleConsumerListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(SimpleConsumerListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (msgs.isEmpty()) {
            logger.info("simple receive msgs is empty, return success");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        // 默认msgs里只有一条消息
        MessageExt msg = msgs.get(0);
        logger.info("simple receive msgs[0] = {}", msg.toString());

        // todo幂等处理

        // 获取该消息重试次数，消息已经重试了3次，如果不需要再次消费，则返回成功
        int reconsumeTimes = msg.getReconsumeTimes();
        if (reconsumeTimes == 3) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        // todo业务处理
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
