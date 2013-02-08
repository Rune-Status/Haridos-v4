package com.runecore.codec.codec614.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.runecore.codec.codec614.js5.AuthenticationPacket;
import com.runecore.network.io.MessageBuilder;

/**
 * EventHandler.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class EventHandler extends SimpleChannelHandler {
    
    @Override
    public final void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
	if(e.getMessage() instanceof AuthenticationPacket) {
	    AuthenticationPacket p = (AuthenticationPacket)e.getMessage();
	    MessageBuilder builder = new MessageBuilder();
	    if(p.getRevision() != 614) {
		builder.writeByte(6);
	    } else {
		builder.writeByte(0);
	    }
	    ctx.getChannel().write(builder.toMessage());
	}
    }

}
