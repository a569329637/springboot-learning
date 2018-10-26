package com.gsq.learning.sharding.service;

import com.gsq.learning.sharding.entity.Order;
import com.gsq.learning.sharding.entity.OrderItem;
import com.gsq.learning.sharding.repository.OrderItemRepository;
import com.gsq.learning.sharding.repository.OrderRepository;
import io.shardingsphere.api.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guishangquan
 * @date 2018/10/17
 */
@Service
public class DemoService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Value("${spring.profiles.active}")
    private String active;

    public void demo() {

        orderRepository.createIfNotExistsTable();
        orderItemRepository.createIfNotExistsTable();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(1000 + i);
            order.setStatus("INSERT_TEST");
            preHintStrategy();
            orderRepository.insert(order);
            long orderId = order.getOrderId();
            orderIds.add(orderId);

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(1000 + i);
            preHintStrategy();
            orderItemRepository.insert(item);
        }
        System.out.println(orderItemRepository.selectAll());
//        System.out.println("2.Delete--------------");
//        for (Long each : orderIds) {
//            orderRepository.delete(each);
//            orderItemRepository.delete(each);
//        }
//        System.out.println(orderItemRepository.selectAll());
//        orderItemRepository.dropTable();
//        orderRepository.dropTable();
    }

    private void preHintStrategy() {
        // 每次执行完数据库操作都会自动 instance.close()，所以每次操作之前需要设置一次
        if ("sharding-hint-strategy".equals(active)) {
            HintManager instance = HintManager.getInstance();
            instance.addTableShardingValue("t_order", 2018L);
            instance.addTableShardingValue("t_order_item", 2019L);
        }
    }
}
