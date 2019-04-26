package com.gsq.learning.flume.sink;

import com.gsq.learning.flume.sink.properties.RedisProperties;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * http://flume.apache.org/FlumeDeveloperGuide.html#sink
 *
 * 执行顺序，configure，start，process，stop
 * configure，start，stop都只执行一次
 *
 * @author guishangquan
 * @date 2019/4/26
 */
public class CustomSink extends AbstractSink implements Configurable {

    private static final Logger logger = Logger.getLogger(CustomSink.class);

    private int batchSize = 3;
    private RedisProperties redisProperties;

    @Override
    public synchronized void start() {
        logger.info("开始执行start方法");
        logger.info("初始化类");
        super.start();
    }

    @Override
    public synchronized void stop() {
        logger.info("开始执行stop方法");
        logger.info("关闭一些类的连接");
        super.stop();
    }

    public Status process() throws EventDeliveryException {
        logger.info("开始执行process方法");
        //Status status = handleSingleEvent();
        Status status = handleBatchEvent();
        return status;
    }

    public void configure(Context context) {
        logger.info("开始执行configure方法");
        redisProperties = new RedisProperties();
        redisProperties.setHost(context.getString(RedisProperties.hostName));
        redisProperties.setPassword(context.getString(RedisProperties.passwordName));
        redisProperties.setPort(context.getInteger(RedisProperties.portName));
        redisProperties.setDatabase(context.getInteger(RedisProperties.databaseName));
        logger.info("redisProperties = " + redisProperties);
    }

    private Status handleSingleEvent() {
        Status status;
        Event event;

        // Start transaction
        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        txn.begin();
        try {
            // This try clause includes whatever Channel operations you want to do

            event = ch.take();

            // Send the Event to the external repository.
            // storeSomeData(e);
            handleEvent(event);

            txn.commit();
            status = Status.READY;
        } catch (Throwable t) {
            txn.rollback();

            // Log exception, handle individual exceptions as needed

            status = Status.BACKOFF;

            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error)t;
            }
        } finally {
            txn.close();
        }

        return status;
    }

    private Status handleBatchEvent() {
        List<Event> events = new ArrayList<>(batchSize);

        Status status = Status.READY;

        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        txn.begin();
        while (events.size() < batchSize) {
            Event event = ch.take();
            if (event == null) {
                status = Status.BACKOFF;
                System.out.println("take event is null");
                // 这里就不txn.rollback();
                break;
            }
            events.add(event);
        }
        txn.commit();
        txn.close();

        events.forEach(this::handleEvent);
        return status;
    }

    private void handleEvent(Event event) {
        if (event != null) {
            String body;
            try {
                body = new String(event.getBody(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("event转换层string异常，event = " + event);
                return ;
            }

            logger.info("body = " + body);
        }
    }
}
