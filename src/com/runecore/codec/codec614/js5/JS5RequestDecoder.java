package com.runecore.codec.codec614.js5;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * JS5RequestDecoder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class JS5RequestDecoder extends FrameDecoder {

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel chan, ChannelBuffer buf) throws Exception {
	if(buf.readableBytes() >= 4) {
	    int priority = buf.readByte() & 0xFF;
	    int container = buf.readByte() & 0xFF;
	    int file = buf.readShort() & 0xFFFF;
	    if(priority == 0 || priority == 1) {
		return new JS5Request(chan, priority, container, file);
	    }
	}
	return null;
    }

}
