package com.gsq.learning.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author guishangquan
 * @date 2018/10/26
 */
public class CustomComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        System.out.println("availableTargetNames = " + availableTargetNames + ", shardingValue = " + shardingValues);

        Collection<Long> orderIds = getShardingValues(shardingValues, "order_id");
        Collection<Long> orderItemIds = getShardingValues(shardingValues, "order_item_id");

        List<String> names = new ArrayList<>();
        for (Long orderId : orderIds) {
            for (Long orderItemId : orderItemIds) {
                String suffix = "_" + orderId % 2 + "_" + orderItemId % 2;
                for (String name : availableTargetNames) {
                    if (name.endsWith(suffix)) {
                        names.add(name);
                    }
                }
            }
        }
        System.out.println("return names = " + names);
        return names;
    }

    private Collection<Long> getShardingValues(Collection<ShardingValue> shardingValues, final String shardingKey) {
        for (ShardingValue sv : shardingValues) {
            if (sv instanceof ListShardingValue) {
                if (sv.getColumnName().equals(shardingKey)) {
                    return ((ListShardingValue) sv).getValues();
                }
            }
        }
        return new ArrayList<>();
    }
}
