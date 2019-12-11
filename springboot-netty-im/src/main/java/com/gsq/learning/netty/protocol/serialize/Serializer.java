package com.gsq.learning.netty.protocol.serialize;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public interface Serializer {

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
