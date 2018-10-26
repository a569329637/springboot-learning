package com.gsq.learning.sharding.repository;

import com.gsq.learning.sharding.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author guishangquan
 * @date 2018/10/17
 */
@Mapper
public interface OrderRepository {

    void createIfNotExistsTable();

    void truncateTable();

    Long insert(Order model);

    void delete(Long orderId);

    void dropTable();
}
