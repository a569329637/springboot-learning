package com.gsq.learning.netty.protocol;

import com.gsq.learning.netty.protocol.command.Command;
import com.gsq.learning.netty.protocol.packet.*;
import com.gsq.learning.netty.protocol.serialize.JsonSerializer;
import com.gsq.learning.netty.protocol.serialize.Serializer;
import com.gsq.learning.netty.protocol.serialize.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class ENDEC {

    public static final ENDEC INSTANCE = new ENDEC();

    public static final int MAGIC_NUMBER = 0x12345678;

    private static Map<Byte, Class<? extends Packet>> commandPacketMap;
    private static Map<Byte, Serializer> serializerMap;

    static {
        commandPacketMap = new HashMap<>();
        commandPacketMap.put(Command.ERROR_MESSAGE, ErrorMessagePacket.class);
        commandPacketMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        commandPacketMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        commandPacketMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        commandPacketMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        commandPacketMap.put(Command.PRIVATE_REQUEST, PrivateChatRequestPacket.class);
        commandPacketMap.put(Command.PRIVATE_RESPONSE, PrivateChatResponsePacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(SerializerAlgorithm.JSON, new JsonSerializer());
    }


    public void encode(Packet packet, ByteBuf byteBuf) {

        // 写魔数
        byteBuf.writeInt(MAGIC_NUMBER);

        // 写版本号
        byteBuf.writeByte(packet.getVersion());

        // 写序列化算法
        byteBuf.writeByte(packet.getSerializeAlgorithm());

        // 写命令
        byteBuf.writeByte(packet.getCommand());

        Serializer serializer = getSerializer(packet.getSerializeAlgorithm());
        byte[] bytes = serializer.serialize(packet);

        // 写数据长度
        byteBuf.writeInt(bytes.length);

        // 写数据
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {

        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 读序列化算法
        byte algorithm = byteBuf.readByte();

        // 读命令
        byte command = byteBuf.readByte();

        // 读数据长度
        int length = byteBuf.readInt();

        // 读数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> packetType = getPacketType(command);
        Serializer serializer = getSerializer(algorithm);
        Packet packet = serializer.deserialize(packetType, bytes);

        return packet;
    }

    private Class<? extends Packet> getPacketType(byte command) {
        return commandPacketMap.get(command);
    }

    private Serializer getSerializer(byte algorithm) {
        return serializerMap.get(algorithm);
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername("guishangquan");
        loginRequestPacket.setPassword("123456");

        ENDEC.INSTANCE.encode(loginRequestPacket, byteBuf);

        Packet packet = ENDEC.INSTANCE.decode(byteBuf);
        System.out.println("packet = " + packet);
    }
}
