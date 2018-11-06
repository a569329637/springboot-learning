package com.gsq.learning.mq.simple;

import com.gsq.learning.mq.config.RocketMqProperties;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author guishangquan
 * @date 2018/11/6
 */
@Service
public class TransactionProducer {

    private Logger logger = LoggerFactory.getLogger(TransactionProducer.class);

    @Autowired
    private RocketMqProperties rocketMqProperties;

    /**
     * 生产者本地事务成功
     * @throws Exception
     */
    public void send() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setTransactionListener(new MyTransactionListener());
        producer.start();

        String orderId = "OrderId:10000";
        Message msg = new Message(
                "TopicTransaction",
                "TagA",
                orderId,
                "Hello RocketMQ, Transaction".getBytes(RemotingHelper.DEFAULT_CHARSET));
        TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(msg, orderId);
        logger.info("事务消息发送成功：transactionSendResult = {}", transactionSendResult);

        producer.shutdown();
    }

    /**
     * 生产者本地事务失败
     * @throws Exception
     */
    public void send1() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                logger.info("执行本地事务：msg = {}, arg={}", msg, arg);
                for (int i = 0; i < 5; ++ i) {
                    logger.info("执行本地事务...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("执行本地事务：失败");
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                logger.info("执行本地事务检查：msg = {}", msg);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
        producer.start();

        String orderId = "OrderId:10001";
        Message msg = new Message(
                "TopicTransaction",
                "TagA",
                orderId,
                "Hello RocketMQ, Transaction 1".getBytes(RemotingHelper.DEFAULT_CHARSET));
        TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(msg, orderId);
        logger.info("事务消息发送失败：transactionSendResult = {}", transactionSendResult);

        producer.shutdown();
    }

    /**
     * 生产者本地事务超时，回查
     * @throws Exception
     */
    public void send2() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group_name");
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                logger.info("执行本地事务：msg = {}, arg={}", msg, arg);
                logger.info("执行本地事务...");
                try {
                    // 模拟超时
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("执行本地事务终于成功");
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                logger.info("执行本地事务回查：msg = {}", msg);
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();

        String orderId = "OrderId:10002";
        Message msg = new Message(
                "TopicTransaction",
                "TagA",
                orderId,
                "Hello RocketMQ, Transaction 2".getBytes(RemotingHelper.DEFAULT_CHARSET));
        TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(msg, orderId);
        logger.info("事务消息发送成功：transactionSendResult = {}", transactionSendResult);

        producer.shutdown();
    }
}
