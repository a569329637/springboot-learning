package com.gsq.learning.mq.config;

import com.gsq.learning.mq.consumer.OrderedConsumerListener;
import com.gsq.learning.mq.consumer.SimpleConsumerListener;
import com.gsq.learning.mq.consumer.TransactionConsumerListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConfig {

    private Logger logger = LoggerFactory.getLogger(RocketMqConfig.class);

    @Autowired
    private RocketMqProperties rocketMqProperties;

    @Bean("simpleConsumer")
    public DefaultMQPushConsumer getSimpleConsumer() {
        String groupName = "consumer_group_name";
        String topics = "TopicTest";
        String tags = "TagA";

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        // 设置消费模型，集群还是广播，默认为集群
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 消费最小线程数
        consumer.setConsumeThreadMin(4);
        // 消费最大线程数
        consumer.setConsumeThreadMax(4);
        // 设置消费者监听
        consumer.setMessageListener(new SimpleConsumerListener());
        // ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET 第一次启动从队列尾部开始消费，以后启动从上次停止的地方开始消费
        // ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET 第一次启动从队列头部开始消费，以后启动从上次停止的地方开始消费
        // ConsumeFromWhere.CONSUME_FROM_TIMESTAMP 第一次启动将在指定时间戳开始消费，以后启动从上次停止的地方开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 消费者负载均衡算法
        consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragely());
        // 消费进度存储
        //consumer.setOffsetStore();
        // 设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(1);
        // 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
        try {
            consumer.subscribe(topics, tags);
            consumer.start();
            logger.info("simple consumer start success !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
        } catch (MQClientException e) {
            logger.error("simple consumer start exception !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
            e.printStackTrace();
        }
        return consumer;
    }

    @Bean("orderConsumer")
    public DefaultMQPushConsumer getOrderConsumer() {
        String groupName = "order_group_name";
        String topics = "TopicOrderTest";
        String tags = "TagA";

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        consumer.setMessageListener(new OrderedConsumerListener());
        try {
            consumer.subscribe(topics, tags);
            consumer.start();
            logger.info("order consumer start success !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
        } catch (MQClientException e) {
            logger.info("order consumer start exception !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
            e.printStackTrace();
        }
        return consumer;
    }

    @Bean("transactionConsumer")
    public DefaultMQPushConsumer getTransactionConsumer() {
        String groupName = "transaction_group_name";
        String topics = "TopicTransaction";
        String tags = "*";

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        consumer.setMessageListener(new TransactionConsumerListener());
        try {
            consumer.subscribe(topics, tags);
            consumer.start();
            logger.info("transaction consumer start success !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
        } catch (MQClientException e) {
            logger.info("transaction consumer start exception !!! GroupName:{}, Topics:{}, Tags:{}, namesrvAddr:{}", groupName, topics, tags, rocketMqProperties.getNamesrvAddr());
            e.printStackTrace();
        }
        return consumer;
    }
}
