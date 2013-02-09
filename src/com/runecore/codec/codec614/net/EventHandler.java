package com.runecore.codec.codec614.net;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.runecore.codec.codec614.auth.LoginKeyRequest;
import com.runecore.codec.codec614.js5.AuthenticationPacket;
import com.runecore.codec.codec614.js5.JS5Request;
import com.runecore.network.io.MessageBuilder;

/**
 * EventHandler.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class EventHandler extends SimpleChannelHandler {
    
    private final ExecutorService service;
    
    public EventHandler(ExecutorService service) {
	this.service = service;
    }
    
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
	} else if(e.getMessage() instanceof JS5Request) {
	    service.submit((JS5Request)e.getMessage());
	} else if(e.getMessage() instanceof LoginKeyRequest) {
	    MessageBuilder builder = new MessageBuilder();
	    builder.writeByte(0);
	    builder.writeLong(new SecureRandom().nextLong());
	    ctx.getChannel().write(builder.toMessage());
	}
    }

}
