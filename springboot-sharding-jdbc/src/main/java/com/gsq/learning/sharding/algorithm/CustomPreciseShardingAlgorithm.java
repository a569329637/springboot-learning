package com.gsq.learning.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @author guishangquan
 * @date 2018/10/26
 */
public class CustomPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("availableTargetNames = " + availableTargetNames + ", shardingValue = " + shardingValue);

        for (String name : availableTargetNames) {
            // 实现了类似「Inline」配置的功能
            if (name.endsWith(shardingValue.getValue() % availableTargetNames.size() + "")) {
                System.out.println("return name = " + name);
                return name;
            }
        }
        return null;
    }
}
