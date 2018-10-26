package com.gsq.learning.sharding.keygen;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;

import java.util.Random;

/**
 * 默认的算法在并发不高的情况基本上都生成偶数
 */
public class CustomKeyGenerator implements KeyGenerator {

    private DefaultKeyGenerator keyGenerator = new DefaultKeyGenerator();
    private Random random = new Random();

    @Override
    public synchronized Number generateKey() {
        Number number = keyGenerator.generateKey();
        if (random.nextBoolean()) {
            return number;
        }
        return number.longValue() | 1;
    }
}
