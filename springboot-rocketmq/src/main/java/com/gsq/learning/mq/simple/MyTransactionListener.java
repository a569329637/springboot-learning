package com.gsq.learning.mq.simple;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guishangquan
 * @date 2018/11/6
 */
public class MyTransactionListener implements TransactionListener {

    private Logger logger = LoggerFactory.getLogger(MyTransactionListener.class);

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        logger.info("执行本地事务：msg = {}, arg={}", msg, arg);
        // todo业务代码
        logger.info("执行本地事务：成功");
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        logger.info("执行本地事务检查：msg = {}", msg);
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
