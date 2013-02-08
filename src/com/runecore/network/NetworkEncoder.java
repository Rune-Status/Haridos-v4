package com.runecore.network;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.runecore.network.io.Message;
import com.runecore.network.io.Message.PacketType;
import com.runecore.network.io.MessageBuilder;

/**
 * NetworkEncoder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class NetworkEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
	if(msg instanceof ChannelBuffer) {
	    return msg;
	}
	Message packet = null;
	if(msg instanceof MessageBuilder) {
		packet = ((MessageBuilder) msg).toMessage();
	} else {
		packet = (Message) msg;
	}
	if(!packet.isRaw()) {
		int packetLength = 1 + packet.getLength() + packet.getType().getSize();
		ChannelBuffer response = ChannelBuffers.buffer(packetLength);
		response.writeByte((byte) packet.getOpcode());
		if(packet.getType() == PacketType.VAR_BYTE) {
			response.writeByte((byte) packet.getLength());
		} else if(packet.getType() == PacketType.VAR_SHORT) {
			response.writeShort((short) packet.getLength());
		}
		response.writeBytes(packet.getBuffer());
		return response;
	}
	return packet.getBuffer();
    }

}