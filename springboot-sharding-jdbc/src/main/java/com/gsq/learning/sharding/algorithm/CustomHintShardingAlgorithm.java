package com.gsq.learning.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author guishangquan
 * @date 2018/10/26
 */
public class CustomHintShardingAlgorithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ShardingValue shardingValue) {
        System.out.println("availableTargetNames = " + availableTargetNames + ", shardingValue = " + shardingValue);

        if (shardingValue instanceof ListShardingValue) {
            ListShardingValue v = (ListShardingValue) shardingValue;
            System.out.println("v = " + v);
        }

        Collection<String> names = new ArrayList<>();
        for (String name : availableTargetNames) {
            if ("t_order_2".equals(name)) {
                names.add(name);
            }
            if ("t_order_item_2".equals(name)) {
                names.add(name);
            }
        }
        System.out.println("return names = " + names);
        return names;
    }
}
