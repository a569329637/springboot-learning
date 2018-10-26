package com.gsq.learning.sharding.repository;

import com.gsq.learning.sharding.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author guishangquan
 * @date 2018/10/17
 */
@Mapper
public interface OrderItemRepository {

    void createIfNotExistsTable();

    void truncateTable();

    Long insert(OrderItem model);

    void delete(Long orderItemId);

    List<OrderItem> selectAll();

    void dropTable();
}
