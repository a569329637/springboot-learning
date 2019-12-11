package com.gsq.learning.netty.protocol.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        String jsonString = JSON.toJSONString(object);
        return jsonString.getBytes();
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        String jsonString = new String(bytes);
        return JSON.parseObject(jsonString, clazz);
    }
}
