package com.gsq.learning.netty.protocol.packet;

import com.gsq.learning.netty.protocol.command.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
@Getter
@Setter
public class LoginResponsePacket implements Packet {

    private String userId;
    private Boolean isSuccess;

    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
