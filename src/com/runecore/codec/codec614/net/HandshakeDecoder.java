package com.runecore.codec.codec614.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.runecore.codec.codec614.js5.AuthenticationPacket;
import com.runecore.codec.codec614.js5.JS5RequestDecoder;

/**
 * HandshakeDecoder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class HandshakeDecoder extends FrameDecoder {

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
	if(ctx.getPipeline().get(HandshakeDecoder.class) != null)
	    ctx.getPipeline().remove(this);
	int service = buffer.readByte();
	if(service == 15) {
	    int version = buffer.readInt();
	    ctx.getPipeline().addBefore("encoder", "decoder", new JS5RequestDecoder());
	    return new AuthenticationPacket(version);
	} else {
	    System.out.println("Service: "+service);
	}
	return null;
    }

}
