package com.gsq.learning.netty.protocol.packet;

import com.gsq.learning.netty.protocol.command.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@Getter
@Setter
public class ErrorMessagePacket implements Packet {

    private String errorMessage;

    @Override
    public byte getCommand() {
        return Command.ERROR_MESSAGE;
    }
}
