package com.gsq.learning.netty.protocol.packet;

import com.alibaba.fastjson.annotation.JSONField;
import com.gsq.learning.netty.protocol.serialize.SerializerAlgorithm;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public interface Packet {

    @JSONField(serialize = false)
    byte getCommand();

    @JSONField(serialize = false)
    default byte getVersion() {
        return 1;
    }

    @JSONField(serialize = false)
    default byte getSerializeAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

}
